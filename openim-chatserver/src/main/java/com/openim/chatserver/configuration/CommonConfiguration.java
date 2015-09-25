package com.openim.chatserver.configuration;

import com.openim.chatserver.dispatch.ChatServerMessageQueueDispatchV2;
import com.openim.common.mq.MessageQueueDispatch;
import com.openim.common.mq.constants.MQConstants;
import com.openim.common.util.IPUtil;
import com.openim.common.zk.OpenIMZKClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by shihuacai on 2015/7/29.
 */
@Configuration
public class CommonConfiguration /*implements InitializingBean*/ {

    @Value("${zkServers}")
    private String zkServers;

    @Deprecated
    public static String chatQueueName;

    @Value("${chat.port}")
    protected String port;

    @Bean
    MessageQueueDispatch messageDispatch() {
        return new ChatServerMessageQueueDispatchV2();
    }

    @Bean(name = "chatServerListenerQueue")
    protected String chatServerListenerQueue() {
        chatQueueName = MQConstants.chatServerQueueTemplate.replace("{server}", IPUtil.getLocalIP()).replace("{port}", port);
        return chatQueueName;
    }

    @Autowired
    OpenIMZKClient openIMZKClient(){
        OpenIMZKClient client = new OpenIMZKClient(zkServers, OpenIMZKClient.ClientType.curator);
        return client;
    }



    /*@Override
    public void afterPropertiesSet() throws Exception {
        //chatServerListenerQueue();
    }*/
}
