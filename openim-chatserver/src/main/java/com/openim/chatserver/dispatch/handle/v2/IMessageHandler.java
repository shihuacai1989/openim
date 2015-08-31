package com.openim.chatserver.dispatch.handle.v2;

import com.google.protobuf.MessageLite;
import com.openim.common.im.bean.ExchangeMessage;
import org.springframework.amqp.core.MessageListener;

/**
 * Created by shihc on 2015/8/26.
 */
public interface IMessageHandler {
    void handle(ExchangeMessage exchangeMessage);
}
