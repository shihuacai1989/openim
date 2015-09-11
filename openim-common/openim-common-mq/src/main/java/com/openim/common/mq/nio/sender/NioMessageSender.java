package com.openim.common.mq.nio.sender;

import com.openim.common.mq.IMessageSender;

/**
 * Created by shihuacai on 2015/7/28.
 */
public class NioMessageSender implements IMessageSender {

    @Override
    public void sendMessage(String exchange, String routeKey, Object message) {
    }
}
