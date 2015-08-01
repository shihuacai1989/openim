package com.openim.common.mq.rabbitmq.listener;

import com.openim.common.mq.IMessageDispatch;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by shihuacai on 2015/7/28.
 */
public class RabbitMQMessageListener implements MessageListener {

    @Autowired
    IMessageDispatch messageDispatch;

    @Override
    public void onMessage(Message amqpMessage) {
        try {
            //amqpMessage.
            byte[] bytes = (byte[])amqpMessage.getBody();
            //String message = new String(bytes, "UTF-8");

            //也是返回byte[]
            //messageConverter.fromMessage(amqpMessage);

            String exchange = amqpMessage.getMessageProperties().getReceivedExchange();
            String routeKey = amqpMessage.getMessageProperties().getReceivedRoutingKey();

            messageDispatch.dispatchMessage(exchange, routeKey, bytes);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
