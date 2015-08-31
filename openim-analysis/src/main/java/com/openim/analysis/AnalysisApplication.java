package com.openim.analysis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * Created by shihc on 2015/8/27.
 */
@Configuration
@EnableAutoConfiguration
@ComponentScan("com.openim")
public class AnalysisApplication {
    public static void main(String[] args) {
        SpringApplication springApplication = new SpringApplication(AnalysisApplication.class);
        springApplication.run(args);
    }
}
