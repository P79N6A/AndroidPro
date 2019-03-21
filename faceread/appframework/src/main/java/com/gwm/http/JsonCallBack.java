package com.gwm.http;

import com.alibaba.fastjson.JSON;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public abstract class JsonCallBack<T> implements Callback {
    private Class<T> result;

    public JsonCallBack(Class<T> result) {
        this.result = result;
    }

    @Override
    public void onFailure(Call call, IOException e) {

    }

    @Override
    public void onResponse(Call call, Response response) throws IOException {
        try {
            T t = JSON.parseObject(response.body().string(), result);//转成对象
            onResponseNext(call,t);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public abstract void onResponseNext(Call call,T t);
}
