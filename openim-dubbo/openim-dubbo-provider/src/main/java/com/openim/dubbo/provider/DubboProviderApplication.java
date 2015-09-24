package com.openim.dubbo.provider;

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
@ImportResource({"classpath:dubbo.xml"})
public class DubboProviderApplication {
    public static void main(String[] args) {
        SpringApplication springApplication = new SpringApplication(DubboProviderApplication.class);
        //springApplication.addListeners(new ApplicationContextAware());
        springApplication.run(args);
    }
}
