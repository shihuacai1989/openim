package com.openim.chatserver.dispatch;

import com.openim.chatserver.dispatch.handle.v2.ChatHandler;
import com.openim.chatserver.dispatch.handle.v2.IMessageHandler;
import com.openim.common.im.bean.ExchangeMessage;
import com.openim.common.im.bean.MessageType;
import com.openim.common.mq.IMessageDispatch;
import com.openim.common.im.codec.mq.IMQCodec;
import com.openim.common.im.codec.mq.MQBsonCodec;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * protocolbuffer
 * Created by shihuacai on 2015/7/29.
 */
public class ChatServerMessageDispatchV2 implements IMessageDispatch {

    private static final Logger LOG = LoggerFactory.getLogger(ChatServerMessageDispatchV2.class);

    private static IMessageHandler chatHandler = new ChatHandler();

    private IMQCodec<ExchangeMessage> mqCodec = new MQBsonCodec();

    @Override
    public void dispatchMessage(String exchange, String routeKey, byte[] bytes) {
        try {
            /*BSONObject bsonObject = BSON.decode(bytes);
            int type = Integer.valueOf(String.valueOf(bsonObject.get("type")));
            byte[] messageBytes = (byte[])bsonObject.get("message");

            ExchangeMessage exchangeMessage = new ExchangeMessage();
            exchangeMessage.setType(type);
            MessageLite messageLite = MessageParserV2.parse(type, messageBytes);
            exchangeMessage.setMessageLite(messageLite);*/

            ExchangeMessage exchangeMessage = mqCodec.decode(bytes);
            if(exchangeMessage != null){
                int type = exchangeMessage.getType();
                if(type == MessageType.CHAT){
                    chatHandler.handle(exchangeMessage);
                }
            }
        }catch (Exception e){
            LOG.error(e.toString());
        }
    }
}
