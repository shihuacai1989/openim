package com.openim.server.handler.impl;

import com.alibaba.fastjson.JSONObject;
import com.openim.common.bean.DeviceMsgType;
import com.openim.common.mq.IMessageSender;
import com.openim.common.mq.constants.MQConstants;
import com.openim.server.ChannelUtil;
import com.openim.server.Constants;
import com.openim.server.handler.IMessageHandler;
import com.openim.server.listener.ApplicationContextAware;
import io.netty.channel.Channel;
import io.netty.util.Attribute;
import io.netty.util.AttributeKey;

/**
 * Created by shihuacai on 2015/7/22.
 */
//@Component
public class LogoutHandler implements IMessageHandler {
    private IMessageSender messageSender;
    AttributeKey<String> key = AttributeKey.valueOf("loginId");

    public LogoutHandler(){
        //messageDispatch = ApplicationStartUp.applicationContext.getBean(IMessageDispatch.class);
        messageSender = ApplicationContextAware.getBean(IMessageSender.class);
    }
    @Override
    public void handle(JSONObject jsonObject, HandlerChain handlerChain, Channel channel) {
        int type = jsonObject.getIntValue(Constants.deviceMsgField_Type);
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
