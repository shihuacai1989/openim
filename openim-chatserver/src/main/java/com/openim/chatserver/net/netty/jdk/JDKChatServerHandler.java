package com.openim.chatserver.net.netty.jdk;

import com.openim.chatserver.ChannelUtil;
import com.openim.chatserver.net.netty.jdk.handler.HandlerChain;
import com.openim.common.im.bean.DeviceMsg;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by shihuacai on 2015/7/21.
 * 不跨平台
 */
@Deprecated
public class JDKChatServerHandler extends SimpleChannelInboundHandler<DeviceMsg> {
    private static final Logger LOG = LoggerFactory.getLogger(JDKChatServerHandler.class);

    //public static ChannelGroup channels = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    //private static Map<String, Channel> map = new HashMap<String, Channel>();

    //private String loginName;

    private HandlerChain handlerChain;

    public JDKChatServerHandler() {
        handlerChain = new HandlerChain();
    }

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
    protected void channelRead0(ChannelHandlerContext ctx, DeviceMsg msg) throws Exception {
        try {
            handlerChain.handle(msg, handlerChain, ctx.channel());
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
}
