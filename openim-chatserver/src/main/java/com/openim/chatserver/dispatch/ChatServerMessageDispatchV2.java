package com.openim.chatserver.dispatch;

import com.google.protobuf.MessageLite;
import com.openim.chatserver.dispatch.handle.v2.ChatHandler;
import com.openim.chatserver.dispatch.handle.v2.IMessageHandler;
import com.openim.common.im.bean.DeviceMsgType;
import com.openim.common.im.bean.ExchangeMessage;
import com.openim.common.im.containter.MessageTypeContainerV2;
import com.openim.common.mq.IMessageDispatch;
import org.bson.BSON;
import org.bson.BSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * protocolbuffer
 * Created by shihuacai on 2015/7/29.
 */
public class ChatServerMessageDispatchV2 implements IMessageDispatch {

    private static final Logger LOG = LoggerFactory.getLogger(ChatServerMessageDispatchV2.class);

    private static IMessageHandler chatHandler = new ChatHandler();


    @Override
    public void dispatchMessage(String exchange, String routeKey, byte[] bytes) {
        LOG.error("待完成");
        try {
            BSONObject bsonObject = BSON.decode(bytes);
            int type = Integer.valueOf(String.valueOf(bsonObject.get("type")));
            byte[] messageBytes = (byte[])bsonObject.get("message");

            ExchangeMessage exchangeMessage = new ExchangeMessage();
            exchangeMessage.setType(type);
            MessageLite messageLite = MessageTypeContainerV2.getMessageLite(type).newBuilderForType().mergeFrom(messageBytes).build();
            exchangeMessage.setMessageLite(messageLite);


            if(type == DeviceMsgType.CHAT){
                chatHandler.handle(exchangeMessage);
            }
        }catch (Exception e){
            LOG.error(e.toString());
        }
    }
}
