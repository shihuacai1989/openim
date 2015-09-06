package com.openim.chatserver.dispatch;

import com.openim.chatserver.ChannelUtil;
import com.openim.common.im.bean.ProtobufDeviceMsg;
import com.openim.common.mq.MessageQueueDispatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * protocolbuffer
 * Created by shihuacai on 2015/7/29.
 */
@Deprecated
public class ChatServerMessageDispatchV1 extends MessageQueueDispatch {

    private static final Logger LOG = LoggerFactory.getLogger(ChatServerMessageDispatchV1.class);

    @Override
    public void dispatchMessage(String exchange, String routeKey, byte[] bytes) {
        LOG.error("待完成");
        try {
            ProtobufDeviceMsg.DeviceMsg msg = ProtobufDeviceMsg.DeviceMsg.parseFrom(bytes);
            ChannelUtil.sendMessage(msg.getTo(), msg);
        }catch (Exception e){
            LOG.error(e.toString());
        }
    }
}
