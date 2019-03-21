package com.gwm.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Created by Administrator on 2018/5/28 0028.
 */
@Documented
@Target(TYPE)
@Retention(RUNTIME)
public @interface Layout {
    int value() default 0;
}
