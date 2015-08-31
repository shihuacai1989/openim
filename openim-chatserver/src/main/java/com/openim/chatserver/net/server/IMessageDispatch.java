package com.openim.chatserver.net.server;

/**
 * Created by shihc on 2015/8/31.
 */
public interface IMessageDispatch<C, T> {
    void dispatch(C channel, T message);
}
