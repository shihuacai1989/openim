package com.openim.chatserver.configuration;

import com.openim.chatserver.net.IChatServer;
import com.openim.chatserver.net.NetMessageDispatch;
import com.openim.chatserver.net.mina.v2.MinaChatServerV2;
import com.openim.chatserver.net.mina.v2.MinaMessageDispatchV2;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by shihuacai on 2015/7/29.
 */
@Configuration
public class MinaConfiguration  {

    @Bean
    NetMessageDispatch netMessageDispatch(){
        return new MinaMessageDispatchV2();
    }

    @Bean
    IChatServer chatServer(){
        return new MinaChatServerV2();
    }
}
