package com.openim.chatserver.net.netty.v1;

import com.openim.chatserver.ChannelUtil;
import com.openim.chatserver.handler.netty.v1.LoginHandler;
import com.openim.chatserver.handler.netty.v1.LogoutHandler;
import com.openim.chatserver.handler.netty.v1.SendHandler;
import com.openim.common.im.bean.DeviceMsgType;
import com.openim.common.im.bean.ProtobufDeviceMsg;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.timeout.IdleStateEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by shihuacai on 2015/7/21.
 */
//@Component
public class ProtobufChatServerHandler extends SimpleChannelInboundHandler<ProtobufDeviceMsg.DeviceMsg> {
    private static final Logger LOG = LoggerFactory.getLogger(ProtobufChatServerHandler.class);

    //private HandlerChain handlerChain;
    private static LoginHandler loginHandler = new LoginHandler();
    private static LogoutHandler logoutHandler = new LogoutHandler();
    private static SendHandler sendHandler = new SendHandler();

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
        ChannelUtil.remove(ctx.channel());
    }


    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ProtobufDeviceMsg.DeviceMsg msg) throws Exception {
        try {
            System.out.print(msg);
            int type = msg.getType();
            if (type == DeviceMsgType.LOGIN) {
                loginHandler.handle(msg, ctx.channel());
            } else if (type == DeviceMsgType.LOGOUT) {
                logoutHandler.handle(msg, ctx.channel());
            } else if (type == DeviceMsgType.CHAT) {
                sendHandler.handle(msg, ctx.channel());
            }
            //handlerChain.handle(msg, handlerChain, ctx.channel());
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
            ChannelUtil.remove(ctx.channel());
        }
        super.userEventTriggered(ctx, evt);
    }
}
