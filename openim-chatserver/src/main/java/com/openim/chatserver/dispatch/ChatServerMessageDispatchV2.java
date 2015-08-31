package com.openim.chatserver.dispatch;

import com.openim.chatserver.dispatch.handle.v2.ChatHandler;
import com.openim.chatserver.dispatch.handle.v2.FriendOnLineHandler;
import com.openim.chatserver.dispatch.handle.v2.IMessageHandler;
import com.openim.common.im.bean.ExchangeMessage;
import com.openim.common.im.bean.MessageType;
import com.openim.common.im.codec.mq.IMQCodec;
import com.openim.common.im.codec.mq.MQBsonCodec;
import com.openim.common.mq.IMessageQueueDispatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by shihuacai on 2015/7/29.
 */
public class ChatServerMessageDispatchV2 implements IMessageQueueDispatch {

    private static final Logger LOG = LoggerFactory.getLogger(ChatServerMessageDispatchV2.class);

    private IMQCodec<ExchangeMessage> mqCodec = new MQBsonCodec();

    private static Map<Integer, IMessageHandler> msgHandler = new HashMap<Integer, IMessageHandler>(){
        {
            put(MessageType.CHAT, new ChatHandler());
            put(MessageType.FRIEND_ONLINE, new FriendOnLineHandler());
        }
    };

    @Override
    public void dispatchMessage(String exchange, String routeKey, byte[] bytes) {
        try {
            ExchangeMessage exchangeMessage = mqCodec.decode(bytes);
            if(exchangeMessage != null){
                int type = exchangeMessage.getType();
                IMessageHandler messageHandler = msgHandler.get(type);
                if(messageHandler != null){
                    messageHandler.handle(exchangeMessage);
                }else{
                    LOG.error("未找到指定type的消息处理器, {}", exchangeMessage);
                }
            }
        }catch (Exception e){
            LOG.error(e.toString());
        }
    }
}
