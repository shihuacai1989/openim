package com.openim.common.im.containter;

import com.google.protobuf.MessageLite;
import com.openim.common.im.bean.MessageType;
import com.openim.common.im.bean.protbuf.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by shihc on 2015/8/25.
 */
@Deprecated
public class MessageParserV2 {
    private static final Logger LOG = LoggerFactory.getLogger(MessageParserV2.class);

    @Deprecated
    private static final Map<Integer, MessageLite> messageTypeMap = new HashMap<Integer, MessageLite>();

    static {
        MessageLite chatMessage = ProtobufChatMessage.ChatMessage.getDefaultInstance();
        MessageLite connectMessage = ProtobufConnectMessage.ConnectMessage.getDefaultInstance();
        MessageLite disconnectMessage = ProtobufDisconnectMessage.DisconnectMessage.getDefaultInstance();
        MessageLite heartBeatMessage = ProtobufHeartBeatMessage.HeartBeatMessage.getDefaultInstance();
        MessageLite receiveMessage = ProtobufReceiveMessage.ReceiveMessage.getDefaultInstance();
        messageTypeMap.put(MessageType.CHAT, chatMessage);
        messageTypeMap.put(MessageType.LOGIN, connectMessage);
        messageTypeMap.put(MessageType.LOGOUT, disconnectMessage);
        messageTypeMap.put(MessageType.HEART_BEAT, heartBeatMessage);
        messageTypeMap.put(MessageType.RECEIVE, receiveMessage);

    }

    @Deprecated
    public static MessageLite getMessageLite(int type){
        //ProtobufChatMessage.ChatMessage.parseFrom()
        return messageTypeMap.get(type);
    }

    public static MessageLite parse(int type, byte[] bytes){
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
