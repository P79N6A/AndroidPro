package com.gwm.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Created by 13258 on 2018/4/29.
 * 用于注解RequestBody参数的注解类型   用于自定义请求  请求方式为POST
 * 使用了该注解，均不能与Query,FileUpload,JSON 注解连用
 */
@Documented
@Target(PARAMETER)
@Retention(RUNTIME)
public @interface RequestBody {
}
