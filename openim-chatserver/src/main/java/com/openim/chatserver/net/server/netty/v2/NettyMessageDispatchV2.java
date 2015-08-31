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
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by shihc on 2015/8/31.
 */
public class NettyMessageDispatchV2 implements INettyMessageDispatch, InitializingBean {

    private static final Logger LOG = LoggerFactory.getLogger(NettyMessageDispatchV2.class);
    /*private static LoginHandler loginHandler = new LoginHandler();
    private static LogoutHandler logoutHandler = new LogoutHandler();
    private static ChatHandler chatHandler = new ChatHandler();*/

    @Autowired
    private LoginHandlerV2 loginHandlerV2;

    @Autowired
    private LogoutHandlerV2 logoutHandlerV2;

    @Autowired
    private ChatHandlerV2 chatHandlerV2;

    private static final Map<Integer, INettyMessageHandlerV2> handlerMap = new HashMap<Integer, INettyMessageHandlerV2>();

    @Override
    public void dispatch(ChannelHandlerContext ctx, ExchangeMessage msg) {
        INettyMessageHandlerV2 messageHandler = handlerMap.get(msg.getType());
        if(messageHandler != null){
            messageHandler.handle(ctx.channel(), msg);
        }else{
            LOG.error("未找到的消息处理器: " + msg);
        }
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        handlerMap.put(MessageType.LOGIN, loginHandlerV2);
        handlerMap.put(MessageType.LOGOUT, logoutHandlerV2);
        handlerMap.put(MessageType.CHAT, chatHandlerV2);
    }
}
