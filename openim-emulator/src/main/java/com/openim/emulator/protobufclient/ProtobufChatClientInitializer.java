package com.openim.emulator.protobufclient;

import com.openim.common.im.bean.ProtobufDeviceMsg;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;

/**
 * Created by shihuacai on 2015/7/22.
 */
public class ProtobufChatClientInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    public void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();

        ch.pipeline().addLast("encoder", new ProtobufEncoder());

        ch.pipeline().addLast("decoder", new ProtobufDecoder(ProtobufDeviceMsg.DeviceMsg.getDefaultInstance()));

        ch.pipeline().addLast("handler", new ProtobufChatClientHandler());

    }
}