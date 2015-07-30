package com.openim.chatserver.handler.impl;

import com.alibaba.fastjson.JSONObject;
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
public class SendHandler implements IMessageHandler {
    //private IMessageDispatch messageDispatch;
    private IMessageSender messageSender;
    public SendHandler(){
        //messageDispatch = ApplicationStartUp.applicationContext.getBean(IMessageDispatch.class);
        messageSender = ApplicationContextAware.getBean(IMessageSender.class);
    }

    @Override
    public void handle(JSONObject jsonObject, HandlerChain handlerChain, Channel channel) {
        int type = jsonObject.getIntValue(DeviceMsgField.type);
        if (type == DeviceMsgType.SEND) {
            Attribute<String> attribute = channel.attr(key);
            jsonObject.put(DeviceMsgField.from, attribute.get());
            messageSender.sendMessage(MQConstants.openimExchange, MQConstants.chatRouteKey, jsonObject.toJSONString());
        } else {
            handlerChain.handle(jsonObject, handlerChain, channel);
        }
    }
}
