package com.openim.chatserver.net;

import com.openim.common.im.exception.NotCompletionException;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;

/**
 * Created by shihuacai on 2015/8/2.
 */
public class ProtobufChatServerInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        throw new NotCompletionException("ProtobufChatServerInitializer待实现");

        // 以("\n")为结尾分割的 解码器
        //pipeline.addLast("framer", new DelimiterBasedFrameDecoder(8192, Delimiters.lineDelimiter()));

        // 字符串解码 和 编码
        /*pipeline.addLast("decoder", new ObjectDecoder(ClassResolvers.cacheDisabled(this.getClass().getClassLoader())));
        pipeline.addLast("encoder", new ObjectEncoder());

        // 自己的逻辑Handler
        pipeline.addLast("handler", new JDKChatServerHandler());*/
    }
}