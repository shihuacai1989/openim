package com.openim.common.mq;

/**
 * Created by shihuacai on 2015/7/28.
 */
public interface IMessageSender {
    void sendMessage(String exchange, String routeKey, Object message);
}
