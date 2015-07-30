package com.openim.manager.handler;

/**
 * Created by shihc on 2015/7/30.
 */
public interface IMessageHandler<T> {
    void handle(T t, HandlerChain handlerChain);
}
