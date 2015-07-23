package com.openim.server.listener;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

/**
 * Created by shihuacai on 2015/7/21.
 * 系统启动监听器
 */
@Deprecated
public class ApplicationStartUp implements ApplicationListener<ContextRefreshedEvent> {
    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {

    }
}
