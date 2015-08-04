package com.openim.chatserver.handler.protobuf;

import com.openim.chatserver.ChannelUtil;
import com.openim.chatserver.listener.ApplicationContextAware;
import com.openim.common.im.bean.DeviceMsgType;
import com.openim.common.im.bean.ProtobufDeviceMsg;
import com.openim.common.mq.IMessageSender;
import com.openim.common.mq.constants.MQConstants;
import io.netty.channel.Channel;

/**
 * Created by shihuacai on 2015/7/22.
 */
//@Component
public class LogoutHandler implements IMessageHandler {
    private IMessageSender messageSender;

    public LogoutHandler() {
        messageSender = ApplicationContextAware.getBean(IMessageSender.class);
    }

    @Override
    public void handle(ProtobufDeviceMsg.DeviceMsg deviceMsg, Channel channel) {
        int type = deviceMsg.getType();
        if (type == DeviceMsgType.LOGOUT) {
            messageSender.sendMessage(MQConstants.openimExchange, MQConstants.logoutRouteKey, deviceMsg.toByteArray());
            ChannelUtil.remove(channel);
        }
    }
}
