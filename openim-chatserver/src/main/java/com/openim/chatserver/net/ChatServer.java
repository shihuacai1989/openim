package com.openim.chatserver.net;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Created by shihuacai on 2015/7/20.
 * 终端设备聊天信息通道
 */
@Component
public class ChatServer implements InitializingBean {

    private static final Logger LOG = LoggerFactory.getLogger(ChatServer.class);

    @Value("${chat.port}")
    private int port;

    private void startServer(){
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ProtobufChatServerInitializer())
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
}
