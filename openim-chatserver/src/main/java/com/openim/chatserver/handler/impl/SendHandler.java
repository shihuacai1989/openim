package com.openim.chatserver.handler.impl;

import com.openim.chatserver.ChannelUtil;
import com.openim.chatserver.handler.IMessageHandler;
import com.openim.chatserver.listener.ApplicationContextAware;
import com.openim.common.im.bean.DeviceMsg;
import com.openim.common.im.bean.DeviceMsgType;
import com.openim.common.mq.IMessageSender;
import com.openim.common.mq.constants.MQConstants;
import io.netty.channel.Channel;
import io.netty.util.Attribute;

/**
 * Created by shihuacai on 2015/7/22.
 */
//@Component
public class SendHandler implements IMessageHandler {
    //private IMessageDispatch messageDispatch;
    private IMessageSender messageSender;

    public SendHandler() {
        messageSender = ApplicationContextAware.getBean(IMessageSender.class);
    }

    @Override
    public void handle(DeviceMsg jsonObject, HandlerChain handlerChain, Channel channel) {
        int type = jsonObject.getType();
        if (type == DeviceMsgType.CHAT) {
            Attribute<String> attribute = channel.attr(ChannelUtil.loginIdKey);
            jsonObject.setFrom(attribute.get());
            messageSender.sendMessage(MQConstants.openimExchange, MQConstants.chatRouteKey, jsonObject);
        } else {
            handlerChain.handle(jsonObject, handlerChain, channel);
        }
    }
}
