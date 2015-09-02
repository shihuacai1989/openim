package com.openim.chatserver.net.netty.v2;

import com.google.common.collect.Maps;
import com.openim.chatserver.net.INetMessageDispatch;
import com.openim.chatserver.net.bean.NettySession;
import com.openim.chatserver.net.bean.Session;
import com.openim.chatserver.net.handler.v2.ChatHandlerV2;
import com.openim.chatserver.net.handler.v2.IMessageHandlerV2;
import com.openim.chatserver.net.handler.v2.LoginHandlerV2;
import com.openim.chatserver.net.handler.v2.LogoutHandlerV2;
import com.openim.common.im.bean.ExchangeMessage;
import com.openim.common.im.bean.MessageType;
import com.openim.common.im.bean.protbuf.ProtobufConnectMessage;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.Attribute;
import io.netty.util.AttributeKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

/**
 * Created by shihc on 2015/8/31.
 */

public class NettyMessageDispatchV2 implements INetMessageDispatch<ChannelHandlerContext, ExchangeMessage>, InitializingBean {

    private static final Logger LOG = LoggerFactory.getLogger(NettyMessageDispatchV2.class);
    /*private static LoginHandler loginHandler = new LoginHandler();
    private static LogoutHandler logoutHandler = new LogoutHandler();
    private static ChatHandler chatHandler = new ChatHandler();*/

    public static final AttributeKey<Session> sessionKey = AttributeKey.valueOf("session");

    @Autowired
    private LoginHandlerV2 loginHandlerV2;

    @Autowired
    private LogoutHandlerV2 logoutHandlerV2;

    @Autowired
    private ChatHandlerV2 chatHandlerV2;

    private static final Map<Integer, IMessageHandlerV2> handlerMap = Maps.newHashMap();

    @Override
    public void dispatch(ChannelHandlerContext ctx, ExchangeMessage exchangeMessage) {
        int type = exchangeMessage.getType();
        Session session = null;
        if(type == MessageType.LOGIN){
            ProtobufConnectMessage.ConnectMessage connectMessage = exchangeMessage.getMessageLite();
            String loginId = connectMessage.getLoginId();
            session = new NettySession(loginId, ctx.channel());
            Attribute<Session> attr = ctx.channel().attr(sessionKey);
            attr.set(session);
        }else if(type == MessageType.CHAT){
            session = ctx.channel().attr(sessionKey).get();
        }else if(type == MessageType.LOGOUT){
            session = ctx.channel().attr(sessionKey).get();
        }else{
            LOG.error("无法处理的消息类型" + exchangeMessage.toString());
            return;
        }
        //终端连接成功后，还未登陆，变断开了
        if(session != null){
            IMessageHandlerV2 messageHandler = handlerMap.get(type);
            if(messageHandler != null){
                messageHandler.handle(session, exchangeMessage);
            }else{
                LOG.error("未找到的消息处理器: " + exchangeMessage);
            }
        }

    }

    @Override
    public void afterPropertiesSet() throws Exception {
        handlerMap.put(MessageType.LOGIN, loginHandlerV2);
        handlerMap.put(MessageType.LOGOUT, logoutHandlerV2);
        handlerMap.put(MessageType.CHAT, chatHandlerV2);
    }
}
