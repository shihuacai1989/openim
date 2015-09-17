package com.openim.emulator.client.netty.v2;

import com.openim.common.im.codec.netty.OpenIMProtobufDecoderV2;
import com.openim.common.im.codec.netty.OpenIMProtobufEncoderV2;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.InsecureTrustManagerFactory;

/**
 * Created by shihuacai on 2015/7/22.
 */
public class ProtobufChatClientInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    public void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();

        final SslContext sslCtx = SslContextBuilder.forClient()
                .trustManager(InsecureTrustManagerFactory.INSTANCE).build();
        pipeline.addLast(sslCtx.newHandler(ch.alloc(), ChatClient.SERVER_HOST, ChatClient.SERVER_PORT));

        pipeline.addLast("encoder", new OpenIMProtobufEncoderV2());
        pipeline.addLast("decoder", new OpenIMProtobufDecoderV2());
        pipeline.addLast("handler", new ProtobufChatClientHandler());

    }
}