package com.openim.chatserver.net.netty.v2;

import com.openim.chatserver.net.IMessageHandler;
import com.openim.common.im.bean.ExchangeMessage;
import io.netty.channel.Channel;

/**
 * Created by shihuacai on 2015/7/22.
 */
public interface INettyMessageHandlerV2 extends IMessageHandler<Channel, ExchangeMessage> {
    //void handle(Channel channel, ExchangeMessage message);
}
