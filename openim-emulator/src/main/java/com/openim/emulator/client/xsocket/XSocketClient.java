package com.openim.emulator.client.xsocket;

import org.xsocket.MaxReadSizeExceededException;
import org.xsocket.connection.*;

import java.io.IOException;
import java.nio.BufferUnderflowException;
import java.nio.channels.ClosedChannelException;

/**
 * Created by shihuacai on 2015/9/13.
 */
public class XSocketClient {
    private static final int PORT = 9000;
    public static void main(String[] args) throws IOException {
        //采用非阻塞式的连接
        INonBlockingConnection nbc = new NonBlockingConnection("localhost", PORT, new ClientHandler());

        //采用阻塞式的连接
        //IBlockingConnection bc = new BlockingConnection("localhost", PORT);
        //一个非阻塞的连接是很容易就变成一个阻塞连接
        IBlockingConnection bc = new BlockingConnection(nbc);
        //设置编码格式
        bc.setEncoding("UTF-8");
        //设置是否自动清空缓存
        bc.setAutoflush(true);
        //向服务端写数据信息
        for (int i = 0; i < 100; i++) {
            bc.write(" client | i |love |china !..." +i);
        }
        //向客户端读取数据的信息
        byte[] byteBuffers= bc.readBytesByDelimiter("|", "UTF-8");
        //打印服务器端信息
        System.out.println(new String(byteBuffers));
        //将信息清除缓存，写入服务器端
        bc.flush();
        bc.close();
    }

    public static class ClientHandler implements IDataHandler,IConnectHandler ,IDisconnectHandler {

        /**
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
         * 连接断开时的操作
         */
        @Override
        public boolean onDisconnect(INonBlockingConnection nbc) throws IOException {
            // TODO Auto-generated method stub
            return false;
        }
        /**
         *
         * 接收到数据库时候的处理
         */
        @Override
        public boolean onData(INonBlockingConnection nbc) throws IOException,
                BufferUnderflowException, ClosedChannelException,
                MaxReadSizeExceededException {
            String data=nbc.readStringByDelimiter("|");
            nbc.write("--|Client:receive data from server sucessful| -----");
            nbc.flush();
            System.out.println(data);
            return true;
        }

    }
}
