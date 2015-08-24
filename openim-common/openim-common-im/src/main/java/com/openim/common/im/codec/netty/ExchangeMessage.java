package com.openim.common.im.codec.netty;

import com.google.protobuf.MessageLite;
import com.mongodb.BasicDBObject;

/**
 * Created by shihuacai on 2015/8/20.
 */
public class ExchangeMessage {


    private static final int MAX_VALUE = 127;
    private static final int MIN_VALUE = 0;

    //@JsonProperty
    private int type;

    //@JsonIgnore
    private MessageLite messageLite;

    //@JsonProperty
    public int getType() {
        return type;
    }

    public void setType(int type) {
        if(type > MAX_VALUE || type < MIN_VALUE){
            throw new IllegalArgumentException(MIN_VALUE + " <= type <= " + MAX_VALUE);
        }
        this.type = type;
    }
    //@JsonIgnore
    public MessageLite getMessageLite() {
        return messageLite;
    }

    public void setMessageLite(MessageLite messageLite) {
        this.messageLite = messageLite;
    }

    @Override
    public String toString() {
        return toBsonString();
    }
    //@JsonIgnore
    public String toBsonString(){
        BasicDBObject obj = new BasicDBObject();
        obj.put("type", type);
        obj.put("message", messageLite.toByteArray());
        return obj.toString();
    }
}
