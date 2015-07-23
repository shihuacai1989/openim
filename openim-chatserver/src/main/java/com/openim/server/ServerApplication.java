package com.openim.server;

import com.openim.server.listener.ApplicationStartUp;
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
public class ServerApplication {
    public static void main(String[] args){
        SpringApplication springApplication = new SpringApplication(ServerApplication.class);
        springApplication.addListeners(new ApplicationStartUp());
        springApplication.run(args);

    }
}
