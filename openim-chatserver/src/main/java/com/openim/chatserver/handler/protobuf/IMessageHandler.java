package com.openim.chatserver.handler.protobuf;

import com.openim.common.im.bean.ProtobufDeviceMsg;
import io.netty.channel.Channel;

/**
 * Created by shihuacai on 2015/7/22.
 */
public interface IMessageHandler {
    void handle(ProtobufDeviceMsg.DeviceMsg deviceMsg, Channel channel);
}
