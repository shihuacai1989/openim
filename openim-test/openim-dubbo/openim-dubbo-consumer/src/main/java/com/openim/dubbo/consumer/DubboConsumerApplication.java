package com.openim.dubbo.consumer;

import com.openim.dubbo.common.service.IDemoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

import java.util.List;

/**
 * Created by shihc on 2015/7/30.
 */
@Configuration
@EnableAutoConfiguration
@ComponentScan("com.openim")
@ImportResource({"classpath:consumer.xml"})
public class DubboConsumerApplication  implements CommandLineRunner {

    @Autowired
    ApplicationContext context;

    public static void main(String[] args) {
        SpringApplication springApplication = new SpringApplication(DubboConsumerApplication.class);
        //springApplication.addListeners(new ApplicationContextAware());
        springApplication.run(args);
    }

    @Override
    public void run(String... args) throws Exception {
        IDemoService demoService = (IDemoService) context.getBean("demoService"); //
        String hello = demoService.sayHello("tom"); // ִ
        System.out.println(hello); //

        //
        List list = demoService.getUsers();
        if (list != null && list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                System.out.println(list.get(i));
            }
        }

    }
}
