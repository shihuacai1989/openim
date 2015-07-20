package com.openim.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * Created by shihuacai on 2015/7/20.
 */
@Configuration
@EnableAutoConfiguration
@ComponentScan
public class Application {
    public void main(String[] args){
        SpringApplication.run(Application.class, args);
    }
}
