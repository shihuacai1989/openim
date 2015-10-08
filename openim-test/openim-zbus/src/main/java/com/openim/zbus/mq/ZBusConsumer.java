package com.openim.zbus.mq;

import org.zbus.broker.Broker;
import org.zbus.broker.BrokerConfig;
import org.zbus.broker.SingleBroker;
import org.zbus.mq.Consumer;
import org.zbus.mq.MqConfig;
import org.zbus.net.core.Session;
import org.zbus.net.http.Message;

import java.io.IOException;

/**
 * Created by shihuacai on 2015/10/8.
 */
public class ZBusConsumer {
    public static void main(String[] args) throws Exception {
        //创建Broker代表
        BrokerConfig brokerConfig = new BrokerConfig();
        brokerConfig.setServerAddress("127.0.0.1:15555");
        Broker broker = new SingleBroker(brokerConfig);

        MqConfig config = new MqConfig();
        config.setBroker(broker);
        config.setMq("MyMQ");

        //创建消费者
        @SuppressWarnings("resource")
        Consumer c = new Consumer(config);

        c.onMessage(new Message.MessageHandler() {
            @Override
            public void handle(Message msg, Session sess) throws IOException {
                System.out.println(msg);
            }
        });

        //启动消费线程
        c.start();

    }
}
