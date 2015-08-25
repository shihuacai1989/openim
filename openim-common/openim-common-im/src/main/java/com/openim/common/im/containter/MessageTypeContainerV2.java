package com.openim.common.im.containter;

import com.google.protobuf.MessageLite;
import com.openim.common.im.bean.DeviceMsgType;
import com.openim.common.im.bean.protbuf.*;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by shihc on 2015/8/25.
 */
public class MessageTypeContainerV2 {
    private static final Map<Integer, MessageLite> messageTypeMap = new HashMap<Integer, MessageLite>();

    static {
        MessageLite chatMessage = ProtobufChatMessage.ChatMessage.getDefaultInstance();
        MessageLite connectMessage = ProtobufConnectMessage.ConnectMessage.getDefaultInstance();
        MessageLite disconnectMessage = ProtobufDisconnectMessage.DisconnectMessage.getDefaultInstance();
        MessageLite heartBeatMessage = ProtobufHeartBeatMessage.HeartBeatMessage.getDefaultInstance();
        MessageLite receiveMessage = ProtobufReceiveMessage.ReceiveMessage.getDefaultInstance();
        messageTypeMap.put(DeviceMsgType.CHAT, chatMessage);
        messageTypeMap.put(DeviceMsgType.LOGIN, connectMessage);
        messageTypeMap.put(DeviceMsgType.LOGOUT, disconnectMessage);
        messageTypeMap.put(DeviceMsgType.HEART_BEAT, heartBeatMessage);
        messageTypeMap.put(DeviceMsgType.RECEIVE, receiveMessage);

    }

    public static MessageLite getMessageLite(int type){
        return messageTypeMap.get(type);
    }
}
