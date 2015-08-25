package com.openim.chatserver.net.server.netty.v1;

import com.openim.common.im.bean.ProtobufDeviceMsg;
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
@Deprecated
public class ProtobufChatServerInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        //配置服务端监听读超时，即无法收到客户端发的心跳信息的最长时间间隔：2分钟
        pipeline.addLast("ping", new IdleStateHandler(120, 0, 0,
                TimeUnit.SECONDS));
        //存在粘包的
        /*pipeline.addLast("encoder", new ProtobufEncoder());
        pipeline.addLast("decoder", new ProtobufDecoder(ProtobufDeviceMsg.DeviceMsg.getDefaultInstance()));
        pipeline.addLast("handler", new ProtobufChatServerHandler());*/

        //解决粘包的问题
        pipeline.addLast("frameEncoder", new ProtobufVarint32LengthFieldPrepender());
        pipeline.addLast("encoder", new ProtobufEncoder());

        pipeline.addLast("frameDecoder", new ProtobufVarint32FrameDecoder());
        pipeline.addLast("decoder", new ProtobufDecoder(ProtobufDeviceMsg.DeviceMsg.getDefaultInstance()));

        pipeline.addLast("handler", new ProtobufChatServerHandler());

    }
}