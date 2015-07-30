package com.openim.chatserver.configuration;

import com.openim.common.util.IPUtil;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;

/**
 * Created by shihuacai on 2015/7/29.
 */
@Configurable
public class BeanConfiguration {

    private static final String queue = "chatSever-{server}:{port}";

    @Value("chat.port")
    private String port;

    @Bean
    Queue serverQueue() {
        String queueName = chatServerListenerQueue();
        //String queueName = String.format(queue, IPUtil.getLocalIP(), port);
        return new Queue(queueName, true);
    }

    @Bean
    Binding bindingChatQueue(Queue queue, TopicExchange exchange) {
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

    private String chatServerListenerQueue(){
        return queue.replace("{server}", IPUtil.getLocalIP()).replace("{port}", port);
    }
}
