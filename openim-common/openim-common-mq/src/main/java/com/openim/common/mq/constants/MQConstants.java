package com.openim.common.mq.constants;

/**
 * Created by shihuacai on 2015/7/28.
 */
public class MQConstants {
    public final static String openimExchange = "openim-exchange";

    //==================
    //以下队列由manager服务消费，由rabbitmq使用
    public final static String chatQueue = "openim-chat-queue";
    public final static String chatRouteKey = "openim-chat-routeKey";

    public final static String loginQueue = "openim-login-queue";
    public final static String loginRouteKey = "openim-login-routeKey";

    public final static String logoutQueue = "openim-logout-queue";
    public final static String logoutRouteKey = "openim-logout-routeKey";
    //==================


    //==================
    //activemq消息，暂时未找到其监听器同时监听多个队列的方法
    public final static String MANAGER_CONSUMER_TOPIC = "managerTopic";
    public final static String CHATSERVER_CONSUMER_TOPIC = "chatServerTopic";
    //==================
}
