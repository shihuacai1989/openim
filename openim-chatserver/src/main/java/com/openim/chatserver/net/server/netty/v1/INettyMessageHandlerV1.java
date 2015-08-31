package com.openim.chatserver.net.server.netty.v1;

import com.openim.chatserver.net.server.IMessageHandler;
import com.openim.common.im.bean.ExchangeMessage;
import com.openim.common.im.bean.ProtobufDeviceMsg;
import io.netty.channel.Channel;

/**
 * Created by shihuacai on 2015/7/22.
 */
public interface INettyMessageHandlerV1 extends IMessageHandler<Channel, ProtobufDeviceMsg.DeviceMsg> {
    void handle(Channel channel, ProtobufDeviceMsg.DeviceMsg message);
}
