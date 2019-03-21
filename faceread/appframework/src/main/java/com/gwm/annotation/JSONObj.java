package com.gwm.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Created by Administrator on 2018/5/10 0010.
 */
@Documented
@Target({METHOD})
@Retention(RUNTIME)
public @interface JSONObj {
    String transType();
}
