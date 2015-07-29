package com.openim.server.handler.impl;

import com.alibaba.fastjson.JSONObject;
import com.openim.common.bean.DeviceMsgType;
import com.openim.common.mq.IMessageSender;
import com.openim.common.mq.constants.MQConstants;
import com.openim.server.Constants;
import com.openim.server.handler.IMessageHandler;
import com.openim.server.listener.ApplicationContextAware;
import io.netty.channel.Channel;

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
            //messageDispatch.dispatchMessage(null, null, jsonObject);
            messageSender.sendMessage(MQConstants.openimExchange, MQConstants.chatRouteKey, jsonObject.toJSONString());
        } else {
            handlerChain.handle(jsonObject, handlerChain, channel);
        }
    }
}
