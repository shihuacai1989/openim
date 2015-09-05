package com.openim.chatserver.net.handler.v2;

import com.openim.chatserver.net.bean.Session;
import com.openim.common.im.annotation.HandleGroup;
import com.openim.common.im.annotation.HandleGroupConstants;
import com.openim.common.im.bean.ExchangeMessage;
import com.openim.common.im.bean.MessageType;
import com.openim.common.im.bean.protbuf.ProtobufChatMessage;
import com.openim.common.im.codec.mq.MQBsonCodecUtilV2;
import com.openim.common.mq.IMessageSender;
import com.openim.common.mq.constants.MQConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by shihuacai on 2015/7/22.
 */
@Component
@HandleGroup(name = HandleGroupConstants.CHAT_SERVER_NiO_HANDLER_V2, type = MessageType.CHAT)
public class ChatHandlerV2 implements IMessageHandlerV2 {

    private static final Logger LOG = LoggerFactory.getLogger(ChatHandlerV2.class);

    @Autowired
    private IMessageSender messageSender;

    @Override
    public void handle(Session session, ExchangeMessage exchangeMessage) {
        if (exchangeMessage.getType() == MessageType.CHAT) {
            try {
                String loginId = session.getLoginId();

                ProtobufChatMessage.ChatMessage chatMessage = exchangeMessage.getMessageLite();
                chatMessage = chatMessage.toBuilder().setFrom(loginId).build();
                exchangeMessage.setMessageLite(chatMessage);
                messageSender.sendMessage(MQConstants.openimExchange, MQConstants.chatRouteKey, MQBsonCodecUtilV2.encode(exchangeMessage));
            }catch (Exception e){
                LOG.error(e.toString());
            }
        } else {
            LOG.error("数据类型错误: " + MQBsonCodecUtilV2.encode(exchangeMessage));
        }
    }
}
