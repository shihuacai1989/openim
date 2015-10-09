package com.openim.chatserver.net.xsocket.v2;

import com.openim.chatserver.net.IChatServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.xsocket.MaxReadSizeExceededException;
import org.xsocket.connection.*;

import java.io.IOException;
import java.net.InetAddress;
import java.nio.BufferUnderflowException;
import java.nio.channels.ClosedChannelException;
import java.util.Map;

/**
 * Created by shihuacai on 2015/9/13.
 */
@Deprecated
public class XSocketChatServerV2 implements IChatServer {

    private static final Logger LOG = LoggerFactory.getLogger(XSocketChatServerV2.class);

    @Value("${chat.port}")
    private int port;

    @Override
    public void startServer() {
        try {

            //注意其构造方法有多个。一般是使用这种构造方法出来的！
            //不过要注意一下java.net.InetAddress这个类的使用在初始化的时候需要捕获异常
            //可能是这个绑定的主机可能不存在之类的异常即UnknowHostNameException
            InetAddress address = InetAddress.getByName("localhost");
            //创建一个服务端的对象
            IServer srv = new Server(address, port, new ServerHandler());
            //设置当前的采用的异步模式
            srv.setFlushmode(IConnection.FlushMode.ASYNC);

            // srv.run();
            // the call will not return
            // ... or start it by using a dedicated thread
            srv.start(); // returns after the server has been started
            System.out.println("服务器" + srv.getLocalAddress() + ":" + port);
            Map<String, Class> maps = srv.getOptions();
            if (maps != null) {

                for (Map.Entry<String, Class> entry : maps.entrySet()) {
                    LOG.info("key= " + entry.getKey() + " value =" + entry.getValue().getName());
                }
            }
            LOG.info("日志: " + srv.getStartUpLogMessage());

        } catch (Exception e) {
            LOG.error(e.toString());
        }
    }

    @Override
    public void stopServer() {

    }

    @Override
    public void afterPropertiesSet() throws Exception {
        startServer();
    }


    public class ServerHandler implements IDataHandler,IConnectHandler ,IIdleTimeoutHandler,IConnectionTimeoutHandler,IDisconnectHandler {

        /**
         * 即当建立完连接之后可以进行的一些相关操作处理。包括修改连接属性、准备资源、等！
         * 连接的成功时的操作
         */
        @Override
        public boolean onConnect(INonBlockingConnection nbc) throws IOException,
                BufferUnderflowException, MaxReadSizeExceededException {
            String  remoteName=nbc.getRemoteAddress().getHostName();
            System.out.println("remoteName "+remoteName +" has connected ！");
            return true;
        }
        /**
         * 即如果失去连接应当如何处理？
         *需要实现 IDisconnectHandler  这个接口
         * 连接断开时的操作
         */
        @Override
        public boolean onDisconnect(INonBlockingConnection nbc) throws IOException {
            return false;
        }
        /**
         * 即这个方法不光是说当接收到一个新的网络包的时候会调用而且如果有新的缓存存在的时候也会被调用。而且
         *The onData will also be called, if the connection is closed当连接被关闭的时候也会被调用的!
         */
        @Override
        public boolean onData(INonBlockingConnection nbc) throws IOException,
                BufferUnderflowException, ClosedChannelException,
                MaxReadSizeExceededException {

            //nbc.read
            String data=nbc.readStringByDelimiter("|");
            nbc.write("--|server:receive data from client sucessful| -----");
            nbc.flush();
            System.out.println(data);
            return true;
        }
        /**
         * 请求处理超时的处理事件
         */
        @Override
        public boolean onIdleTimeout(INonBlockingConnection connection) throws IOException {
            // TODO Auto-generated method stub
            return false;
        }
        /**
         * 连接超时处理事件
         */
        @Override
        public boolean onConnectionTimeout(INonBlockingConnection connection) throws IOException {
            // TODO Auto-generated method stub
            return false;
        }

    }
}
