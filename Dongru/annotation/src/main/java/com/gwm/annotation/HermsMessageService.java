package com.gwm.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Created by Administrator on 2019/1/10.
 */
@Documented
@Target(TYPE)
@Retention(RUNTIME)
public @interface HermsMessageService {
    String value();
}
