package com.openim.chatserver.dispatch;

import com.openim.common.mq.IMessageDispatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * protocolbuffer
 * Created by shihuacai on 2015/7/29.
 */
public class ProtobufChatServerMessageDispatch implements IMessageDispatch {

    private static final Logger LOG = LoggerFactory.getLogger(ProtobufChatServerMessageDispatch.class);

    @Override
    public void dispatchMessage(String exchange, String routeKey, byte[] bytes) {
        LOG.error("待完成");
    }
}
