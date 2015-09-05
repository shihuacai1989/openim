package com.openim.chatserver.net.mina.v2;

import com.openim.chatserver.net.Constants;
import com.openim.chatserver.net.IChatServer;
import com.openim.chatserver.net.INetMessageDispatch;
import com.openim.common.im.bean.ExchangeMessage;
import com.openim.common.im.codec.mina.OpenIMProtobufDecoderV2;
import com.openim.common.im.codec.mina.OpenIMProtobufEncoderV2;
import org.apache.mina.core.service.IoAcceptor;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.net.InetSocketAddress;

/**
 * Created by shihuacai on 2015/8/19.
 */
public class MinaChatServerV2 implements IChatServer {

    private static final Logger LOG = LoggerFactory.getLogger(MinaChatServerV2.class);

    @Value("${chat.port}")
    private int port;

    @Autowired
    private INetMessageDispatch<IoSession, ExchangeMessage> messageDispatch;

    /*@Autowired
    private LoginHandlerV2 loginHandler;

    @Autowired
    private ChatHandlerV2 chatHandler;

    @Autowired
    private LogoutHandlerV2 logoutHandler;*/


    @Override
    public void startServer() {
        try {
            IoAcceptor acceptor = new NioSocketAcceptor();
            acceptor.getSessionConfig().setIdleTime(IdleStatus.BOTH_IDLE, Constants.IDLE_TIME);   //读写 通道均在10 秒内无任何操作就进入空闲
            // 指定protobuf的编码器和解码器
            acceptor.getFilterChain().addLast("codec",
                    new ProtocolCodecFilter(new OpenIMProtobufEncoderV2(), new OpenIMProtobufDecoderV2()));

            acceptor.setHandler(new TcpServerHandle());
            acceptor.bind(new InetSocketAddress(port));
        }catch (Exception e){
            e.printStackTrace();
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


    public class TcpServerHandle extends IoHandlerAdapter {

        @Override
        public void exceptionCaught(IoSession session, Throwable cause)
                throws Exception {
            cause.printStackTrace();
        }

        @Override
        public void messageReceived(IoSession session, Object message)
                throws Exception {
            ExchangeMessage exchangeMessage = (ExchangeMessage)message;
            messageDispatch.dispatch(session, exchangeMessage);
            //processMessage(session, exchangeMessage);
        }

        @Override
        public void sessionClosed(IoSession session) throws Exception {
            LOG.error("sessionClosed");
        }

        @Override
        public void sessionIdle(IoSession session, IdleStatus status) throws Exception {
            LOG.error("sessionIdle");
        }


        /*private void processMessage(IoSession iosession, ExchangeMessage exchangeMessage){
            int type = exchangeMessage.getType();
            if(type == MessageType.LOGIN){
                ProtobufConnectMessage.ConnectMessage connectMessage = exchangeMessage.getMessageLite();
                String loginId = connectMessage.getLoginId();
                Session session = new MinaSession(loginId, iosession);
                iosession.setAttribute("session", session);
                loginHandler.handle(session, exchangeMessage);
            }else if(type == MessageType.CHAT){
                Session session =  (Session)iosession.getAttribute("session");
                chatHandler.handle(session, exchangeMessage);
            }else if(type == MessageType.LOGOUT){
                Session session =  (Session)iosession.getAttribute("session");
                logoutHandler.handle(session, exchangeMessage);
            }else{
                LOG.error("无法处理的消息类型" + exchangeMessage.toString());
            }
        }*/
    }
}