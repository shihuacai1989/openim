package com.openim.chatserver.dispatch.handle.v2;

import com.openim.chatserver.SessionManager;
import com.openim.common.im.bean.ExchangeMessage;
import com.openim.common.im.bean.protbuf.ProtobufFriendOnLineMessage;

/**
 * Created by shihc on 2015/8/26.
 */
public class FriendOnLineHandler implements IMessageHandler {

    @Override
    public void handle(ExchangeMessage exchangeMessage) {
        ProtobufFriendOnLineMessage.FriendOnLineMessage  chatMessage = exchangeMessage.getMessageLite();
        String loginId = chatMessage.getLoginId();
        SessionManager.sendMessage(loginId, exchangeMessage);
    }
}
