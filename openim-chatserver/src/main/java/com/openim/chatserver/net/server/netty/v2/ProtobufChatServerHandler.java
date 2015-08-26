package com.openim.chatserver.net.server.netty.v2;

import com.google.protobuf.MessageLite;
import com.openim.chatserver.net.handler.netty.v2.ChatHandler;
import com.openim.chatserver.net.handler.netty.v2.LoginHandler;
import com.openim.chatserver.net.handler.netty.v2.LogoutHandler;
import com.openim.common.im.bean.ExchangeMessage;
import com.openim.common.im.bean.MessageType;
import com.openim.common.im.bean.protbuf.ProtobufDisconnectMessage;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.timeout.IdleStateEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by shihuacai on 2015/7/21.
 */
//@Component
public class ProtobufChatServerHandler extends SimpleChannelInboundHandler<ExchangeMessage> {
    private static final Logger LOG = LoggerFactory.getLogger(ProtobufChatServerHandler.class);

    //private HandlerChain handlerChain;
    private static LoginHandler loginHandler = new LoginHandler();
    private static LogoutHandler logoutHandler = new LogoutHandler();
    private static ChatHandler chatHandler = new ChatHandler();

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
            System.out.print(msg);
            int type = msg.getType();
            if (type == MessageType.LOGIN) {
                loginHandler.handle(msg, ctx.channel());
            } else if (type == MessageType.LOGOUT) {
                logoutHandler.handle(msg, ctx.channel());
            } else if (type == MessageType.CHAT) {
                chatHandler.handle(msg, ctx.channel());
            }
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

        logoutHandler.handle(exchangeMessage, ctx.channel());
    }
}
