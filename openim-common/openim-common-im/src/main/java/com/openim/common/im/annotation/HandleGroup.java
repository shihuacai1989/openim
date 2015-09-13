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

    public static final String CHAT_SERVER_MQ_HANDLER = "chatServerMQHandler";

    public static final String CHAT_SERVER_NiO_HANDLER_V2 = "chatServerNioHandlerV2";

    public static final String MANAGER_MQ_HANDLER_V2 = "managerMQHandlerV2";

    String name();
    int type();
}
