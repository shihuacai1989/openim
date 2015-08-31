package com.openim.chatserver.dispatch.handle.v2;

import com.openim.chatserver.ChannelUtil;
import com.openim.common.im.bean.ExchangeMessage;
import com.openim.common.im.bean.protbuf.ProtobufChatMessage;

/**
 * Created by shihc on 2015/8/26.
 */
public class ChatHandler implements IMessageHandler {

    @Override
    public void handle(ExchangeMessage exchangeMessage) {
        ProtobufChatMessage.ChatMessage  chatMessage = exchangeMessage.getMessageLite();
        String to = chatMessage.getTo();
        ChannelUtil.sendMessage(to, exchangeMessage);
    }
}
