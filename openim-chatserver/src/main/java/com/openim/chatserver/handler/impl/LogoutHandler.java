package com.openim.chatserver.handler.impl;

import com.alibaba.fastjson.JSONObject;
import com.openim.chatserver.ChannelUtil;
import com.openim.chatserver.handler.IMessageHandler;
import com.openim.chatserver.listener.ApplicationContextAware;
import com.openim.common.im.DeviceMsgField;
import com.openim.common.im.DeviceMsgType;
import com.openim.common.mq.IMessageSender;
import com.openim.common.mq.constants.MQConstants;
import io.netty.channel.Channel;
import io.netty.util.Attribute;

/**
 * Created by shihuacai on 2015/7/22.
 */
//@Component
public class LogoutHandler implements IMessageHandler {
    private IMessageSender messageSender;

    public LogoutHandler(){
        messageSender = ApplicationContextAware.getBean(IMessageSender.class);
    }

    @Override
    public void handle(JSONObject jsonObject, HandlerChain handlerChain, Channel channel) {
        int type = jsonObject.getIntValue(DeviceMsgField.type);
        if (type == DeviceMsgType.LOGOUT) {
            messageSender.sendMessage(MQConstants.openimExchange, MQConstants.logoutRouteKey, jsonObject);
            Attribute<String> attribute = channel.attr(key);
            String loginId = attribute.get();
            ChannelUtil.remove(loginId);
        } else {
            handlerChain.handle(jsonObject, handlerChain, channel);
        }
    }
}
