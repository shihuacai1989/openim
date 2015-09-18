package com.openim.chatserver.configuration;

import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.jms.listener.DefaultMessageListenerContainer;

/**
 * Created by shihuacai on 2015/7/29.
 */
public class ActiveMQConfiguration extends BaseConfiguration {

    /*@Bean
    ActiveMQQueue chatQueue() {
        return new ActiveMQQueue(chatServerListenerQueue());
    }*/

    @Autowired
    @Qualifier("chatServerConsumerQueue")
    ActiveMQQueue chatServerConsumerQueue;


    /**
     * activemq监听器，无法同时监听多个队列，只能通过topic的形式达到监听多个多列的目录
     * @param connectionFactory
     * @param messageListener
     * @return
     */
    @Bean
    DefaultMessageListenerContainer activeMQListenerContainer(javax.jms.ConnectionFactory connectionFactory, javax.jms.MessageListener messageListener) {
        DefaultMessageListenerContainer container = new DefaultMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setDestination(chatServerConsumerQueue);
        /*ActiveMQTopic topic = new ActiveMQTopic(MQConstants.CHATSERVER_CONSUMER_TOPIC);
        container.setDestination(topic);*/
        container.setMessageListener(messageListener);
        return container;
    }
}
