package com.openim.chatserver.net.handler.netty.jdk;

import com.openim.chatserver.ChannelUtil;
import com.openim.chatserver.listener.ApplicationContextAware;
import com.openim.common.im.bean.DeviceMsg;
import com.openim.common.im.bean.MessageType;
import com.openim.common.mq.IMessageSender;
import com.openim.common.mq.constants.MQConstants;
import io.netty.channel.Channel;

/**
 * Created by shihuacai on 2015/7/22.
 */
@Deprecated
public class LogoutHandler implements IMessageHandler {
    private IMessageSender messageSender;

    public LogoutHandler() {
        messageSender = ApplicationContextAware.getBean(IMessageSender.class);
    }

    @Override
    public void handle(DeviceMsg jsonObject, HandlerChain handlerChain, Channel channel) {
        int type = jsonObject.getType();
        if (type == MessageType.LOGOUT) {
            messageSender.sendMessage(MQConstants.openimExchange, MQConstants.logoutRouteKey, jsonObject);
            ChannelUtil.remove(channel);
        } else {
            handlerChain.handle(jsonObject, handlerChain, channel);
        }
    }
}
