package com.shan.netlibrary.net;

import android.os.SystemClock;
import android.text.TextUtils;
import android.util.Log;

import com.junshan.pub.bean.MessageEvent;
import com.junshan.pub.config.BaseMsgConstant;
import com.junshan.pub.utils.LogUtils;
import com.junshan.pub.utils.SPUtils;
import com.junshan.pub.utils.TimeUtils;

import org.greenrobot.eventbus.EventBus;

import java.io.EOFException;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;

import okhttp3.Headers;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.internal.http.HttpEngine;
import okio.Buffer;
import okio.BufferedSource;

/**
 * Created by chenjunshan on 2016/8/19.
 */

public class HttpInterceptor implements Interceptor {
    private static final Charset UTF8 = Charset.forName("UTF-8");

    @Override
    public Response intercept(Chain chain) throws IOException {
        String headerValue = null;
        String token = (String) SPUtils.get("token", "");
        Request request;
        if (TextUtils.isEmpty(token)) {
            request = chain.request();
        } else {
            request = chain.request().newBuilder().addHeader("token", token).build();
        }
        Response response = null;

        Log.e("TOKEN",token);

        /*---------增加多baseUrl--STARt---------*/
        //获取request的创建者builder
        Request.Builder builder = request.newBuilder();
        builder.addHeader("Connection","close");
        //从request中获取headers，通过给定的键url_name
        List<String> headerValues = request.headers(HttpConfig.HEADER_KEY);
        if (headerValues != null && headerValues.size() > 0) {
            //如果有这个header，先将配置的header删除，因此header仅用作app和okhttp之间使用
            builder.removeHeader(HttpConfig.HEADER_KEY);

            //从request中获取原有的HttpUrl实例oldHttpUrl
            HttpUrl oldHttpUrl = request.url();

            //匹配获得新的BaseUrl
            headerValue = headerValues.get(0);
            HttpUrl newBaseUrl = null;

            if (!TextUtils.isEmpty(headerValue) && HttpConfig.YQX_URL_HEADER.equals(headerValue)) {
                newBaseUrl = HttpUrl.parse(HttpBuilder.YQX_URL);
                LogUtils.e("headerValue:" + headerValue);
            } else if (!TextUtils.isEmpty(headerValue) && HttpConfig.OLD_OGMALL_URL_HEADER.equals(headerValue)) {
                newBaseUrl = HttpUrl.parse(HttpBuilder.OLD_OGMALL_URL);
                LogUtils.e("headerValue:" + headerValue);
            } else {
                newBaseUrl = oldHttpUrl;
            }

            //重建新的HttpUrl，修改需要修改的url部分
            HttpUrl newFullUrl = oldHttpUrl
                    .newBuilder()
                    .scheme(newBaseUrl.scheme())
                    .host(newBaseUrl.host())
                    .port(newBaseUrl.port())
                    .build();

            //重建这个request，通过builder.url(newFullUrl).build()；
            //然后返回一个response至此结束修改
            response = chain.proceed(builder.url(newFullUrl).build());
        } else {
            response = chain.proceed(request);
        }
        /*---------增加多baseUrl--END---------*/
//        if (response.body().string().contains("50x.html")){
//            EventBus.getDefault().post(new MessageEvent(BaseMsgConstant.SYSTEMERROR));
//            return response;
//        }
        LogUtils.i("==================================" + TimeUtils.getTime("yyyy-MM-dd HH:mm:ss") + "==================================");
        LogUtils.i("===== URL:" + response.toString());

        ResponseBody responseBody = response.body();
        long contentLength = responseBody.contentLength();

        if (!HttpEngine.hasBody(response) || bodyEncoded(response.headers())) {
            return response;
        }

        try {
            BufferedSource source = responseBody.source();
            source.request(Long.MAX_VALUE); // Buffer the entire body.
            Buffer buffer = source.buffer();

            Charset charset = UTF8;
            MediaType contentType = responseBody.contentType();
            if (contentType != null) {
                charset = contentType.charset(UTF8);
            }

            if (!isPlaintext(buffer)) {
                LogUtils.i("<-- END HTTP (binary " + buffer.size() + "-byte body omitted)");
                return response;
            }

            if (contentLength != 0) {
                //如果result的长度大于1000则分段打印输出
                String result = buffer.clone().readString(charset);

                //判断是不是一起秀回调
                if (headerValue != null && HttpConfig.YQX_URL_HEADER.equals(headerValue)) {
                    result = "{\"code\":0,\"data\":" + result + ",\"message\":\"成功\"}";
                    buffer.clear();
                    buffer.writeUtf8(result);
                }

                ////////////////////
                if (result.contains("IsTrue")) {
                    //如果是操作类接口就睡眠1秒，防止主从数据库不能同步
                    SystemClock.sleep(1000);
                }
                /////////////////////
                int length = result.length();
                int printNum = 3000;//每次打印的字数
                if (length > printNum) {
                    int number = length / printNum;
                    int beyond = length % printNum;
                    int index = 0;
                    for (int i = 0; i < number; i++) {
                        LogUtils.i("===== " + result.substring(index, index + printNum));
                        index = index + printNum;
                    }
                    LogUtils.i("===== " + result.substring(index, index + beyond));
                } else {
                    LogUtils.i("===== " + result);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }


    static boolean isPlaintext(Buffer buffer) throws EOFException {
        try {
            Buffer prefix = new Buffer();
            long byteCount = buffer.size() < 64 ? buffer.size() : 64;
            buffer.copyTo(prefix, 0, byteCount);
            for (int i = 0; i < 16; i++) {
                if (prefix.exhausted()) {
                    break;
                }
                int codePoint = prefix.readUtf8CodePoint();
                if (Character.isISOControl(codePoint) && !Character.isWhitespace(codePoint)) {
                    return false;
                }
            }
            return true;
        } catch (EOFException e) {
            return false; // Truncated UTF-8 sequence.
        }
    }

    private boolean bodyEncoded(Headers headers) {
        String contentEncoding = headers.get("Content-Encoding");
        return contentEncoding != null && !contentEncoding.equalsIgnoreCase("identity");
    }

}
