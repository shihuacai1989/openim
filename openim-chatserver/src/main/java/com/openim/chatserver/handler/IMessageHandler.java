package com.openim.chatserver.handler;

import com.alibaba.fastjson.JSONObject;
import com.openim.chatserver.handler.impl.HandlerChain;
import io.netty.channel.Channel;
import io.netty.util.AttributeKey;

/**
 * Created by shihuacai on 2015/7/22.
 */
public interface IMessageHandler {
    static AttributeKey<String> key = AttributeKey.valueOf("loginId");

    void handle(JSONObject jsonObject, HandlerChain handlerChain, Channel channel);
}
