package com.openim.emulator.server;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;

/**
 * Created by shihuacai on 2015/7/22.
 */
@Deprecated
public class SimpleChatServerInitializer extends
        ChannelInitializer<SocketChannel> {

    //新客户端建立连接，调用一次该方法
    @Override
    public void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
/*
        pipeline.addLast("framer", new DelimiterBasedFrameDecoder(8192, Delimiters.lineDelimiter()));
        pipeline.addLast("decoder", new StringDecoder());
        pipeline.addLast("encoder", new StringEncoder());
        pipeline.addLast("handler", new SimpleChatServerHandler());
*/
        //System.out.println("SimpleChatClient:" + ch.remoteAddress() + "连接上");

        pipeline.addLast("decoder", new ObjectDecoder(ClassResolvers.cacheDisabled(this.getClass().getClassLoader())));
        pipeline.addLast("encoder", new ObjectEncoder());

        // 自己的逻辑Handler
        pipeline.addLast("handler", new SimpleChatServerHandler());
    }
}
