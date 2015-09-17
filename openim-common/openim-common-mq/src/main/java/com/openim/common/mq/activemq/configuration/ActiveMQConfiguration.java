package com.openim.common.mq.activemq.configuration;

import com.openim.common.mq.IMessageSender;
import com.openim.common.mq.activemq.ActiveMQMessageListener;
import com.openim.common.mq.activemq.ActiveMQMessageSender;
import com.openim.common.mq.constants.MQConstants;
import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.jms.Queue;

/**
 * Created by shihc on 2015/9/17.
 */
@Configuration
public class ActiveMQConfiguration {

    public static final String HELLO_QUEUE = "hello.queue";


    @Bean
    public Queue helloJMSQueue() {
        return new ActiveMQQueue(HELLO_QUEUE);
    }

    @Bean
    Queue chatQueue() {
        return new ActiveMQQueue(MQConstants.chatQueue);
    }

    @Bean
    Queue loginQueue() {
        return new ActiveMQQueue(MQConstants.loginQueue);
    }

    @Bean
    Queue logoutQueue() {
        return new ActiveMQQueue(MQConstants.logoutQueue);
    }

    @Bean
    IMessageSender activeMQMessageSender() {
        return new ActiveMQMessageSender();
    }

    @Bean
    ActiveMQMessageListener activeMQMessageListener() {
        return new ActiveMQMessageListener();
    }




    /*<bean id="jmsConnectionFactory" class="org.apache.activemq.ActiveMQConnectionFactory">
    <property name="brokerURL">
    <!-- value>tcp://localhost:61616</value -->
    <value>vm://localhost</value>
    </property>
    </bean>

    <bean id="pooledJmsConnectionFactory" class="org.apache.activemq.pool.PooledConnectionFactory"
    destroy-method="stop">
    <property name="connectionFactory" ref="jmsConnectionFactory" />
    </bean>

    <bean id="destination" class="org.apache.activemq.command.ActiveMQQueue">
    <constructor-arg value="jmsExample" />
    </bean>

    <bean id="jmsTemplate" class="org.springframework.jms.core.JmsTemplate">
    <property name="connectionFactory" ref="pooledJmsConnectionFactory" />

    </bean>*/





}
