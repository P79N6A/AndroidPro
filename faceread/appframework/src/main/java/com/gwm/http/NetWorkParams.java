package com.gwm.http;

import android.os.Bundle;

/**
 * Created by Administrator on 2017/11/24.
 */

public class NetWorkParams<Result> {
    public String url;
    public HttpObserver<Result> observer; //控制器，http请求会将服务器返回的数据传递给该接口
    public Bundle params;  //http请求的参数(不包含文件) Bundle中如果包含复合类型统一采用JSON格式封装
    public Class<Result> result;
}
