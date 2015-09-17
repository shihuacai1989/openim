package com.openim.common.mq;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Created by shihuacai on 2015/7/28.
 */
@SpringBootApplication
public class ActiveMQTestApplication implements CommandLineRunner {

    @Autowired
    IMessageSender iMessageSender;

    public static void main(String[] args) throws InterruptedException {
        SpringApplication.run(ActiveMQTestApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("Waiting five seconds...");
        Thread.sleep(5000);
        System.out.println("Sending message...");
        iMessageSender.sendMessage(null, "hello.queue", "Hello from RabbitMQ!");
        iMessageSender.sendMessage(null, "hello.queue", "Hello from RabbitMQ2!");

        /*while (true){
            Message message = jmsTemplate.receive("hello.queue");
            System.out.println("收到消息: " + message);
        }*/

        //receiver().getLatch().await(10000, TimeUnit.MILLISECONDS);
        //context.close();
    }
}
