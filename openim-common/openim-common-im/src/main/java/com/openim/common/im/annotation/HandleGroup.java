package com.openim.common.im.annotation;

/**
 * Created by shihc on 2015/9/2.
 */

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface HandleGroup {
    String name();
    int type();
}
