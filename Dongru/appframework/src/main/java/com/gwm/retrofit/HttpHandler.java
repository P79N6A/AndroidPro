package com.gwm.retrofit;

import android.os.Bundle;
import android.text.TextUtils;

import com.gwm.annotation.DownLoadAnnotation;
import com.gwm.annotation.FileUpload;
import com.gwm.annotation.HTTP;
import com.gwm.annotation.Header;
import com.gwm.annotation.JSON;
import com.gwm.annotation.Path;
import com.gwm.annotation.Query;
import com.gwm.annotation.QueryUrl;
import com.gwm.annotation.RequestBody;
import com.gwm.annotation.Url;
import com.gwm.http.HttpParams;
import com.gwm.http.NetWorkParams;

import java.io.File;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.HashMap;

/**
 * Created by Administrator on 2017/11/3.
 */
public class HttpHandler extends HttpInvocationHandler {

    public NetWorkParams addNetworkParams(Method method, Object[] args) {
        HttpParams params = new HttpParams();
        if(method.isAnnotationPresent(HTTP.class)) {
            HTTP annotation = method.getAnnotation(HTTP.class);
            params.url = annotation.url();
            params.way = annotation.way();
            params.isJson = annotation.isJson();
            params.isRequestBody = annotation.isRequestBody();
            if (method.isAnnotationPresent(DownLoadAnnotation.class)) {
                params.isDownLoad = true;
                DownLoadAnnotation annotation1 = method.getAnnotation(DownLoadAnnotation.class);
                params.targetPath = annotation1.targetPath();
                params.targetFileName = annotation1.targetFileName();
            }
            Annotation[][] annotations = method.getParameterAnnotations();
            params.params = new Bundle();
            params.files = new HashMap();
            for (int i = 0; i < annotations.length; i++) {     //i:第几个参数的注解
                if (annotations[i].length > 0) {
                    for (int j = 0; j < annotations[i].length; j++) {  //j: 第i个参数上的第j个注解
                        if(annotations[i][j] instanceof Query) {
                            Query query = (Query) annotations[i][j];
                            String key = query.value();
                            Object value = args[i];
                            if (value instanceof Integer) {
                                params.params.putInt(key, (Integer) value);
                            } else if (value instanceof String) {
                                params.params.putString(key, (String) value);
                            } else if (value instanceof Double) {
                                params.params.putDouble(key, (Double) value);
                            }
                        }else if(annotations[i][j] instanceof FileUpload){
                            FileUpload fileUpload = (FileUpload) annotations[i][j];
                            String key = TextUtils.isEmpty(fileUpload.key()) ? (TextUtils.isEmpty(fileUpload.value()) ? null: fileUpload.value()): fileUpload.key();
                            File file = (File) args[i];
                            params.files.put(key,file);
                        }else if(annotations[i][j] instanceof Url){
                            if(args[i] instanceof String){
                                params.url = (String) args[i];
                            }
                        }else if (annotations[i][j] instanceof RequestBody){
                            params.body = (okhttp3.RequestBody) args[i];
                            params.isRequestBody = true;
                        }else if (annotations[i][j] instanceof JSON){
                            params.json = (String) args[i];
                            params.isJson = true;
                        }else if (annotations[i][j] instanceof QueryUrl){
                            String key = ((QueryUrl)annotations[i][j]).value();
                            String value = (String) args[i];
                            if (params.url != null){
                                if (params.url.contains("?")){
                                    params.url += ("&&" + key + "=" + value);
                                }else {
                                    params.url += ("?" + key + "=" + value);
                                }
                            }
                        }else if (annotations[i][j] instanceof Path){
                            String value = ((Path)annotations[i][j]).value();
                            params.url = params.url.replaceAll("\\{"+value+"}", (String) args[i]);
                        }
                    }
                }
            }
            //添加header头解析
            Header header = method.getAnnotation(Header.class);
            if (header != null){
                String[] strings = header.value();
                if (params.headers == null){
                    params.headers = new HashMap();
                    for (String string : strings){
                        String keyvalue[] = string.split(":");
                        params.headers.put(keyvalue[0],keyvalue[1]);
                    }
                }
            }
        }else {
            throw new IllegalArgumentException("Unknown method annotation");
        }
        return params;
    }

}
