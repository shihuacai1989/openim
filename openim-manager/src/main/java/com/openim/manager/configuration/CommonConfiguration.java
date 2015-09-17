package com.openim.manager.configuration;

import com.openim.common.mq.MessageQueueDispatch;
import com.openim.manager.cache.login.ILoginCache;
import com.openim.manager.cache.login.LoginMemoryCache;
import com.openim.manager.dispatch.ManagerMessageQueueDispatchV2;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by shihc on 2015/7/30.
 */
@Configuration
public class CommonConfiguration {

    @Bean
    ILoginCache loginMemoryCache() {
        return new LoginMemoryCache();
    }

    @Bean
    MessageQueueDispatch messageDispatch() {
        return new ManagerMessageQueueDispatchV2();
    }

    /**
     * rabbitmq监听器
     * @param connectionFactory
     * @param listenerAdapter
     * @return
     */
    /*@Bean
    SimpleMessageListenerContainer rabbitMQListenerContainer(ConnectionFactory connectionFactory, MessageListenerAdapter listenerAdapter) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);

        container.setQueueNames(MQConstants.chatQueue, MQConstants.loginQueue, MQConstants.logoutQueue);
        container.setMessageListener(listenerAdapter);
        return container;
    }*/

    /**
     * activemq监听器，无法同时监听多个队列，只能通过topic的形式达到监听多个多列的目录
     * @param connectionFactory
     * @param messageListener
     * @return
     */
    /*@Bean
    DefaultMessageListenerContainer activeMQListenerContainer(javax.jms.ConnectionFactory connectionFactory, javax.jms.MessageListener messageListener) {
        DefaultMessageListenerContainer container = new DefaultMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        ActiveMQTopic topic = new ActiveMQTopic(MQConstants.MANAGER_CONSUMER_TOPIC);
        container.setDestination(topic);
        container.setMessageListener(messageListener);
        return container;
    }*/
}
