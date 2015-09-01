package com.openim.common.im.codec.mina;

import com.openim.common.im.bean.ExchangeMessage;
import com.openim.common.im.bean.MessageType;
import com.openim.common.im.bean.protbuf.ProtobufChatMessage;
import org.apache.mina.core.filterchain.DefaultIoFilterChainBuilder;
import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.transport.socket.nio.NioSocketConnector;

import java.net.InetSocketAddress;

/**
 * Created by shihc on 2015/9/1.
 */
public class MinaClientTest {
    public static void main(String[] args) {
        //create tcp/ip connector
        NioSocketConnector connector = new NioSocketConnector();
        //创建接收数据的过滤器
        DefaultIoFilterChainBuilder chain = connector.getFilterChain();
        //设定这个过滤器将一行一行读数据
        chain.addLast("myChin", new ProtocolCodecFilter(new OpenIMProtobufEncoderV2(), new OpenIMProtobufDecoderV2()));
        connector.setHandler(new MinaClientHandler());
        connector.setConnectTimeout(30);
        //连接到服务器
        ConnectFuture cf = connector.connect(new InetSocketAddress("localhost",10000));
        //wait for the connection attempt to be finished
        cf.awaitUninterruptibly();
        cf.getSession().getCloseFuture().awaitUninterruptibly();
        connector.dispose();
    }

    public static class MinaClientHandler extends IoHandlerAdapter  {

        @Override
        public void messageReceived(IoSession session, Object message) throws Exception {
            // TODO Auto-generated method stub
            String s = (String)message;
            System.out.println("来自服务器的消息:"+s);
            session.write(s);
        }

        @Override
        public void sessionClosed(IoSession session) throws Exception {
            // TODO Auto-generated method stub
            System.out.println("服务器走了~");
        }

        @Override
        public void sessionCreated(IoSession session) throws Exception {
            // TODO Auto-generated method stub
            System.out.println("one Client Connection"+session.getRemoteAddress());
            ExchangeMessage exchangeMessage = new ExchangeMessage();
            int type = MessageType.CHAT;
            ProtobufChatMessage.ChatMessage chatMsg = ProtobufChatMessage.ChatMessage.newBuilder().setMsg("消息").build();
            exchangeMessage.setMessageLite(chatMsg);
            exchangeMessage.setType(type);
            session.write(exchangeMessage);

            //粘包模拟
            /*for(int i=0; i<100; i++){
                session.write(exchangeMessage);
            }*/



        }

    }
}
