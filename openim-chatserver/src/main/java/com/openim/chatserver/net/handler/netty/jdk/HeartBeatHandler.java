package com.openim.chatserver.net.handler.netty.jdk;

import com.openim.common.im.bean.DeviceMsg;
import com.openim.common.im.bean.DeviceMsgType;
import io.netty.channel.Channel;

/**
 * Created by shihuacai on 2015/7/22.
 */
@Deprecated
public class HeartBeatHandler implements IMessageHandler {
    @Override
    public void handle(DeviceMsg jsonObject, HandlerChain handlerChain, Channel channel) {
        if (jsonObject != null) {
            int type = jsonObject.getType();
            if (type == DeviceMsgType.HEART_BEAT) {

            } else {
                handlerChain.handle(jsonObject, handlerChain, channel);
            }
        }
    }
}
