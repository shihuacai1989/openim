package com.openim.manager.dispatch;

import com.google.protobuf.InvalidProtocolBufferException;
import com.openim.common.im.bean.DeviceMsgType;
import com.openim.common.im.bean.ProtobufDeviceMsg;
import com.openim.common.mq.IMessageDispatch;
import com.openim.manager.handler.protobuf.ProtobufLoginHandler;
import com.openim.manager.handler.protobuf.ProtobufLogoutHandler;
import com.openim.manager.handler.protobuf.ProtobufSendHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.nio.charset.Charset;

/**
 * Created by shihuacai on 2015/7/29.
 */
public class ProtobufMessageDispatch implements IMessageDispatch {

    private static final Logger LOG = LoggerFactory.getLogger(ProtobufMessageDispatch.class);
    private static final Charset charset = Charset.forName("UTF-8");
    @Autowired
    private ProtobufLoginHandler loginHandler;
    @Autowired
    private ProtobufLogoutHandler logoutHandler;
    @Autowired
    private ProtobufSendHandler sendHandler;

    @Override
    public void dispatchMessage(String exchange, String routeKey, byte[] bytes) {
        try {
            ProtobufDeviceMsg.DeviceMsg msg = ProtobufDeviceMsg.DeviceMsg.parseFrom(bytes);

            int type = msg.getType();
            if (type == DeviceMsgType.SEND) {
                sendHandler.handle(msg, null);
            } else if (type == DeviceMsgType.LOGIN) {
                loginHandler.handle(msg, null);
            } else if (type == DeviceMsgType.LOGOUT) {
                logoutHandler.handle(msg, null);
            } else {
                LOG.error("无法处理收到的消息：{}", msg);
            }
        } catch (InvalidProtocolBufferException e) {
            LOG.error(e.toString());
        }

    }
}
