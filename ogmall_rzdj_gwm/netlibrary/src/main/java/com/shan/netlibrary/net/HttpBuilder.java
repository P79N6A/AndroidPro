package com.shan.netlibrary.net;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by chenjunshan on 2016/8/19.
 */

public class HttpBuilder {
    //public static String BASE_URL = "http://47.99.118.121/";//正式环境
    public static String BASE_URL = "http://pad.rzds.net/";//正式环境,域名
//    public static String BASE_URL = "http://192.168.1.107:8080/";//测试环境
    //public static String YQX_URL = "http://47.106.134.201:8080/";//一起秀URL,测试环境
    public static String YQX_URL = "http://ougong.e7show.com/";//一起秀URL，正式环境
    public static String OLD_OGMALL_URL = "http://318.ogmall.com/";//正式环境
    public static String SHARE_URL = "share/pro.html?";
    private static final int DEFAULT_TIMEOUT = 30;//默认5秒超时
    private static final HttpBuilder INSTANCE = new HttpBuilder();
    private Retrofit retrofit;
    public static HttpService httpService;

    private HttpBuilder() {
        retrofit = new Retrofit.Builder()
                .client(getOkHttpClient())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(BASE_URL)
                .build();
        httpService = retrofit.create(HttpService.class);
    }

    private static OkHttpClient getOkHttpClient() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .addInterceptor(new HttpInterceptor())
                .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true);//是否超时重连
        return builder.build();
    }

    public static HttpBuilder getInstance() {
        return INSTANCE;
    }

    public <T> Subscription execute(Observable observable, Subscriber<T> subscriber) {
        return observable.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    public <T> Subscription execute(Observable observable, Subscriber<T> subscriber, Func1 func1) {
        return observable.map(func1)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }
}
