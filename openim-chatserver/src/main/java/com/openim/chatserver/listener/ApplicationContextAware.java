package com.openim.chatserver.listener;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

/**
 * Created by shihuacai on 2015/7/21.
 * 系统启动监听器
 */
public class ApplicationContextAware implements ApplicationListener<ContextRefreshedEvent> {
    private static ApplicationContext applicationContext;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        applicationContext = event.getApplicationContext();
    }

    public static Object getBean(String name){
        return applicationContext.getBean(name);
    }

    public static <T> T getBean(Class<T> clazz){
        return applicationContext.getBean(clazz);
    }
}
