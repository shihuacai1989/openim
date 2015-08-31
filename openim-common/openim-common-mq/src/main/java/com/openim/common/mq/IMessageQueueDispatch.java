package com.openim.common.mq;

/**
 * Created by shihuacai on 2015/7/28.
 */
public interface IMessageQueueDispatch {
    void dispatchMessage(String exchange, String routeKey, byte[] message);
}
