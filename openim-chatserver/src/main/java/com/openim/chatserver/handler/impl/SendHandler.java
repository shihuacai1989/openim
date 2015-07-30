package com.openim.chatserver.handler.impl;

import com.alibaba.fastjson.JSONObject;
import com.openim.chatserver.Constants;
import com.openim.chatserver.handler.IMessageHandler;
import com.openim.chatserver.listener.ApplicationContextAware;
import com.openim.common.bean.DeviceMsgField;
import com.openim.common.bean.DeviceMsgType;
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
        int type = jsonObject.getIntValue(Constants.deviceMsgField_Type);
        if (type == DeviceMsgType.SEND) {
            Attribute<String> attribute = channel.attr(key);
            jsonObject.put(DeviceMsgField.from, attribute.get());
            messageSender.sendMessage(MQConstants.openimExchange, MQConstants.chatRouteKey, jsonObject.toJSONString());
        } else {
            handlerChain.handle(jsonObject, handlerChain, channel);
        }
    }
}
