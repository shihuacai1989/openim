package com.openim.common.mq.codec;

import com.google.protobuf.MessageLite;
import com.mongodb.BasicDBObject;
import com.openim.common.im.bean.ExchangeMessage;
import com.openim.common.im.bean.MessageType;
import com.openim.common.im.bean.protbuf.ProtobufChatMessage;
import com.openim.common.im.bean.protbuf.ProtobufConnectMessage;
import com.openim.common.im.bean.protbuf.ProtobufDisconnectMessage;
import com.openim.common.im.bean.protbuf.ProtobufHeartBeatMessage;
import org.bson.BSON;
import org.bson.BSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by shihuacai on 2015/8/26.
 */
public class MQBsonCodec implements IMQCodec<ExchangeMessage> {

    private static final Logger LOG = LoggerFactory.getLogger(MQBsonCodec.class);

    @Override
    public String encode(ExchangeMessage msg) {
        if(msg != null){
            BasicDBObject obj = new BasicDBObject();
            obj.put("type", msg.getType());
            obj.put("message", msg.getMessageLite().toByteArray());
            return obj.toString();
        }
        return null;
    }

    @Override
    public ExchangeMessage decode(byte[] bytes) {
        try {
            BSONObject bsonObject = BSON.decode(bytes);
            int type = Integer.valueOf(String.valueOf(bsonObject.get("type")));
            byte[] messageBytes = (byte[])bsonObject.get("message");

            ExchangeMessage exchangeMessage = new ExchangeMessage();
            exchangeMessage.setType(type);
            MessageLite messageLite = parse(type, messageBytes);
            exchangeMessage.setMessageLite(messageLite);
            return exchangeMessage;
        }catch (Exception e){
            LOG.error(e.toString());
        }
        return null;
    }

    private MessageLite parse(int type, byte[] bytes){
        try {
            if(type == MessageType.CHAT){
                return ProtobufChatMessage.ChatMessage.parseFrom(bytes);
            }else if(type == MessageType.LOGOUT){
                return ProtobufDisconnectMessage.DisconnectMessage.parseFrom(bytes);
            }else if(type == MessageType.LOGIN){
                return ProtobufConnectMessage.ConnectMessage.parseFrom(bytes);
            }else if(type == MessageType.HEART_BEAT){
                return ProtobufHeartBeatMessage.HeartBeatMessage.parseFrom(bytes);
            }else if(type == MessageType.LEAVE){
                return null;
            }else if(type == MessageType.RECEIVE){
                return null;
            }else if(type == MessageType.Working){
                return null;
            }else{
                return null;
            }
        }catch (Exception e){
            LOG.error(e.toString());
        }
        return null;
    }
}
