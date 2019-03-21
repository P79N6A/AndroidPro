package com.gwm.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Documented
@Target(METHOD)
@Retention(RUNTIME)
public @interface Subscrition {
    String action() default "";
    ThreadMode threadMode() default ThreadMode.MAIN;

    enum ThreadMode{
        MAIN,CHILD,CURRENT
    }
}
