package com.openim.common.mq.constants;

/**
 * Created by shihuacai on 2015/7/28.
 */
public class MQConstants {
    public final static String openimExchange = "openim-exchange";

    //==================
    //以下队列由manager服务消费
    public final static String chatQueue = "openim-chat-queue";
    public final static String chatRouteKey = "openim-chat-routeKey";

    public final static String loginQueue = "openim-login-queue";
    public final static String loginRouteKey = "openim-login-routeKey";

    public final static String logoutQueue = "openim-logout-queue";
    public final static String logoutRouteKey = "openim-logout-routeKey";
    //==================
    //public final static String serverQueue = "openim-server-queue";
    //public final static String serverRouteKey = "openim-server-routeKey";
}
