package com.openim.common.mq.kafka.sender;

import com.openim.common.mq.IMessageSender;

/**
 * Created by shihuacai on 2015/7/28.
 */
public class KafkaMQMessageSender implements IMessageSender {

    @Override
    public void sendMessage(String exchange, String routeKey, Object message) {

    }
}
