package com.gwm.http;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;

import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.builder.GetBuilder;
import com.zhy.http.okhttp.builder.HasParamsable;
import com.zhy.http.okhttp.builder.OkHttpRequestBuilder;
import com.zhy.http.okhttp.builder.PostFormBuilder;
import com.zhy.http.okhttp.callback.FileCallBack;
import com.zhy.http.okhttp.cookie.CookieJarImpl;
import com.zhy.http.okhttp.cookie.store.PersistentCookieStore;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * Created by Administrator on 2017/11/14.
 */

public class BaseOkHttp {

    public BaseOkHttp(Context context) {
        CookieJarImpl cookieJar = new CookieJarImpl(new PersistentCookieStore(context));

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .cookieJar(cookieJar)
                //其他配置
                .connectTimeout(30L, TimeUnit.SECONDS)
                .addInterceptor(new LoggerInterceptor())
                .build();
        OkHttpUtils.initClient(okHttpClient);
    }

    public <T> void sendHttp(final HttpParams<T> httpParams) {
        RequestBody body = null;
        if (!httpParams.isRequestBody){
            if (TextUtils.isEmpty(httpParams.json) && httpParams.isJson) {
                Bundle bundle = httpParams.params;
                Set<String> strings = bundle.keySet();
                JSONObject json = new JSONObject();
                for (String string : strings) {
                    Object value = bundle.get(string);
                    try {
                        json.put(string, value);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"),json.toString());
            }else if (httpParams.isJson && !TextUtils.isEmpty(httpParams.json)){
                body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"),httpParams.json);
            }else {
                //通过Query做普通的数据请求
                sendQueryReq(httpParams);
                return;
            }
        }else {
            body = httpParams.body;
        }
        Request request = getRequest(httpParams.url, body);
        execute(request,httpParams);
    }

    private <T> void sendQueryReq(final HttpParams<T> httpParams) {
        if (httpParams.way == HttpParams.GET_WAY) {
            GetBuilder getBuilder = OkHttpUtils.get().url(httpParams.url);
            addParams(getBuilder, httpParams.params);
            if (httpParams.headers != null){
                addHeader(getBuilder,httpParams.headers);
            }
            execute(httpParams, getBuilder);
        } else if (httpParams.way == HttpParams.POST_WAY) {
            PostFormBuilder builder = OkHttpUtils.post().url(httpParams.url);
            addParams(builder, httpParams.params);
            if (httpParams.headers != null){
                addHeader(builder,httpParams.headers);
            }
            execute(httpParams, builder);
        } else if (httpParams.way == HttpParams.FILE_UPLOAD) {
            PostFormBuilder builder = OkHttpUtils.post().url(httpParams.url);
            addParams(builder, httpParams.params);

            addFile(builder, httpParams.files);
            execute(httpParams, builder);
        } else if (httpParams.way == HttpParams.DOWNLOAD_WAY) {
            GetBuilder getBuilder = OkHttpUtils.get().url(httpParams.url);
            getBuilder.build().execute(new FileCallBack(httpParams.targetPath, httpParams.targetFileName) {
                @Override
                public void onBefore(Request request, int id) {
                    if (httpParams.netListener != null)
                        httpParams.netListener.onBefore(request, id);
                }

                @Override
                public void onAfter(int id) {
                    if (httpParams.netListener != null)
                        httpParams.netListener.onAfter(id);
                }

                @Override
                public void onError(Call call, Exception e, int id) {
                    if (httpParams.observer != null)
                        httpParams.observer.onError(e, id);
                }

                @Override
                public void onResponse(File response, int id) {
                    if (httpParams.observer != null)
                        httpParams.observer.onNextFile(response, id);
                }

                @Override
                public void inProgress(float progress, long total, int id) {
                    if (httpParams.netListener != null)
                        httpParams.netListener.inProgress(progress, total, id);
                }
            });
        } else if (httpParams.way == HttpParams.WEB_SOCKET) {
            Request request = new Request.Builder().url(httpParams.url).build();
            OkHttpUtils.getInstance().getOkHttpClient().newWebSocket(request, httpParams.websocketListener);
        }
    }

    private void addHeader(OkHttpRequestBuilder builder, HashMap<String, String> headers) {
        Set<String> keySet = headers.keySet();
        for (String key : keySet) {
             builder.addHeader(key,headers.get(key));
        }

    }

    private <T> void execute(Request request, final HttpParams<T> httpParams) {
        OkHttpUtils.getInstance().getOkHttpClient().newCall(request).enqueue(new JsonCallBack<T>(httpParams.result) {
            @Override
            public void onFailure(Call call, IOException e) {
                super.onFailure(call, e);
                if (httpParams.observer != null){
                    httpParams.observer.onError(e,0);
                }
            }

            @Override
            public void onResponseNext(Call call, T t) {
                if (httpParams.observer != null){
                    httpParams.observer.onNext(t,0);
                }
            }
        });
    }

    private Request getRequest(String url, RequestBody body) {
        Request.Builder builder = new Request.Builder();
        builder = builder.url(url).post(body);
        return builder.build();
    }

    private void addFile(PostFormBuilder builder, Map<String, File> files) {
        Set<String> keySet = files.keySet();
        for (String key : keySet){
            File file = files.get(key);
            builder.addFile(key,file.getName(),file);
        }
    }
    private void addParams(HasParamsable builder, Bundle params) {
        Set<String> keySet = params.keySet();
        for (String key : keySet){
            builder.addParams(key,params.get(key).toString());
        }
    }

    private <T> void execute(final HttpParams<T> httpParams, OkHttpRequestBuilder build) {
        build.build().execute(new JsonStringCallBack<T>(httpParams.result) {
            @Override
            public void onResponseNext(T t) {
                if (httpParams.observer != null){
                    httpParams.observer.onNext(t,0);
                }
            }
        });
    }
}
