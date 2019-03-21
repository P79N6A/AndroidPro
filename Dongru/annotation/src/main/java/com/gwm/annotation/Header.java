package com.gwm.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Header注解使用案例：
 *  @Header({"key:value","key:value"})
 */
@Documented
@Target(METHOD)
@Retention(RUNTIME)
public @interface Header {
    String[] value();
}
