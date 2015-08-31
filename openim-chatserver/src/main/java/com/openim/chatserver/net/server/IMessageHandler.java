package com.openim.chatserver.net.server;

import com.openim.common.im.bean.ExchangeMessage;
import io.netty.channel.Channel;

/**
 * Created by shihuacai on 2015/7/22.
 */
public interface IMessageHandler<C, T> {
    void handle(C channel, T message);
}
