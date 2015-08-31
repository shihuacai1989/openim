package com.openim.chatserver.configuration;

import com.openim.chatserver.dispatch.ChatServerMessageDispatchV2;
import com.openim.chatserver.net.netty.INettyMessageDispatch;
import com.openim.chatserver.net.netty.v2.NettyMessageDispatchV2;
import com.openim.common.mq.IMessageQueueDispatch;
import com.openim.common.util.IPUtil;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by shihuacai on 2015/7/29.
 */
@Configuration
public class BeanConfiguration {

    private static final String queueTemplate = "chatSever-{server}:{port}";
    public static String chatQueueName;

    @Value("${chat.port}")
    private String port;

    @Bean
    IMessageQueueDispatch messageDispatch() {
        return new ChatServerMessageDispatchV2();
    }


    @Bean
    INettyMessageDispatch nettyMessageDispatch(){
        return new NettyMessageDispatchV2();
    }



    @Bean
    Queue serverQueue() {
        String queueName = chatServerListenerQueue();
        //String queueName = String.format(queue, IPUtil.getLocalIP(), port);
        return new Queue(queueName, true);
    }

    /**
     * 采用该注解注入对象，方法名不能重名，否则存在一个不执行的情况
     *
     * @param exchange
     * @return
     */
    @Bean
    Binding bindingChatServerQueue(TopicExchange exchange) {
        Queue queue = serverQueue();
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

    private String chatServerListenerQueue() {
        chatQueueName = queueTemplate.replace("{server}", IPUtil.getLocalIP()).replace("{port}", port);
        return chatQueueName;
    }
}
