package com.openim.manager.dispatch;

import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.MessageLite;
import com.mongodb.BasicDBObject;
import com.mongodb.util.JSON;
import com.openim.common.im.bean.DeviceMsgType;
import com.openim.common.im.bean.ExchangeMessage;
import com.openim.common.im.containter.MessageTypeContainerV2;
import com.openim.common.mq.IMessageDispatch;
import com.openim.common.util.CharsetUtil;
import com.openim.manager.dispatch.handler.v2.ChatHandler;
import com.openim.manager.dispatch.handler.v2.ConnectHandler;
import com.openim.manager.dispatch.handler.v2.DisconnectHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.nio.charset.Charset;

/**
 * Created by shihuacai on 2015/7/29.
 */
public class MessageDispatchV2 implements IMessageDispatch {

    private static final Logger LOG = LoggerFactory.getLogger(MessageDispatchV2.class);
    private static final Charset charset = Charset.forName("UTF-8");
    @Autowired
    private ConnectHandler loginHandler;
    @Autowired
    private DisconnectHandler logoutHandler;
    @Autowired
    private ChatHandler sendHandler;

    @Override
    public void dispatchMessage(String exchange, String routeKey, byte[] c) {
        try {
            BasicDBObject doc = (BasicDBObject) JSON.parse(new String(c, CharsetUtil.utf8));
            int type = doc.getInt("type");

            byte[] msgBytes = (byte[])doc.get("message");

            MessageLite messageLite = MessageTypeContainerV2.getMessageLite(type);
            messageLite.newBuilderForType().mergeFrom(msgBytes).build();

            ExchangeMessage exchangeMessage = new ExchangeMessage();
            exchangeMessage.setType(type);
            exchangeMessage.setMessageLite(messageLite);
            if (type == DeviceMsgType.CHAT) {
                sendHandler.handle(exchangeMessage);
            } else if (type == DeviceMsgType.LOGIN) {
                loginHandler.handle(exchangeMessage);
            } else if (type == DeviceMsgType.LOGOUT) {
                logoutHandler.handle(exchangeMessage);
            } else {
                LOG.error("无法处理收到的消息：{}", new String(c, CharsetUtil.utf8));
            }
        } catch (InvalidProtocolBufferException e) {
            LOG.error(e.toString());
        }

    }
}
