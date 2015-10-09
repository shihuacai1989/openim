package com.openim.chatserver.net.mina.v2;

import com.openim.chatserver.net.IChatServer;
import com.openim.chatserver.net.NetMessageDispatch;
import com.openim.common.im.bean.ExchangeMessage;
import com.openim.common.im.bean.MessageType;
import com.openim.common.im.bean.protbuf.ProtobufLogoutMessage;
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

import javax.transaction.NotSupportedException;
import java.net.InetSocketAddress;

/**
 * Created by shihuacai on 2015/8/19.
 */
public class MinaChatServerV2 implements IChatServer {

    private static final Logger LOG = LoggerFactory.getLogger(MinaChatServerV2.class);

    @Value("${chat.port}")
    private int port;

    @Value("${ssl}")
    private boolean ssl;

    @Autowired
    private NetMessageDispatch<IoSession, ExchangeMessage> messageDispatch;

    /*@Autowired
    private LoginHandlerV2 loginHandler;

    @Autowired
    private ChatHandlerV2 chatHandler;

    @Autowired
    private LogoutHandlerV2 logoutHandler;*/


    @Override
    public void startServer() {
        try {
            if (ssl) {
                throw new NotSupportedException("暂未实现mina框架的ssl通信，如何与netty兼容");
            }
            IoAcceptor acceptor = new NioSocketAcceptor();
            acceptor.getSessionConfig().setIdleTime(IdleStatus.BOTH_IDLE, 15);   //读写 通道均在10 秒内无任何操作就进入空闲
            // 指定protobuf的编码器和解码器
            acceptor.getFilterChain().addLast("codec",
                    new ProtocolCodecFilter(new OpenIMProtobufEncoderV2(), new OpenIMProtobufDecoderV2()));

            acceptor.setHandler(new TcpServerHandle());
            acceptor.bind(new InetSocketAddress(port));

            /*JmxService.registerBean(acceptor);*/
            /*IoServiceMBean acceptorMBean = new IoServiceMBean(acceptor);
            MBeanServer mBeanServer = ManagementFactory.getPlatformMBeanServer();
            // create a JMX ObjectName.  This has to be in a specific format.
            ObjectName acceptorName = new ObjectName(acceptor.getClass().getPackage().getName() +
                    ":type=acceptor,name=" + acceptor.getClass().getSimpleName());

            // register the bean on the MBeanServer.  Without this line, no JMX will happen for
            // this acceptor.
            mBeanServer.registerMBean(acceptorMBean, acceptorName);*/
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void stopServer() {

    }

    @Override
    public void afterPropertiesSet() throws Exception {
        /*new Thread(new Runnable() {
            @Override
            public void run() {
                startServer();
            }
        }).start();*/
        startServer();
        LOG.info("推送服务启动完毕");
    }


    public class TcpServerHandle extends IoHandlerAdapter {

        /**
         * 执行了该方法后，还会执行sessionClosed方法
         *
         * @param session
         * @param cause
         * @throws Exception
         */
        @Override
        public void exceptionCaught(IoSession session, Throwable cause)
                throws Exception {
            LOG.error(cause.toString());
            closeSession(session);
        }

        @Override
        public void messageReceived(IoSession session, Object message)
                throws Exception {
            ExchangeMessage exchangeMessage = (ExchangeMessage) message;
            messageDispatch.dispatch(session, exchangeMessage);
            //processMessage(session, exchangeMessage);
        }

        @Override
        public void sessionClosed(IoSession session) throws Exception {
            //其他地方调用session.close()方法，也会引起sessionClosed()方法的调用
            //此处不再处理
        }

        /**
         * 执行了该方法后，不会执行sessionClosed方法
         *
         * @param session
         * @param status
         * @throws Exception
         */
        @Override
        public void sessionIdle(IoSession session, IdleStatus status) throws Exception {
            //如果发生了idle，但是不做任何处理，则会反复调用
            closeSession(session);
        }

        private void closeSession(IoSession session) {
            int type = MessageType.LOGOUT;
            ProtobufLogoutMessage.LogoutMessage logoutMessage = ProtobufLogoutMessage.LogoutMessage.newBuilder().build();
            ExchangeMessage exchangeMessage = new ExchangeMessage();
            exchangeMessage.setMessageLite(logoutMessage);
            exchangeMessage.setType(type);

            messageDispatch.dispatch(session, exchangeMessage);
            LOG.error("sessionClosed");
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
