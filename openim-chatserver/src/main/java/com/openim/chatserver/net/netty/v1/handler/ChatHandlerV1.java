package com.openim.chatserver.net.netty.v1.handler;

import com.openim.chatserver.ChannelUtil;
import com.openim.chatserver.listener.ApplicationContextAware;
import com.openim.chatserver.net.netty.v1.INettyMessageHandlerV1;
import com.openim.common.im.bean.MessageType;
import com.openim.common.im.bean.ProtobufDeviceMsg;
import com.openim.common.mq.IMessageSender;
import com.openim.common.mq.constants.MQConstants;
import io.netty.channel.Channel;
import io.netty.util.Attribute;

/**
 * Created by shihuacai on 2015/7/22.
 */
//@Component
public class ChatHandlerV1 implements INettyMessageHandlerV1 {
    //private IMessageDispatch messageDispatch;
    private IMessageSender messageSender;

    public ChatHandlerV1() {
        messageSender = ApplicationContextAware.getBean(IMessageSender.class);
    }

    @Override
    public void handle(Channel channel,ProtobufDeviceMsg.DeviceMsg deviceMsg) {
        int type = deviceMsg.getType();
        if (type == MessageType.CHAT) {
            Attribute<String> attribute = channel.attr(ChannelUtil.loginIdKey);
            ProtobufDeviceMsg.DeviceMsg.Builder builder = ProtobufDeviceMsg.DeviceMsg.newBuilder(deviceMsg).setFrom(attribute.get());
            messageSender.sendMessage(MQConstants.openimExchange, MQConstants.chatRouteKey, builder.build().toByteArray());
        }
    }
}
