package com.openim.common.mq.activemq.configuration;

import com.openim.common.mq.IMessageSender;
import com.openim.common.mq.activemq.listener.ActiveMQMessageListener;
import com.openim.common.mq.activemq.sender.ActiveMQMessageSender;
import com.openim.common.mq.constants.MQConstants;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQQueue;
import org.apache.activemq.pool.PooledConnectionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.core.JmsTemplate;

/**
 * Created by shihc on 2015/9/17.
 */
@Configuration
public class BaseActiveMQConfiguration {

    @Value("${activemq.broker-url}")
    String brokerUrl;

    @Value("${activemq.user}")
    String userName;

    @Value("${activemq.password}")
    String password;

    @Bean
    ActiveMQQueue managerConsumerQueue() {
        return new ActiveMQQueue(MQConstants.MANAGER_CONSUMER_TOPIC);
    }

    @Bean
    ActiveMQQueue chatServerConsumerQueue() {
        return new ActiveMQQueue(MQConstants.CHATSERVER_CONSUMER_TOPIC);
    }

    @Bean
    IMessageSender activeMQMessageSender() {
        return new ActiveMQMessageSender();
    }

    @Bean
    ActiveMQMessageListener activeMQMessageListener() {
        return new ActiveMQMessageListener();
    }

    /*@Bean
    ActiveMQConnectionFactory activeMQConnectionFactory(){

        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory();
        connectionFactory.setBrokerURL(brokerUrl);
        connectionFactory.setUserName(userName);
        connectionFactory.setPassword(password);

        return connectionFactory;
    }

    @Bean
    SingleConnectionFactory connectionFactory(ActiveMQConnectionFactory activeMQConnectionFactory){
        SingleConnectionFactory connectionFactory = new SingleConnectionFactory();
        //Spring提供的ConnectionFactory只是Spring用于管理ConnectionFactory的，
        // 真正产生到JMS服务器链接的ConnectionFactory还得是由JMS服务厂商提供，
        // 并且需要把它注入到Spring提供的ConnectionFactory中
        connectionFactory.setTargetConnectionFactory(activeMQConnectionFactory);
        return connectionFactory;
    }

    @Bean
    JmsTemplate jmsTemplate(SingleConnectionFactory connectionFactory){
        JmsTemplate jmsTemplate = new JmsTemplate();;
        jmsTemplate.setConnectionFactory(connectionFactory);
        return jmsTemplate;
    }*/

    //=====================
    // 上面的配置中JmsTemplate每次发送数据时，都要创建连接、关闭，效率太
    // 调整为以下配置，或者采用org.springframework.jms.connection.CachingConnectionFactory
    //=====================

    @Bean
    PooledConnectionFactory pooledConnectionFactory(){

        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory();
        connectionFactory.setBrokerURL(brokerUrl);
        connectionFactory.setUserName(userName);
        connectionFactory.setPassword(password);

        PooledConnectionFactory pooledConnectionFactory = new PooledConnectionFactory();
        pooledConnectionFactory.setConnectionFactory(connectionFactory);
        return pooledConnectionFactory;
    }

    @Bean
    JmsTemplate jmsTemplate(PooledConnectionFactory connectionFactory){
        JmsTemplate jmsTemplate = new JmsTemplate();
        jmsTemplate.setConnectionFactory(connectionFactory);
        return jmsTemplate;
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
