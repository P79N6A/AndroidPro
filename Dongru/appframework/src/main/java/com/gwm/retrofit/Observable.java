package com.gwm.retrofit;

import com.gwm.http.HttpObserver;
import com.gwm.http.HttpParams;
import com.gwm.util.MyLogger;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import okhttp3.Request;
import okhttp3.WebSocketListener;

/**
 * Created by Administrator on 2017/11/3.
 * http万物配置器
 */

public class Observable<T> {
    private HttpObserver<T> observer;
    private WebSocketListener listener;
    private Class<T> clazz;
    private HttpParams.HttpNetListener netListener = new HttpParams.HttpNetListener() {
        @Override
        public void onBefore(Request request, int id) {

        }

        @Override
        public void onAfter(int id) {

        }

        @Override
        public void inProgress(float progress, long total, int id) {

        }
    };

    /**
     * 配置请求回调监听
     * @param observer
     * @return
     */
    public Observable subscriber(HttpObserver<T> observer){
        this.observer = observer;
        return this;
    }
    /**
     * 配置websocket推送回调监听
     * @return
     */
    public Observable subscriber(WebSocketListener listener){
        this.listener = listener;
        return this;
    }

    /**
     *  配置实体解析
     * @param clazz
     * @return
     */
    public Observable subscriber(Class<T> clazz){
        this.clazz = clazz;
        return this;
    }


    /**
     * 配置网络请求加载框
     * @param netListener
     * @return
     */
    public Observable subscriber(HttpParams.HttpNetListener netListener){
        this.netListener = netListener;
        return this;
    }

    public HttpObserver<T> getObserver(){
        return observer;
    }

    public WebSocketListener getListener() {
        return listener;
    }

    public Class<T> getClazz() {
        return clazz;
    }

    public HttpParams.HttpNetListener getNetListener() {
        return netListener;
    }

}
