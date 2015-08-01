package com.openim.chatserver.dispatch;

/**
 * Created by shihuacai on 2015/7/29.
 */
public interface IChatServerMessageDispatch {
    void dispatchMessage(String exchange, String routeKey, byte[] bytes);
}
