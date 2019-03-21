package com.gwm.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static com.gwm.annotation.HTTP.WAY.POST;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Created by Administrator on 2017/11/3.
 * 使用注解的形式拼写HTTP请求时应该注意：
 *  例如：@HTTP(urlId = R.string.http_login,way = "POST")
 *
 */
@Documented
@Target(METHOD)
@Retention(RUNTIME)
public @interface HTTP{
    String url() default "";
    WAY way() default POST;
    String targetPath() default "";
    String targetFileName() default "";
    boolean isJson() default false;
    boolean isRequestBody() default false;

    enum WAY {
        POST,GET,DOWNLOAD,FILE_UPLOAD
    }
}
