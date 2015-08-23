package com.openim.chatserver.net.handler;

import io.netty.channel.Channel;

/**
 * Created by shihuacai on 2015/7/22.
 */
public interface IMessageHandler<Message, Channel> {
    void handle(Message message, Channel channel);
}
