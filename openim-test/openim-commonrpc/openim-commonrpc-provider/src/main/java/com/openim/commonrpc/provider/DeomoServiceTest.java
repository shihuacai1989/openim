package com.openim.commonrpc.provider;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

/**
 * Created by shihuacai on 2015/9/29.
 */
@Configuration
@EnableAutoConfiguration
@ComponentScan("com.openim")
@ImportResource({"classpath:provider.xml"})
public class DeomoServiceTest {
    public static void main(String[] args) {
        SpringApplication springApplication = new SpringApplication(DeomoServiceTest.class);
        //springApplication.addListeners(new ApplicationContextAware());
        springApplication.run(args);
    }
}