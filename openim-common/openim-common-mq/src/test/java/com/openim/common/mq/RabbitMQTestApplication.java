package com.openim.common.mq;

import com.openim.common.mq.constants.MQConstants;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Created by shihuacai on 2015/7/28.
 */
@SpringBootApplication
public class RabbitMQTestApplication implements CommandLineRunner {

    @Autowired
    RabbitTemplate rabbitTemplate;

    public static void main(String[] args) throws InterruptedException {
        SpringApplication.run(RabbitMQTestApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("Waiting five seconds...");
        Thread.sleep(5000);
        System.out.println("Sending message...");
        rabbitTemplate.convertAndSend(MQConstants.openimExchange, MQConstants.chatRouteKey, "Hello from RabbitMQ!");
        rabbitTemplate.convertAndSend(MQConstants.openimExchange, MQConstants.logoutRouteKey, "Hello from RabbitMQ2!");

        //receiver().getLatch().await(10000, TimeUnit.MILLISECONDS);
        //context.close();
    }
}
