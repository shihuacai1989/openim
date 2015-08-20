package com.openim.netty.handler.protobuf;

import com.google.protobuf.MessageLite;

/**
 * Created by shihuacai on 2015/8/20.
 */
public class ExchangeMessage {

    private static final int MAX_VALUE = 127;
    private int type;
    private MessageLite messageLite;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        if(type > MAX_VALUE){
            throw new IllegalArgumentException("type must less or equal than " + MAX_VALUE);
        }
        this.type = type;
    }

    public MessageLite getMessageLite() {
        return messageLite;
    }

    public void setMessageLite(MessageLite messageLite) {
        this.messageLite = messageLite;
    }
}
