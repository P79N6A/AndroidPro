package com.gwm.base;

import android.content.Context;

import com.gwm.android.ACache;
import com.gwm.http.HttpObserver;
import com.gwm.retrofit.Observable;
import com.gwm.retrofit.RetrofitOKHttp;
import com.gwm.util.MyLogger;


import java.io.File;

/**
 * Created by Administrator on 2018/1/25.
 *
 * Http 与 UI 之间的衔接层的基类
 */

public abstract class BaseDataPresenter<S> {
    private ViewModelCallback callback;
    private RetrofitOKHttp<S> retrofitOKHttp;
    public BaseDataPresenter(ViewModelCallback callback,Context context, Class<S> clazz){
        this.callback = callback;
        retrofitOKHttp = RetrofitOKHttp.getInstance(context, clazz);
    }

    protected <T> void addHttpSubscriber(Observable<T> observable,Class<T> clazz){
        addHttpSubscriber(observable,clazz,false);
    }
    protected <T> void addHttpSubscriber(Observable<T> observable,Class<T> clazz,boolean isCannal){
        HttpObserver<T> httpObserver = new HttpObserver<T>() {
            @Override
            public void onError(Exception e, int id) {
                onErrorResult(e,id);
                MyLogger.kLog().e(e);
            }

            @Override
            public void onNext(T response, int id) {
                onNextResult(getCache(),response,id);
                callback.onRequestSuccessData(response);
            }

            @Override
            public void onNextFile(File response, int id) {
                onNextFileResult(getCache(),response,id);
                callback.onRequestSuccessData(response);
            }
        };
        observable = observable.subscriber(httpObserver).subscriber(clazz);
        retrofitOKHttp.runObservable(observable,isCannal);
    }
    protected void setBaseUrl(String baseUrl) {
        retrofitOKHttp.setBaseUrl(baseUrl);
    }
    public S getHttpPresenter(){
        return retrofitOKHttp.getInstance();
    }

    protected abstract void onNextFileResult(ACache cache,File response, int id);

    protected abstract  void onNextResult(ACache cache,Object response, int id);

    protected abstract void onErrorResult(Exception e, int id);

    protected ViewModelCallback getView(){
        return callback;
    }


    public ACache getCache(){
        return BaseAppcation.getInstance().getCache();
    }
}
