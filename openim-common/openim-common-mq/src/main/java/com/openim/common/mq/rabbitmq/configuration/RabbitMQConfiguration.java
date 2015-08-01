package com.openim.common.mq.rabbitmq.configuration;

import com.openim.common.mq.IMessageSender;
import com.openim.common.mq.constants.MQConstants;
import com.openim.common.mq.rabbitmq.listener.RabbitMQMessageListener;
import com.openim.common.mq.rabbitmq.sender.RabbitMQMessageSender;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.amqp.support.converter.SerializerMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by shihuacai on 2015/7/28.
 */
@Configuration
public class RabbitMQConfiguration {

    /**
     * 自动在rabbitmq server中创建队列
     * @return
     */
    @Bean
    Queue chatQueue() {
        return new Queue(MQConstants.chatQueue, false);
    }

    @Bean
    Queue loginQueue() {
        return new Queue(MQConstants.loginQueue, false);
    }

    @Bean
    Queue logoutQueue() {
        return new Queue(MQConstants.logoutQueue, false);
    }

    @Bean
    TopicExchange exchange() {
        return new TopicExchange(MQConstants.openimExchange);
    }

    @Bean
    Binding bindingChatQueue(TopicExchange exchange) {
        Queue queue = chatQueue();
        return BindingBuilder.bind(queue).to(exchange).with(MQConstants.chatRouteKey);
    }

    @Bean
    Binding bindingLogoutQueue(TopicExchange exchange) {
        Queue queue = logoutQueue();
        return BindingBuilder.bind(queue).to(exchange).with(MQConstants.logoutRouteKey);
    }

    @Bean
    Binding bindingLoginQueue(TopicExchange exchange) {
        Queue queue = loginQueue();
        return BindingBuilder.bind(queue).to(exchange).with(MQConstants.loginRouteKey);
    }


    @Bean
    IMessageSender rabbitMQMessageSender(){
        return new RabbitMQMessageSender();
    }

    @Bean
    RabbitMQMessageListener rabbitMQMessageListener(){
        return new RabbitMQMessageListener();
    }

    @Bean
    MessageListenerAdapter listenerAdapter(RabbitMQMessageListener messageListener) {
        return new MessageListenerAdapter(messageListener);
    }

    @Bean
    MessageConverter messageConverter(RabbitMQMessageListener messageListener) {
        return new SerializerMessageConverter();
    }
}
