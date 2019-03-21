package com.gwm.base;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;

import com.gwm.android.ACache;
import com.gwm.annotation.ACacheParam;
import com.gwm.annotation.BindIntent;
import com.gwm.annotation.Layout;

import java.lang.reflect.Field;

/**
 * Base框架公共部分的提取
 * 自动查找UI布局layout文件和自动添加监听事件
 */
public class BaseCommon {
    public static final String ACTIVITY = "Activity";
    public static final String FRAGMENT = "Fragment";
    public static final String PAGE = "Page";
    private static BaseCommon common;
    private Context context;
    private BaseCommon(Context context){
        this.context = context;

    }
    public synchronized static BaseCommon getCommon(Context context){
        if (common == null)
            common = new BaseCommon(context);
        return common;
    }
    public void bindIntent(AppCompatActivity activity) {
        Field[] fields = activity.getClass().getFields();
        Bundle extras = activity.getIntent().getExtras();
        if(extras != null && !extras.isEmpty()) {
            for (Field field : fields) {
                if (field.isAnnotationPresent(BindIntent.class)) {
                    try {
                        BindIntent bindIntent = field.getAnnotation(BindIntent.class);
                        String key = TextUtils.isEmpty(bindIntent.key()) ? (TextUtils.isEmpty(bindIntent.value()) ? field.getName() : bindIntent.value()): bindIntent.key();
                        field.set(activity, extras.get(key));
                        break;
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
    public View bindView(Object obj){
        Class clazz = obj.getClass();
        if(clazz.isAnnotationPresent(Layout.class)) {
            Layout layout = (Layout) clazz.getAnnotation(Layout.class);
            int resId = layout.value();
            if (resId != 0) {
                return LayoutInflater.from(context).inflate(resId, null);
            }
        }
        return null;
    }




    public void bindCacheParam(Object obj){
        Class clazz = obj.getClass();
        Field[] fields = clazz.getFields();
        for(Field field : fields){
            if (field.isAnnotationPresent(ACacheParam.class)){
                ACacheParam param = field.getAnnotation(ACacheParam.class);
                field.setAccessible(true);
                ACache acache = BaseAppcation.getInstance().getCache();
                String key = TextUtils.isEmpty(param.key()) ? (TextUtils.isEmpty(param.value()) ? field.getName() : param.value()): param.key();
                try {
                    field.set(obj,acache.getAsObject(key));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
