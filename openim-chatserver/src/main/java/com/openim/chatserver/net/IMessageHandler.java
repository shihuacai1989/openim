package com.openim.chatserver.net;

/**
 * Created by shihuacai on 2015/7/22.
 */
@Deprecated
public interface IMessageHandler<C, T> {
    void handle(C channel, T message);
}
