package com.openim.manager.dispatch.handler.v2;

/**
 * Created by shihc on 2015/7/30.
 */
public interface IMessageHandler<T> {
    void handle(T t);
}
