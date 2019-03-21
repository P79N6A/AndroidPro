package com.gwm.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Created by Administrator on 2017/11/15.
 */
@Documented
@Target(PARAMETER)
@Retention(RUNTIME)
public @interface FileUpload {
    String key() default "";
    String value() default "";
}
