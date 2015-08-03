package com.openim.manager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;

/**
 * Created by shihc on 2015/7/30.
 */
@Configuration
@EnableAutoConfiguration
@ComponentScan("com.openim")
public class ManagerApplication{

    @Autowired
    MongoTemplate mongoTemplate;

    public static void main(String[] args){
        SpringApplication springApplication = new SpringApplication(ManagerApplication.class);
        //springApplication.addListeners(new ApplicationContextAware());
        springApplication.run(args);
    }
}
