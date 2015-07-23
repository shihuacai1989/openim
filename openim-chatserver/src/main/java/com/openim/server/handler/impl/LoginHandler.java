package com.openim.server.handler.impl;

import com.alibaba.fastjson.JSONObject;
import com.openim.common.bean.DeviceMsgType;
import com.openim.server.Constants;
import com.openim.server.handler.IMessageHandler;
import io.netty.channel.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Created by shihuacai on 2015/7/22.
 */
//@Component
public class LoginHandler implements IMessageHandler {

    private static final Logger LOG = LoggerFactory.getLogger(LoginHandler.class);

    @Override
    public void handle(JSONObject jsonObject, HandlerChain handlerChain, Channel channel) {
        if (jsonObject != null) {
            int type = jsonObject.getIntValue(Constants.deviceMsgField_Type);
            if (type == DeviceMsgType.LOGIN) {

            } else {
                handlerChain.handle(jsonObject, handlerChain, channel);
            }
        }
    }
}
