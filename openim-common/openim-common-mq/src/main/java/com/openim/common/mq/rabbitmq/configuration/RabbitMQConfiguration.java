package com.openim.common.mq.rabbitmq.configuration;

import com.openim.common.mq.constants.MQConstants;
import com.openim.common.mq.rabbitmq.sender.RabbitMQMessageListener;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import javax.annotation.Resource;

/**
 * Created by shihuacai on 2015/7/28.
 */
@Configuration
public class RabbitMQConfiguration {
    /*private final static String chatExchange = "openim-exchange";
    private final static String chatQueue = "openim-chat-queue";
    private final static String routeKey = "openim-chat-routeKey";*/

    //@Bean
    Queue chatQueue() {
        return new Queue(MQConstants.chatQueue, false);
    }
    //@Bean
    Queue logoutQueue() {
        return new Queue(MQConstants.logoutQueue, false);
    }


    @Bean
    TopicExchange exchange() {
        return new TopicExchange(MQConstants.chatExchange);
    }

    @Bean
    Binding bindingChatQueue(TopicExchange exchange) {
        Queue queue = new Queue(MQConstants.chatQueue, false);
        return BindingBuilder.bind(queue).to(exchange).with(MQConstants.chatRouteKey);
    }

    @Bean
    Binding bindingLogoutQueue(TopicExchange exchange) {
        Queue queue = new Queue(MQConstants.logoutQueue, false);
        return BindingBuilder.bind(queue).to(exchange).with(MQConstants.logoutRouteKey);
    }

    @Bean
    SimpleMessageListenerContainer container(ConnectionFactory connectionFactory, MessageListenerAdapter listenerAdapter) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setQueueNames(MQConstants.chatQueue);
        container.setMessageListener(listenerAdapter);
        return container;
    }

    @Bean
    MessageListener rabbitMQMessageListener(){
        return new RabbitMQMessageListener();
    }

    @Bean
    MessageListenerAdapter listenerAdapter(MessageListener messageListener) {
        return new MessageListenerAdapter(messageListener);
    }
}
