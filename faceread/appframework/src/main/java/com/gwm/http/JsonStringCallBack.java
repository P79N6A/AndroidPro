package com.gwm.http;

import com.alibaba.fastjson.JSON;
import com.gwm.util.MyLogger;
import com.zhy.http.okhttp.callback.StringCallback;

import okhttp3.Call;

/**
 * Created by Administrator on 2018/5/23 0023.
 */

public abstract class JsonStringCallBack<T> extends StringCallback {
    private Class<T> clazz;

    public JsonStringCallBack(Class<T> clazz){
        this.clazz = clazz;
    }
    @Override
    public void onError(Call call, Exception e, int id) {
        e.printStackTrace();
    }

    @Override
    public void onResponse(String response, int id) {
        try {
            T t = JSON.parseObject(response, clazz);//转成对象
            onResponseNext(t);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public abstract void onResponseNext(T t);
}
