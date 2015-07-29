package com.openim.server.configuration;

import com.openim.common.mq.rabbitmq.sender.RabbitMQMessageSender;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;

/**
 * Created by shihuacai on 2015/7/29.
 */
@Configurable
public class BeanConfiguration {
    @Bean
    RabbitMQMessageSender rabbitMQConfiguration(){
        return new RabbitMQMessageSender();
    }
}
