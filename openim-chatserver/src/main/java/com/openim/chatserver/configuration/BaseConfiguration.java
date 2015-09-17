package com.openim.chatserver.configuration;

import com.openim.chatserver.dispatch.ChatServerMessageQueueDispatchV2;
import com.openim.chatserver.net.IChatServer;
import com.openim.chatserver.net.NetMessageDispatch;
import com.openim.chatserver.net.netty.v2.NettyChatServerV2;
import com.openim.chatserver.net.netty.v2.NettyMessageDispatchV2;
import com.openim.common.mq.MessageQueueDispatch;
import com.openim.common.mq.constants.MQConstants;
import com.openim.common.util.IPUtil;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;

/**
 * Created by shihuacai on 2015/7/29.
 */
public class BaseConfiguration implements InitializingBean {

    public static String chatQueueName;

    @Value("${chat.port}")
    protected String port;

    @Bean
    MessageQueueDispatch messageDispatch() {
        return new ChatServerMessageQueueDispatchV2();
    }

    /*==========================================================*/
    //netty实现
    @Bean
    NetMessageDispatch netMessageDispatch(){
        return new NettyMessageDispatchV2();
    }

    @Bean
    IChatServer chatServer(){
        return new NettyChatServerV2();
    }

    //mina实现
    /*@Bean
    INetMessageDispatch netMessageDispatch(){
        return new MinaMessageDispatchV2();
    }

    @Bean
    IChatServer chatServer(){
        return new MinaChatServerV2();
    }*/
    /*==========================================================*/


    protected String chatServerListenerQueue() {
        chatQueueName = MQConstants.chatServerQueueTemplate.replace("{server}", IPUtil.getLocalIP()).replace("{port}", port);
        return chatQueueName;
    }


    @Override
    public void afterPropertiesSet() throws Exception {
        chatServerListenerQueue();
    }
}
