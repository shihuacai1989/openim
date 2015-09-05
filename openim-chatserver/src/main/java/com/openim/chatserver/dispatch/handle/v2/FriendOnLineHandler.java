package com.openim.chatserver.dispatch.handle.v2;

import com.openim.chatserver.SessionManager;
import com.openim.common.im.annotation.HandleGroup;
import com.openim.common.im.annotation.HandleGroupConstants;
import com.openim.common.im.bean.ExchangeMessage;
import com.openim.common.im.bean.MessageType;
import com.openim.common.im.bean.protbuf.ProtobufFriendOnLineMessage;
import org.springframework.stereotype.Component;

/**
 * Created by shihc on 2015/8/26.
 */
@Component
@HandleGroup(name = HandleGroupConstants.CHAT_SERVER_MQ_HANDLER, type = MessageType.FRIEND_ONLINE)
public class FriendOnLineHandler implements IMessageHandler {

    @Override
    public void handle(ExchangeMessage exchangeMessage) {
        ProtobufFriendOnLineMessage.FriendOnLineMessage  chatMessage = exchangeMessage.getMessageLite();
        String loginId = chatMessage.getLoginId();
        SessionManager.sendMessage(loginId, exchangeMessage);
    }
}
