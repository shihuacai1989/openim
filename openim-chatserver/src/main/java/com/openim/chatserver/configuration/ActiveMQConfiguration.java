package com.openim.chatserver.configuration;

import com.openim.common.mq.constants.MQConstants;
import org.apache.activemq.command.ActiveMQQueue;
import org.apache.activemq.command.ActiveMQTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.jms.listener.DefaultMessageListenerContainer;

/**
 * Created by shihuacai on 2015/7/29.
 */
public class ActiveMQConfiguration extends BaseConfiguration {

    @Bean
    javax.jms.Queue chatQueue() {
        return new ActiveMQQueue(chatServerListenerQueue());
    }

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
        ActiveMQTopic topic = new ActiveMQTopic(MQConstants.CHATSERVER_CONSUMER_TOPIC);
        container.setDestination(topic);
        container.setMessageListener(messageListener);
        return container;
    }
}
