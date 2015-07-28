package com.openim.common.mq.rabbitmq.sender;

import com.openim.common.mq.IMessageSender;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by shihuacai on 2015/7/28.
 */
public class RabbitMQMessageSender implements IMessageSender {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Override
    public void sendMessage(String exchange, String routeKey, Object message) {
        rabbitTemplate.convertAndSend(exchange, routeKey, message);
    }
}
