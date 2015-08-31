package com.openim.chatserver.net.server.netty.v2;

import com.openim.chatserver.net.server.IChatServer;
import com.openim.common.im.codec.netty.v2.OpenIMProtobufDecoder;
import com.openim.common.im.codec.netty.v2.OpenIMProtobufEncoder;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.timeout.IdleStateHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * Created by shihuacai on 2015/8/19.
 */
@Component
public class NettyChatServerV2 implements IChatServer {
    private static final Logger LOG = LoggerFactory.getLogger(NettyChatServerV2.class);

    @Value("${chat.port}")
    private int port;

    @Override
    public void startServer() {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChatServerInitializerV2())
                    .option(ChannelOption.SO_BACKLOG, 128)          // (5)
                    .childOption(ChannelOption.SO_KEEPALIVE, true); // (6)
            // 服务器绑定端口监听
            ChannelFuture f = bootstrap.bind(port).sync();
            // 监听服务器关闭监听
            //程序阻塞在此处，ApplicationListener不再调用
            f.channel().closeFuture().sync();

        } catch (InterruptedException e) {
            LOG.error(e.toString());
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        new Thread(new Runnable() {
            @Override
            public void run() {
                startServer();
            }
        }).start();

        LOG.info("推送服务启动完毕");
    }

    private class ChatServerInitializerV2 extends ChannelInitializer<SocketChannel> {

        @Override
        protected void initChannel(SocketChannel ch) throws Exception {
            ChannelPipeline pipeline = ch.pipeline();
            //配置服务端监听读超时，即无法收到客户端发的心跳信息的最长时间间隔：2分钟
            pipeline.addLast("ping", new IdleStateHandler(120, 0, 0, TimeUnit.SECONDS));

            pipeline.addLast("encoder", new OpenIMProtobufEncoder());
            pipeline.addLast("decoder", new OpenIMProtobufDecoder());
            pipeline.addLast("handler", new ChatServerHandlerV2());

        }
    }
}
