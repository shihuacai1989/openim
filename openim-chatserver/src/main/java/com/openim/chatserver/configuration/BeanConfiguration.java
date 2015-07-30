package com.openim.chatserver.configuration;

import com.openim.common.mq.IMessageSender;
import com.openim.common.mq.constants.MQConstants;
import com.openim.common.mq.rabbitmq.sender.RabbitMQMessageSender;
import com.openim.common.util.IPUtil;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;
import sun.net.util.IPAddressUtil;

/**
 * Created by shihuacai on 2015/7/29.
 */
@Configurable
public class BeanConfiguration {
    /*@Bean
    IMessageSender rabbitMQMessageSender(){
        return new RabbitMQMessageSender();
    }*/

    @Bean
    SimpleMessageListenerContainer rabbitMQListenerContainer(ConnectionFactory connectionFactory, MessageListenerAdapter listenerAdapter) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setQueueNames("chatSever-" + IPUtil.getLocalIP());
        container.setMessageListener(listenerAdapter);
        return container;
    }
}
