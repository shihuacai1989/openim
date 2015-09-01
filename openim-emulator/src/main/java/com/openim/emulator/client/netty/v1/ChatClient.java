package com.openim.emulator.client.netty.v1;

import com.alibaba.fastjson.JSON;
import com.openim.common.im.bean.DeviceMsg;
import com.openim.common.im.bean.ProtobufDeviceMsg;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * Created by shihuacai on 2015/7/22.
 */
public class ChatClient {
    private final String host;
    private final int port;



    public ChatClient(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public static void main(String[] args) throws Exception {
        new ChatClient("localhost", 9000).run();
    }

    public void run() throws Exception {
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap bootstrap = new Bootstrap()
                    .group(group)
                    .channel(NioSocketChannel.class)
                    .handler(new ProtobufChatClientInitializer());
            Channel channel = bootstrap.connect(host, port).sync().channel();
            BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
            System.out.println("为方便测试，默认两个用户:user1,user2，退出系统请输入quit \r\n");
            System.out.println("请输入登录名(user1或user2):\r\n");
            while (true) {
                String line = in.readLine();
                DeviceMsg deviceMsg = JSON.parseObject(line, DeviceMsg.class);

                ProtobufDeviceMsg.DeviceMsg.Builder builder = ProtobufDeviceMsg.DeviceMsg.newBuilder();
                builder.setType(deviceMsg.getType());
                if (deviceMsg.getMsg() != null) {
                    builder.setMsg(deviceMsg.getMsg());
                }
                if (deviceMsg.getTo() != null) {
                    builder.setTo(deviceMsg.getTo());
                }
                if (deviceMsg.getLoginId() != null) {
                    builder.setLoginId(deviceMsg.getLoginId());

                }

                /*ProtobufDeviceMsg.DeviceMsg protobufMsg = builder.build();
                channel.writeAndFlush(protobufMsg);*/

                //模拟粘包现象
                for(int i=0; i<10000; i++){

                    ProtobufDeviceMsg.DeviceMsg protobufMsg = builder.build();
                    //channel.writeAndFlush(protobufMsg);
                    channel.write(protobufMsg);
                }
                //不调用该代码，服务端收不到数据，或者调用writeAndFlush
                channel.flush();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            group.shutdownGracefully();
        }

    }


}
