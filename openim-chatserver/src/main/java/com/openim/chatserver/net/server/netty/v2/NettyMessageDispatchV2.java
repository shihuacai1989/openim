package com.openim.chatserver.net.server.netty.v2;

import com.openim.chatserver.net.server.netty.INettyMessageDispatch;
import com.openim.chatserver.net.server.netty.v2.hander.ChatHandlerV2;
import com.openim.chatserver.net.server.netty.v2.hander.LoginHandlerV2;
import com.openim.chatserver.net.server.netty.v2.hander.LogoutHandlerV2;
import com.openim.common.im.bean.ExchangeMessage;
import com.openim.common.im.bean.MessageType;
import io.netty.channel.ChannelHandlerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by shihc on 2015/8/31.
 */
public class NettyMessageDispatchV2 implements INettyMessageDispatch {

    private static final Logger LOG = LoggerFactory.getLogger(NettyMessageDispatchV2.class);
    /*private static LoginHandler loginHandler = new LoginHandler();
    private static LogoutHandler logoutHandler = new LogoutHandler();
    private static ChatHandler chatHandler = new ChatHandler();*/

    private static final Map<Integer, INettyMessageHandlerV2> handlerMap = new HashMap<Integer, INettyMessageHandlerV2>(){
        {
            put(MessageType.LOGIN, new LoginHandlerV2());
            put(MessageType.LOGOUT, new LogoutHandlerV2());
            put(MessageType.CHAT, new ChatHandlerV2());
        }
    };

    @Override
    public void dispatch(ChannelHandlerContext ctx, ExchangeMessage msg) {
        INettyMessageHandlerV2 messageHandler = handlerMap.get(msg.getType());
        if(messageHandler != null){
            messageHandler.handle(ctx.channel(), msg);
        }else{
            LOG.error("未找到的消息处理器: " + msg);
        }

        /*int type = msg.getType();
        if (type == MessageType.LOGIN) {
            loginHandler.handle(msg, ctx.channel());
        } else if (type == MessageType.LOGOUT) {
            logoutHandler.handle(msg, ctx.channel());
        } else if (type == MessageType.CHAT) {
            chatHandler.handle(msg, ctx.channel());
        }*/
    }
}
