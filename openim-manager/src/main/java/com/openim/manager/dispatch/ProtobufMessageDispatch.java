package com.openim.manager.dispatch;

import com.openim.common.mq.IMessageDispatch;
import com.openim.manager.handler.jdk.LoginHandler;
import com.openim.manager.handler.jdk.LogoutHandler;
import com.openim.manager.handler.jdk.SendHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.nio.charset.Charset;

/**
 * Created by shihuacai on 2015/7/29.
 */
public class ProtobufMessageDispatch implements IMessageDispatch {

    private static final Logger LOG = LoggerFactory.getLogger(ProtobufMessageDispatch.class);

    @Autowired
    private LoginHandler loginHandler;

    @Autowired
    private LogoutHandler logoutHandler;

    @Autowired
    private SendHandler sendHandler;

    private static final Charset charset = Charset.forName("UTF-8");


    @Override
    public void dispatchMessage(String exchange, String routeKey, byte[] bytes) {
        LOG.warn("ProtobufMessageDispatch待实现");
    }
}
