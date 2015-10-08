package com.openim.zbus.mq;

import org.zbus.broker.Broker;
import org.zbus.broker.BrokerConfig;
import org.zbus.broker.SingleBroker;
import org.zbus.mq.Producer;
import org.zbus.net.http.Message;

/**
 * Created by shihuacai on 2015/10/8.
 */
public class ZBusProvider {
    public static void main(String[] args) throws Exception {
        //创建Broker代理
        BrokerConfig config = new BrokerConfig();
        config.setServerAddress("127.0.0.1:15555");
        final Broker broker = new SingleBroker(config);

        Producer producer = new Producer(broker, "MyMQ");
        producer.createMQ(); // 如果已经确定存在，不需要创建

        //创建消息，消息体可以是任意binary，应用协议交给使用者
        Message msg = new Message();
        msg.setBody("hello world2");
        producer.sendSync(msg);

        broker.close();
    }
}
