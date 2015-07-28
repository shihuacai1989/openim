package com.openim.server.handler.impl;

import com.alibaba.fastjson.JSONObject;
import com.openim.common.bean.DeviceMsgType;
import com.openim.common.mq.IMessageSender;
import com.openim.common.mq.constants.MQConstants;
import com.openim.server.Constants;
import com.openim.server.handler.IMessageHandler;
import com.openim.server.listener.ApplicationStartUp;
import io.netty.channel.Channel;

/**
 * Created by shihuacai on 2015/7/22.
 */
//@Component
public class LogoutHandler implements IMessageHandler {
    private IMessageSender messageSender;
    public LogoutHandler(){
        //messageDispatch = ApplicationStartUp.applicationContext.getBean(IMessageDispatch.class);
        messageSender = ApplicationStartUp.applicationContext.getBean(IMessageSender.class);
    }
    @Override
    public void handle(JSONObject jsonObject, HandlerChain handlerChain, Channel channel) {
        int type = jsonObject.getIntValue(Constants.deviceMsgField_Type);
        if (type == DeviceMsgType.LOGOUT) {
            messageSender.sendMessage(MQConstants.chatExchange, MQConstants.logoutRouteKey, jsonObject);
        } else {
            handlerChain.handle(jsonObject, handlerChain, channel);
        }
    }
}
