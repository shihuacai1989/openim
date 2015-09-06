package com.openim.manager.dispatch;

import com.google.protobuf.InvalidProtocolBufferException;
import com.openim.common.im.bean.MessageType;
import com.openim.common.im.bean.ProtobufDeviceMsg;
import com.openim.common.mq.MessageQueueDispatch;
import com.openim.manager.dispatch.handler.v1.LoginHandler;
import com.openim.manager.dispatch.handler.v1.LogoutHandler;
import com.openim.manager.dispatch.handler.v1.SendHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.nio.charset.Charset;

/**
 * Created by shihuacai on 2015/7/29.
 */
@Deprecated
public class MessageDispatchV1 extends MessageQueueDispatch {

    private static final Logger LOG = LoggerFactory.getLogger(MessageDispatchV1.class);
    private static final Charset charset = Charset.forName("UTF-8");
    @Autowired
    private LoginHandler loginHandler;
    @Autowired
    private LogoutHandler logoutHandler;
    @Autowired
    private SendHandler sendHandler;

    @Override
    public void dispatchMessage(String exchange, String routeKey, byte[] bytes) {
        try {
            ProtobufDeviceMsg.DeviceMsg msg = ProtobufDeviceMsg.DeviceMsg.parseFrom(bytes);

            int type = msg.getType();
            if (type == MessageType.CHAT) {
                sendHandler.handle(msg, null);
            } else if (type == MessageType.LOGIN) {
                loginHandler.handle(msg, null);
            } else if (type == MessageType.LOGOUT) {
                logoutHandler.handle(msg, null);
            } else {
                LOG.error("无法处理收到的消息：{}", msg);
            }
        } catch (InvalidProtocolBufferException e) {
            LOG.error(e.toString());
        }

    }
}
