package com.openim.manager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Created by shihc on 2015/7/30.
 */
@SpringBootApplication
public class ManagerApplication {
    public static void main(String[] args){
        SpringApplication springApplication = new SpringApplication(ManagerApplication.class);
        //springApplication.addListeners(new ApplicationContextAware());
        springApplication.run(args);
    }
}
