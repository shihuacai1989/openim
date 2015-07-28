package com.openim.common.mq;

/**
 * Created by shihuacai on 2015/7/28.
 */
public interface IMessageDispatch {
    void dispatchMessage(String exchange, String routeKey, Object message);
}
