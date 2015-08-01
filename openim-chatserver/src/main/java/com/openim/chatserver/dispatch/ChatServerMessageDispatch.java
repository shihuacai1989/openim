package com.openim.chatserver.dispatch;

import com.openim.chatserver.ChannelUtil;
import com.openim.common.im.DeviceMsg;
import com.openim.common.mq.IMessageDispatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;

/**
 * Created by shihuacai on 2015/7/29.
 */
@Component
public class ChatServerMessageDispatch implements IMessageDispatch {

    private static final Logger LOG = LoggerFactory.getLogger(ChatServerMessageDispatch.class);

    @Override
    public void dispatchMessage(String exchange, String routeKey, byte[] bytes) {

        try {
            ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
            ObjectInputStream input = new ObjectInputStream(bais);
            DeviceMsg msg = (DeviceMsg)input.readObject();
            ChannelUtil.sendMessage(msg.getTo(), msg);
        }catch (Exception e){
            LOG.error(e.toString());
        }




        /*String message = new String(bytes, CharsetUtil.utf8);
        //ChatMessage chatMessage = JSON.parseObject(message, ChatMessage.class);
        ChannelUtil.sendMessage(chatMessage.getTo(), message);*/
    }
}
