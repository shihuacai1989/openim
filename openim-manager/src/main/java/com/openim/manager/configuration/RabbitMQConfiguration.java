package com.openim.manager.configuration;

import com.openim.common.mq.constants.MQConstants;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.context.annotation.Bean;

/**
 * Created by shihc on 2015/7/30.
 */
//@Configuration
public class RabbitMQConfiguration {

    /**
     * rabbitmq监听器
     * @param connectionFactory
     * @param listenerAdapter
     * @return
     */
    @Bean
    SimpleMessageListenerContainer rabbitMQListenerContainer(ConnectionFactory connectionFactory, MessageListenerAdapter listenerAdapter) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);

        container.setQueueNames(MQConstants.chatQueue, MQConstants.loginQueue, MQConstants.logoutQueue);
        container.setMessageListener(listenerAdapter);
        return container;
    }
}
