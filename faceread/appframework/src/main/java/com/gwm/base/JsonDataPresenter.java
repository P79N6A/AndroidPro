package com.gwm.base;

import android.content.Context;

import com.gwm.annotation.JSONObj;
import com.gwm.annotation.Param;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.List;

/**
 * Created by Administrator on 2018/5/8 0008.
 */

public abstract class JsonDataPresenter<T> extends BaseDataPresenter {
    private Context mContext;
    private Object jsonCreator;

    protected JsonDataPresenter(ViewModelCallback modelCallback,Context context, Class<T> clazz,Class httpClazz) {
        super(modelCallback,context, httpClazz);
        this.mContext = context;
        jsonCreator = Proxy.newProxyInstance(context.getClassLoader(), new Class[]{clazz}, new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                if (method.isAnnotationPresent(JSONObj.class)){
                    JSONObject obj = new JSONObject();
                    Annotation[][] annotations = method.getParameterAnnotations();
                    for (int i = 0; i < annotations.length; i++) {     //i:第几个参数的注解
                        if (annotations[i].length > 0) {
                            for (int j = 0; j < annotations[i].length; j++) {  //j: 第i个参数上的第j个注解
                                if (annotations[i][j] instanceof Param) {
                                    Param query = (Param) annotations[i][j];
                                    String key = query.value();
                                    Object value = args[i];
                                    obj.put(key,value);
                                }
                            }
                        }
                    }
                    return obj.toString();
                }else {
                    throw new IllegalArgumentException("This method does not use JSONObj annotation");
                }
            }
        });
    }
    protected JsonDataPresenter(ViewModelCallback modelCallback,Context context,Class httpClazz) {
        super(modelCallback,context, httpClazz);
    }

    protected JSONArray getListString(List<String> strs){
        if (strs == null || strs.isEmpty()){
            return new JSONArray();
        }
        JSONArray array = new JSONArray();
        for (String string : strs){
            array.put(string);
        }
        return array;
    }

    public T getJsonCreator(){
        return (T) jsonCreator;
    }
}
