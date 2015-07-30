package com.openim.chatserver.net;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.openim.chatserver.handler.impl.HandlerChain;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.net.InetAddress;

/**
 * Created by shihuacai on 2015/7/21.
 */
//@Component
public class ChatHandler extends SimpleChannelInboundHandler<String> {
    private static final Logger LOG = LoggerFactory.getLogger(ChatHandler.class);

    public static ChannelGroup channels = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    //private static Map<String, Channel> map = new HashMap<String, Channel>();

    //private String loginName;

    private HandlerChain handlerChain;

    public ChatHandler(){
        handlerChain = new HandlerChain();
    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {  // (2)
        Channel incoming = ctx.channel();
        for (Channel channel : channels) {
            channel.writeAndFlush("[SERVER] - " + incoming.remoteAddress() + " 加入\n");
        }
        channels.add(ctx.channel());
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {  // (3)
        Channel incoming = ctx.channel();
        for (Channel channel : channels) {
            channel.writeAndFlush("[SERVER] - " + incoming.remoteAddress() + " 离开\n");
        }
        channels.remove(ctx.channel());
    }


    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        LOG.debug(msg);
        try {
            if(!StringUtils.isEmpty(msg)){
                //((SocketChannel)ctx.channel()).
                //HandlerChain handlerChain = new HandlerChain();
                JSONObject msgJson = JSON.parseObject(msg);
                handlerChain.handle(msgJson, handlerChain, ctx.channel());
            }
        }catch (Exception e){
            LOG.error(e.toString());
        }

        //System.out.println(ctx.channel().remoteAddress() + " Say : " + msg);

        // 返回客户端消息 - 我已经接收到了你的消息
        //ctx.writeAndFlush("Received your message !\n");
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

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception { // (6)
        Channel incoming = ctx.channel();
        System.out.println("SimpleChatClient:" + incoming.remoteAddress() + "掉线");
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) { // (7)
        Channel incoming = ctx.channel();
        System.out.println("SimpleChatClient:"+incoming.remoteAddress()+"异常");
        // 当出现异常就关闭连接
        cause.printStackTrace();
        ctx.close();
    }
}
