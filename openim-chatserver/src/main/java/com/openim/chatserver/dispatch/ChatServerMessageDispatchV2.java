package com.openim.chatserver.dispatch;

import com.openim.chatserver.ChannelUtil;
import com.openim.common.im.bean.ProtobufDeviceMsg;
import com.openim.common.mq.IMessageDispatch;
import org.bson.BSON;
import org.bson.BSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * protocolbuffer
 * Created by shihuacai on 2015/7/29.
 */
public class ChatServerMessageDispatchV2 implements IMessageDispatch {

    private static final Logger LOG = LoggerFactory.getLogger(ChatServerMessageDispatchV2.class);

    @Override
    public void dispatchMessage(String exchange, String routeKey, byte[] bytes) {
        LOG.error("待完成");
        try {
            BSONObject bsonObject = BSON.decode(bytes);
            int type = Integer.valueOf(String.valueOf(bsonObject.get("type")));
            byte[] messageBytes = (byte[])bsonObject.get("message");

            ProtobufDeviceMsg.DeviceMsg msg = ProtobufDeviceMsg.DeviceMsg.parseFrom(bytes);
            ChannelUtil.sendMessage(msg.getTo(), msg);
        }catch (Exception e){
            LOG.error(e.toString());
        }
    }
}
