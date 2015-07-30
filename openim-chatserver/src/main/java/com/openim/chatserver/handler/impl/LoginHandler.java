package com.openim.chatserver.handler.impl;

import com.alibaba.fastjson.JSONObject;
import com.openim.chatserver.ChannelUtil;
import com.openim.chatserver.handler.IMessageHandler;
import com.openim.common.im.DeviceMsgField;
import com.openim.common.im.DeviceMsgType;
import io.netty.channel.Channel;
import io.netty.util.Attribute;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by shihuacai on 2015/7/22.
 */
public class LoginHandler implements IMessageHandler {

    private static final Logger LOG = LoggerFactory.getLogger(LoginHandler.class);

    @Override
    public void handle(JSONObject jsonObject, HandlerChain handlerChain, Channel channel) {
        if (jsonObject != null) {
            int type = jsonObject.getIntValue(DeviceMsgField.type);
            if (type == DeviceMsgType.LOGIN) {
                String loginId = jsonObject.getString(DeviceMsgField.loginId);
                String pwd = jsonObject.getString(DeviceMsgField.pwd);
                //后期完成登录验证
                Attribute<String> attribute = channel.attr(key);
                attribute.set(loginId);
                ChannelUtil.add(loginId, channel);
            } else {
                handlerChain.handle(jsonObject, handlerChain, channel);
            }
        }
    }
}
