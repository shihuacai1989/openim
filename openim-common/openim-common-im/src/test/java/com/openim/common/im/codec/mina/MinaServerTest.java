package com.openim.common.im.codec.mina;

import org.apache.mina.core.service.IoAcceptor;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;

import java.net.InetSocketAddress;

/**
 * Created by shihc on 2015/9/1.
 */
public class MinaServerTest {
    public static void main(String[] args){
        try {
            IoAcceptor acceptor = new NioSocketAcceptor();

            // 指定protobuf的编码器和解码器
            acceptor.getFilterChain().addLast("codec",
                    new ProtocolCodecFilter(new OpenIMProtobufEncoderV2(), new OpenIMProtobufDecoderV2()));

            acceptor.setHandler(new TcpServerHandle());
            acceptor.bind(new InetSocketAddress(10000));
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public static class TcpServerHandle extends IoHandlerAdapter {

        @Override
        public void exceptionCaught(IoSession session, Throwable cause)
                throws Exception {
            cause.printStackTrace();
        }

        @Override
        public void messageReceived(IoSession session, Object message)
                throws Exception {


            System.out.println(message);
            /*// 读取客户端传过来的Student对象
            StudentMsg.Student student = (StudentMsg.Student) message;
            System.out.println("ID:" + student.getId());
            System.out.println("Name:" + student.getName());
            System.out.println("Email:" + student.getEmail());
            System.out.println("Friends:");
            List<String> friends = student.getFriendsList();
            for(String friend : friends) {
                System.out.println(friend);
            }

            // 新建一个Student对象传到客户端
            StudentMsg.Student.Builder builder = StudentMsg.Student.newBuilder();
            builder.setId(9);
            builder.setName("服务器");
            builder.setEmail("123@abc.com");
            builder.addFriends("X");
            builder.addFriends("Y");
            StudentMsg.Student student2 = builder.build();
            session.write(student2);*/
        }
    }
}
