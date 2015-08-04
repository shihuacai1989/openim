package com.openim.chatserver.dispatch;

import com.openim.chatserver.ChannelUtil;
import com.openim.common.im.bean.DeviceMsg;
import com.openim.common.mq.IMessageDispatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;

/**
 * Created by shihuacai on 2015/7/29.
 */
public class JDKChatServerMessageDispatch implements IMessageDispatch {

    private static final Logger LOG = LoggerFactory.getLogger(JDKChatServerMessageDispatch.class);

    @Override
    public void dispatchMessage(String exchange, String routeKey, byte[] bytes) {

        try {
            ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
            ObjectInputStream input = new ObjectInputStream(bais);
            DeviceMsg msg = (DeviceMsg) input.readObject();
            ChannelUtil.sendMessage(msg.getTo(), msg);
        } catch (Exception e) {
            LOG.error(e.toString());
        }
    }
}
