package com.openim.chatserver.handler.impl;

import com.openim.chatserver.ChannelUtil;
import com.openim.chatserver.handler.IMessageHandler;
import com.openim.chatserver.listener.ApplicationContextAware;
import com.openim.common.im.bean.DeviceMsg;
import com.openim.common.im.bean.DeviceMsgType;
import com.openim.common.mq.IMessageSender;
import com.openim.common.mq.constants.MQConstants;
import io.netty.channel.Channel;

/**
 * Created by shihuacai on 2015/7/22.
 */
//@Component
public class LogoutHandler implements IMessageHandler {
    private IMessageSender messageSender;

    public LogoutHandler() {
        messageSender = ApplicationContextAware.getBean(IMessageSender.class);
    }

    @Override
    public void handle(DeviceMsg jsonObject, HandlerChain handlerChain, Channel channel) {
        int type = jsonObject.getType();
        if (type == DeviceMsgType.LOGOUT) {
            messageSender.sendMessage(MQConstants.openimExchange, MQConstants.logoutRouteKey, jsonObject);
            ChannelUtil.remove(channel);
        } else {
            handlerChain.handle(jsonObject, handlerChain, channel);
        }
    }
}
