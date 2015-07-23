package com.openim.server.handler;

import com.alibaba.fastjson.JSONObject;
import com.openim.server.handler.impl.HandlerChain;
import io.netty.channel.Channel;

/**
 * Created by shihuacai on 2015/7/22.
 */
public interface IMessageHandler {
    void handle(JSONObject jsonObject, HandlerChain handlerChain, Channel channel);
}
