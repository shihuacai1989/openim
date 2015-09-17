package com.openim.common.mq.constants;

/**
 * Created by shihuacai on 2015/7/28.
 * 由于rabbitmq与activemq的API接口差异，注意队列的命名规范
 */
public class MQConstants {

    public static final String chatServerQueueTemplate = "chatSever-{server}:{port}";


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
    //public final static String MANAGER_CONSUMER_TOPIC = "openim-*-routeKey";
    //public final static String CHATSERVER_CONSUMER_TOPIC = "chatSever-*";
    public final static String MANAGER_CONSUMER_TOPIC = "MANAGER_CONSUMER";
    public final static String CHATSERVER_CONSUMER_TOPIC = "CHATSERVER_CONSUMER";

    //==================
}
