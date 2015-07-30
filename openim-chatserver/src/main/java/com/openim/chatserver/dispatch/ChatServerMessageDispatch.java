package com.openim.chatserver.dispatch;

import com.alibaba.fastjson.JSON;
import com.openim.chatserver.ChannelUtil;
import com.openim.common.bean.ChatMessage;
import com.openim.common.mq.IMessageDispatch;
import com.openim.common.util.CharsetUtil;
import org.springframework.stereotype.Component;

/**
 * Created by shihuacai on 2015/7/29.
 */
@Component
public class ChatServerMessageDispatch implements IMessageDispatch {

    @Override
    public void dispatchMessage(String exchange, String routeKey, byte[] bytes) {
        String message = new String(bytes, CharsetUtil.utf8);
        ChatMessage chatMessage = JSON.parseObject(message, ChatMessage.class);
        ChannelUtil.sendMessage(chatMessage.getTo(), message);
    }
}
