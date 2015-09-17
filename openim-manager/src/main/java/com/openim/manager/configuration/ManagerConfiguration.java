package com.openim.manager.configuration;

import com.openim.common.mq.MessageQueueDispatch;
import com.openim.common.mq.constants.MQConstants;
import com.openim.manager.cache.login.ILoginCache;
import com.openim.manager.cache.login.LoginMemoryCache;
import com.openim.manager.dispatch.ManagerMessageQueueDispatchV2;
import org.apache.activemq.command.ActiveMQTopic;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.listener.DefaultMessageListenerContainer;

/**
 * Created by shihc on 2015/7/30.
 */
@Configuration
public class ManagerConfiguration {

    @Bean
    ILoginCache loginMemoryCache() {
        return new LoginMemoryCache();
    }

    @Bean
    MessageQueueDispatch messageDispatch() {
        return new ManagerMessageQueueDispatchV2();
    }

    @Bean
    SimpleMessageListenerContainer rabbitMQListenerContainer(ConnectionFactory connectionFactory, MessageListenerAdapter listenerAdapter) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);

        container.setQueueNames(MQConstants.chatQueue, MQConstants.loginQueue, MQConstants.logoutQueue);
        container.setMessageListener(listenerAdapter);
        return container;
    }

    @Bean
    DefaultMessageListenerContainer activeMQListenerContainer(javax.jms.ConnectionFactory connectionFactory, javax.jms.MessageListener messageListener) {
        DefaultMessageListenerContainer container = new DefaultMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        ActiveMQTopic topic = new ActiveMQTopic(MQConstants.MANAGER_CONSUMER_TOPIC);
        container.setDestination(topic);
        //container.setQueueNames(MQConstants.chatQueue, MQConstants.loginQueue, MQConstants.logoutQueue);
        container.setMessageListener(messageListener);
        return container;
    }
}
