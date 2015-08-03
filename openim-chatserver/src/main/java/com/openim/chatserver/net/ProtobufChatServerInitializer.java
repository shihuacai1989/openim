package com.openim.chatserver.net;

import com.openim.common.im.bean.ProtobufDeviceMsg;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;

/**
 * Created by shihuacai on 2015/8/2.
 */
public class ProtobufChatServerInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        pipeline.addLast("encoder", new ProtobufEncoder());
        pipeline.addLast("decoder", new ProtobufDecoder(ProtobufDeviceMsg.DeviceMsg.getDefaultInstance()));
        pipeline.addLast("handler", new ProtobufChatServerHandler());

    }
}