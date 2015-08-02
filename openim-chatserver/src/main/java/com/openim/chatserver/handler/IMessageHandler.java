package com.openim.chatserver.handler;

import com.openim.chatserver.handler.impl.HandlerChain;
import com.openim.common.im.bean.DeviceMsg;
import io.netty.channel.Channel;

/**
 * Created by shihuacai on 2015/7/22.
 */
public interface IMessageHandler {
    //static AttributeKey<String> key = AttributeKey.valueOf("loginId");

    void handle(DeviceMsg jsonObject, HandlerChain handlerChain, Channel channel);
}
