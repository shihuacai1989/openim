package com.openim.common.mq.activemq;

import com.openim.common.mq.MessageQueueDispatch;

import javax.jms.Message;
import javax.jms.MessageListener;

/**
 * Created by shihuacai on 2015/7/28.
 */
public class ActiveMQMessageListener implements MessageListener {

    //@Autowired
    MessageQueueDispatch messageDispatch;

    //@JmsListener(destination = ActiveMQConfiguration.HELLO_QUEUE)
    public void onMessage(Message amqpMessage) {

        System.out.println("收到消息: " + amqpMessage);
        /*try {
            //amqpMessage.
            byte[] bytes = (byte[]) amqpMessage.getBody();
            //String message = new String(bytes, "UTF-8");

            //也是返回byte[]
            //messageConverter.fromMessage(amqpMessage);

            String exchange = amqpMessage.getMessageProperties().getReceivedExchange();
            String routeKey = amqpMessage.getMessageProperties().getReceivedRoutingKey();

            messageDispatch.dispatchMessage(exchange, routeKey, bytes);
        } catch (Exception e) {
            e.printStackTrace();
        }*/
    }
}
