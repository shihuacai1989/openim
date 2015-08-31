package com.openim.emulator.client.netty.v2;

import com.openim.common.im.codec.netty.OpenIMProtobufDecoderV2;
import com.openim.common.im.codec.netty.OpenIMProtobufEncoderV2;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;

/**
 * Created by shihuacai on 2015/7/22.
 */
public class ProtobufChatClientInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    public void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();

        pipeline.addLast("encoder", new OpenIMProtobufEncoderV2());
        pipeline.addLast("decoder", new OpenIMProtobufDecoderV2());
        pipeline.addLast("handler", new ProtobufChatClientHandler());

    }
}