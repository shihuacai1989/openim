package com.openim.emulator.client.netty.jdk;

import com.openim.common.im.bean.DeviceMsg;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * Created by shihuacai on 2015/7/22.
 */
public class SimpleChatClientHandler extends SimpleChannelInboundHandler<DeviceMsg> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, DeviceMsg s) throws Exception {
        System.out.println(s);
    }
}