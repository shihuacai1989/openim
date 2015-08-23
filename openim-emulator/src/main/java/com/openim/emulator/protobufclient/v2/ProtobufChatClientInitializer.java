package com.openim.emulator.protobufclient.v2;

import com.openim.common.im.bean.ProtobufDeviceMsg;
import com.openim.common.im.codec.netty.OpenIMProtobufDecoder;
import com.openim.common.im.codec.netty.OpenIMProtobufEncoder;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32LengthFieldPrepender;

/**
 * Created by shihuacai on 2015/7/22.
 */
public class ProtobufChatClientInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    public void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();

        pipeline.addLast("encoder", new OpenIMProtobufEncoder());
        pipeline.addLast("decoder", new OpenIMProtobufDecoder());
        pipeline.addLast("handler", new ProtobufChatClientHandler());

    }
}