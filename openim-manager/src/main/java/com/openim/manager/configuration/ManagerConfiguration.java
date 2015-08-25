package com.openim.manager.configuration;

import com.openim.common.mq.IMessageDispatch;
import com.openim.common.mq.constants.MQConstants;
import com.openim.manager.cache.login.ILoginCache;
import com.openim.manager.cache.login.LoginMemoryCache;
import com.openim.manager.dispatch.MessageDispatchV2;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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
    IMessageDispatch messageDispatch() {
        return new MessageDispatchV2();
    }

    @Bean
    SimpleMessageListenerContainer rabbitMQListenerContainer(ConnectionFactory connectionFactory, MessageListenerAdapter listenerAdapter) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);

        container.setQueueNames(MQConstants.chatQueue, MQConstants.loginQueue, MQConstants.logoutQueue);
        container.setMessageListener(listenerAdapter);
        return container;
    }
}
