package com.openim.chatserver.configuration;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.context.annotation.Bean;

/**
 * Created by shihuacai on 2015/7/29.
 */
public class RabbitMQConfiguration extends BaseConfiguration {

    @Bean
    Queue serverQueue() {
        String queueName = chatServerListenerQueue();
        return new Queue(queueName, true);
    }

    /**
     * 采用该注解注入对象，方法名不能重名，否则存在一个不执行的情况
     *
     * @param exchange
     * @return
     */
    @Bean
    Binding bindingChatServerQueue(DirectExchange exchange) {
        Queue queue = serverQueue();
        return BindingBuilder.bind(queue).to(exchange).with(chatServerListenerQueue());
    }

    @Bean
    SimpleMessageListenerContainer rabbitMQListenerContainer(ConnectionFactory connectionFactory, MessageListenerAdapter listenerAdapter) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        String queueName = chatServerListenerQueue();
        container.setQueueNames(queueName);
        container.setMessageListener(listenerAdapter);
        return container;
    }
}
