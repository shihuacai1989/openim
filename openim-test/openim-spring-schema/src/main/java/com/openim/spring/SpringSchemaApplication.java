package com.openim.spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

/**
 * Created by shihc on 2015/7/30.
 */
@Configuration
@EnableAutoConfiguration
@ComponentScan("com.openim")
@ImportResource({"classpath:spring-api.xml"})
public class SpringSchemaApplication {
    public static void main(String[] args) {
        SpringApplication springApplication = new SpringApplication(SpringSchemaApplication.class);
        //springApplication.addListeners(new ApplicationContextAware());
        springApplication.run(args);
    }
}
