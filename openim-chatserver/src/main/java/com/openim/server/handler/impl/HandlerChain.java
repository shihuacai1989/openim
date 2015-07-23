package com.openim.server.handler.impl;

import com.alibaba.fastjson.JSONObject;
import com.openim.server.handler.IMessageHandler;
import io.netty.channel.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.plugin2.message.HeartbeatMessage;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shihuacai on 2015/7/23.
 */
public class HandlerChain implements IMessageHandler {
    private static final Logger LOG = LoggerFactory.getLogger(HandlerChain.class);

    private List<IMessageHandler> handlerList;

    private int index;
    private int max;

    public HandlerChain(){
        handlerList = new ArrayList<IMessageHandler>();
        handlerList.add(new LoginHandler());
        handlerList.add(new SendHandler());
        handlerList.add(new HeartBeatHandler());
        handlerList.add(new LogoutHandler());

        index = -1;
        max = handlerList.size() - 1;
    }
    @Override
    public void handle(JSONObject jsonObject, HandlerChain handlerChain, Channel channel) {
        index ++;
        if(index <= max){
            handlerList.get(index).handle(jsonObject, handlerChain, channel);
        }else{
            LOG.error("无法处理的客户端消息: {}", jsonObject.toString());
        }
    }
}