package com.openim.chatserver.configuration;

import com.openim.chatserver.net.IChatServer;
import com.openim.chatserver.net.NetMessageDispatch;
import com.openim.chatserver.net.netty.v2.NettyChatServerV2;
import com.openim.chatserver.net.netty.v2.NettyMessageDispatchV2;
import org.springframework.context.annotation.Bean;

/**
 * Created by shihuacai on 2015/7/29.
 */
//@Configuration
public class NettyConfiguration {

    @Bean
    NetMessageDispatch netMessageDispatch(){
        return new NettyMessageDispatchV2();
    }

    @Bean
    IChatServer chatServer(){
        return new NettyChatServerV2();
    }

}
