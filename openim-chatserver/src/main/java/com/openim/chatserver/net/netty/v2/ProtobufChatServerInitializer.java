package com.openim.chatserver.net.netty.v2;

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
import io.netty.handler.timeout.IdleStateHandler;

import java.util.concurrent.TimeUnit;

/**
 * Created by shihuacai on 2015/8/2.
 */
public class ProtobufChatServerInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        //配置服务端监听读超时，即无法收到客户端发的心跳信息的最长时间间隔：2分钟
        pipeline.addLast("ping", new IdleStateHandler(120, 0, 0, TimeUnit.SECONDS));

        pipeline.addLast("encoder", new OpenIMProtobufEncoder());
        pipeline.addLast("decoder", new OpenIMProtobufDecoder());
        pipeline.addLast("handler", new ProtobufChatServerHandler());

    }
}