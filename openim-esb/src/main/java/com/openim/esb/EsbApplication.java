package com.openim.esb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * Created by shihuacai on 2015/7/20.
 */
@Configuration
@EnableAutoConfiguration
@ComponentScan("com.openim")
public class EsbApplication {
    public static void main(String[] args) {
        SpringApplication springApplication = new SpringApplication(EsbApplication.class);
        //springApplication.addListeners(new ApplicationContextAware());
        springApplication.run(args);
    }
}
