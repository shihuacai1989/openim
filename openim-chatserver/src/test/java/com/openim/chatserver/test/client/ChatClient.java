package com.openim.chatserver.test.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * Created by shihuacai on 2015/7/22.
 */
public class ChatClient {
    public static void main(String[] args) throws Exception {
        //测试服务
        //new ChatClient("localhost", 8080).run();
        //正式服务
        new ChatClient("localhost", 9000).run();
    }

    private final String host;
    private final int port;

    public ChatClient(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public void run() throws Exception {
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap bootstrap = new Bootstrap()
                    .group(group)
                    .channel(NioSocketChannel.class)
                    .handler(new SimpleChatClientInitializer());
            Channel channel = bootstrap.connect(host, port).sync().channel();
            BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
            while (true) {
                channel.writeAndFlush(in.readLine() + "\r\n");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            group.shutdownGracefully();
        }

    }
}