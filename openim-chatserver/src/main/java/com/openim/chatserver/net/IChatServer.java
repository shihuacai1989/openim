package com.openim.chatserver.net;

import org.springframework.beans.factory.InitializingBean;

/**
 * Created by shihuacai on 2015/8/19.
 */
public interface IChatServer extends InitializingBean{
    void startServer();
}
