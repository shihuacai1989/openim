package com.openim.manager.configuration;

import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.connection.SingleConnectionFactory;
import org.springframework.jms.listener.DefaultMessageListenerContainer;

/**
 * Created by shihc on 2015/7/30.
 */
@Configuration
public class ActiveMQConfiguration {

    @Autowired
    @Qualifier("managerConsumerQueue")
    ActiveMQQueue managerConsumerQueue;
    /**
     * activemq监听器，无法同时监听多个队列，只能通过topic的形式达到监听多个队列的目的
     * @param connectionFactory
     * @param messageListener
     * @return
     */
    @Bean
    DefaultMessageListenerContainer activeMQListenerContainer(SingleConnectionFactory connectionFactory, javax.jms.MessageListener messageListener) {
        DefaultMessageListenerContainer container = new DefaultMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        //ActiveMQQueue queue = new ActiveMQQueue(MQConstants.MANAGER_CONSUMER_TOPIC);
        container.setDestination(managerConsumerQueue);
        container.setMessageListener(messageListener);
        return container;
    }
}
