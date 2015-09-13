package com.openim.chatserver.net.mina.v2;

import com.google.common.collect.Maps;
import com.openim.chatserver.net.NetMessageDispatch;
import com.openim.chatserver.bean.MinaSession;
import com.openim.chatserver.bean.Session;
import com.openim.chatserver.net.handler.v2.IMessageHandlerV2;
import com.openim.common.im.annotation.HandleGroup;
import com.openim.common.im.bean.ExchangeMessage;
import com.openim.common.im.bean.MessageType;
import com.openim.common.im.bean.protbuf.ProtobufLoginMessage;
import org.apache.mina.core.session.IoSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;

import java.util.Map;

/**
 * Created by shihc on 2015/9/1.
 */
public class MinaMessageDispatchV2 extends NetMessageDispatch<IoSession, ExchangeMessage> implements InitializingBean {

    private static final Logger LOG = LoggerFactory.getLogger(MinaMessageDispatchV2.class);

    /*@Autowired
    private LoginHandlerV2 loginHandlerV2;

    @Autowired
    private LogoutHandlerV2 logoutHandlerV2;

    @Autowired
    private ChatHandlerV2 chatHandlerV2;*/

    private static final Map<Integer, IMessageHandlerV2> handlerMap = Maps.newHashMap();

    @Override
    public void dispatch(IoSession ioSession, ExchangeMessage exchangeMessage) {
        int type = exchangeMessage.getType();
        Session session = null;
        if(type == MessageType.LOGIN){
            ProtobufLoginMessage.LoginMessage connectMessage = exchangeMessage.getMessageLite();
            String loginId = connectMessage.getLoginId();
            session = new MinaSession(loginId, ioSession);
            ioSession.setAttribute("session", session);
        }else if(type == MessageType.CHAT){
            session =  (Session)ioSession.getAttribute("session");
        }else if(type == MessageType.LOGOUT){
            session =  (Session)ioSession.getAttribute("session");
        }else{
            LOG.error("无法处理的消息类型" + exchangeMessage.toString());
        }


        //终端连接成功后，还未登陆，就断开了
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
        registerHandler(HandleGroup.CHAT_SERVER_NiO_HANDLER, handlerMap);

        /*handlerMap.put(MessageType.LOGIN, loginHandlerV2);
        handlerMap.put(MessageType.LOGOUT, logoutHandlerV2);
        handlerMap.put(MessageType.CHAT, chatHandlerV2);*/
    }
}
