package com.openim.server.net;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.Delimiters;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetAddress;

/**
 * Created by shihuacai on 2015/7/20.
 */
public class ServerChannel {

    private static final Logger LOG = LoggerFactory.getLogger(ServerChannel.class);

    public void startServer(int port){
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup);
            b.channel(NioServerSocketChannel.class);
            b.childHandler(new HelloServerInitializer());

            // 服务器绑定端口监听
            ChannelFuture f = b.bind(port).sync();
            // 监听服务器关闭监听
            f.channel().closeFuture().sync();

            // 可以简写为
            /* b.bind(portNumber).sync().channel().closeFuture().sync(); */
        } catch (InterruptedException e) {
            LOG.error(e.toString());
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

    private class HelloServerInitializer extends ChannelInitializer<SocketChannel> {

        @Override
        protected void initChannel(SocketChannel ch) throws Exception {
            ChannelPipeline pipeline = ch.pipeline();

            // 以("\n")为结尾分割的 解码器
            pipeline.addLast("framer", new DelimiterBasedFrameDecoder(8192, Delimiters.lineDelimiter()));

            // 字符串解码 和 编码
            pipeline.addLast("decoder", new StringDecoder());
            pipeline.addLast("encoder", new StringEncoder());

            // 自己的逻辑Handler
            pipeline.addLast("handler", new HelloServerHandler());
        }
    }

    private class HelloServerHandler extends SimpleChannelInboundHandler<String> {

        @Override
        protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
            // 收到消息直接打印输出
            System.out.println(ctx.channel().remoteAddress() + " Say : " + msg);

            // 返回客户端消息 - 我已经接收到了你的消息
            ctx.writeAndFlush("Received your message !\n");
        }

        /**
         *
         * 覆盖 channelActive 方法 在channel被启用的时候触发 (在建立连接的时候)
         *
         * channelActive 和 channelInActive 在后面的内容中讲述，这里先不做详细的描述
         * */
        @Override
        public void channelActive(ChannelHandlerContext ctx) throws Exception {

            System.out.println("RamoteAddress : " + ctx.channel().remoteAddress() + " active !");

            ctx.writeAndFlush("Welcome to " + InetAddress.getLocalHost().getHostName() + " service!\n");

            super.channelActive(ctx);
        }
    }
}
