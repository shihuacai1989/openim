package com.openim.chatserver.net.server.netty.v2;

import com.google.protobuf.MessageLite;
import com.openim.chatserver.net.server.IChatServer;
import com.openim.chatserver.net.server.netty.INettyMessageDispatch;
import com.openim.common.im.bean.ExchangeMessage;
import com.openim.common.im.bean.MessageType;
import com.openim.common.im.bean.protbuf.ProtobufDisconnectMessage;
import com.openim.common.im.codec.netty.v2.OpenIMProtobufDecoder;
import com.openim.common.im.codec.netty.v2.OpenIMProtobufEncoder;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.timeout.IdleStateEvent;
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

    private static class ChatServerHandlerV2 extends SimpleChannelInboundHandler<ExchangeMessage> {
        private static final Logger LOG = LoggerFactory.getLogger(ChatServerHandlerV2.class);

        private static final INettyMessageDispatch messageDispatch = new NettyMessageDispatchV2();

    /*private static LoginHandler loginHandler = new LoginHandler();
    private static LogoutHandler logoutHandler = new LogoutHandler();
    private static ChatHandler chatHandler = new ChatHandler();*/

        @Override
        public void handlerAdded(ChannelHandlerContext ctx) throws Exception {  // (2)
        /*Channel incoming = ctx.channel();
        for (Channel channel : channels) {
            channel.writeAndFlush("[SERVER] - " + incoming.remoteAddress() + " 加入\n");
        }
        channels.add(ctx.channel());*/
        }

        @Override
        public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {  // (3)
            handleLogout(ctx);
        }


        @Override
        protected void channelRead0(ChannelHandlerContext ctx, ExchangeMessage msg) throws Exception {
            try {
                messageDispatch.dispatch(ctx, msg);
            /*System.out.print(msg);
            int type = msg.getType();
            if (type == MessageType.LOGIN) {
                loginHandler.handle(msg, ctx.channel());
            } else if (type == MessageType.LOGOUT) {
                logoutHandler.handle(msg, ctx.channel());
            } else if (type == MessageType.CHAT) {
                chatHandler.handle(msg, ctx.channel());
            }*/
            } catch (Exception e) {
                LOG.error(e.toString());
            }
        }

        /**
         * 覆盖 channelActive 方法 在channel被启用的时候触发 (在建立连接的时候)
         * <p/>
         * channelActive 和 channelInActive 在后面的内容中讲述，这里先不做详细的描述
         */
        @Override
        public void channelActive(ChannelHandlerContext ctx) throws Exception {
            super.channelActive(ctx);
        }

        @Override
        public void channelInactive(ChannelHandlerContext ctx) throws Exception { // (6)
        /*Channel incoming = ctx.channel();
        System.out.println("SimpleChatClient:" + incoming.remoteAddress() + "掉线");*/
        }

        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) { // (7)
            LOG.error(cause.toString());
        /*Channel incoming = ctx.channel();
        System.out.println("SimpleChatClient:"+incoming.remoteAddress()+"异常");
        // 当出现异常就关闭连接
        cause.printStackTrace();*/
            ctx.close();
        }

        @Override
        public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
            if (evt instanceof IdleStateEvent) {
            /*IdleStateEvent event = (IdleStateEvent) evt;
            if (event.state().equals(IdleState.READER_IDLE)) {
                System.out.println("READER_IDLE");
                // 超时关闭channel
                ctx.close();
            } else if (event.state().equals(IdleState.WRITER_IDLE)) {
                System.out.println("WRITER_IDLE");
            } else if (event.state().equals(IdleState.ALL_IDLE)) {
                System.out.println("ALL_IDLE");
                // 发送心跳
                ctx.channel().write("ping");
            }*/
                System.out.println("READER_IDLE");
                //采用客户端向服务端发心跳的机制，服务端只需监听读心跳（即未读到客户端的心跳）
                handleLogout(ctx);
            }
            super.userEventTriggered(ctx, evt);
        }

        private void handleLogout(ChannelHandlerContext ctx){

            ExchangeMessage exchangeMessage = new ExchangeMessage();
            exchangeMessage.setType(MessageType.LOGOUT);

            MessageLite messageLite = ProtobufDisconnectMessage.DisconnectMessage.newBuilder().build();
            exchangeMessage.setMessageLite(messageLite);

            messageDispatch.dispatch(ctx, exchangeMessage);
            //logoutHandler.handle(exchangeMessage, ctx.channel());
        }
    }

}
