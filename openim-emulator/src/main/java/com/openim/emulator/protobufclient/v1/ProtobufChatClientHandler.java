package com.openim.emulator.protobufclient.v1;

import com.google.protobuf.Descriptors;
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

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
            throws Exception {
        cause.printStackTrace();
        System.out.print("exceptionCaught");
        ctx.fireExceptionCaught(cause);
    }

    /**
     * 网络正常情况下，服务端主动关闭长连接后，该方法会触发
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.print("channelInactive");
        ctx.fireChannelInactive();

    }
}