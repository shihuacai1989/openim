package com.openim.emulator.protobufclient;

import com.openim.common.im.bean.ProtobufDeviceMsg;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * Created by shihuacai on 2015/7/22.
 */
public class ProtobufChatClientHandler extends SimpleChannelInboundHandler<ProtobufDeviceMsg.DeviceMsg> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ProtobufDeviceMsg.DeviceMsg s) throws Exception {
        System.out.println(s);
    }
}