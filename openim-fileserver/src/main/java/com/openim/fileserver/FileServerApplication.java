package com.openim.fileserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * Created by shihc on 2015/7/30.
 */
@Configuration
@EnableAutoConfiguration
@ComponentScan("com.openim")
public class FileServerApplication {



    public static void main(String[] args) {
        SpringApplication springApplication = new SpringApplication(FileServerApplication.class);
        //springApplication.addListeners(new ApplicationContextAware());
        springApplication.run(args);
    }
}
