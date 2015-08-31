package com.openim.chatserver.net.server.netty.jdk.handler;

import com.openim.common.im.bean.DeviceMsg;
import io.netty.channel.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shihuacai on 2015/7/23.
 */
@Deprecated
public class HandlerChain implements IMessageHandler {
    private static final Logger LOG = LoggerFactory.getLogger(HandlerChain.class);

    private List<IMessageHandler> handlerList;

    private int index;
    private int max;

    /*static{
        handlerList = new ArrayList<IMessageHandler>();
        handlerList.add(new LoginHandler());
        handlerList.add(new ChatHandler());
        handlerList.add(new HeartBeatHandler());
        handlerList.add(new LogoutHandler());
    }*/

    public HandlerChain() {
        handlerList = new ArrayList<IMessageHandler>();
        handlerList.add(new LoginHandler());
        handlerList.add(new SendHandler());
        handlerList.add(new HeartBeatHandler());
        handlerList.add(new LogoutHandler());

        index = -1;
        max = handlerList.size() - 1;
    }

    @Override
    public void handle(DeviceMsg jsonObject, HandlerChain handlerChain, Channel channel) {
        index++;
        if (index <= max) {
            handlerList.get(index).handle(jsonObject, handlerChain, channel);
            index = -1;
        } else {
            LOG.error("无法处理的客户端消息: {}", jsonObject.toString());
        }
    }
}
