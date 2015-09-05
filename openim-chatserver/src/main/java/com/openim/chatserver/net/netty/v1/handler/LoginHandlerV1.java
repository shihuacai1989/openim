package com.openim.chatserver.net.netty.v1.handler;

import com.openim.chatserver.ChannelUtil;
import com.openim.chatserver.configuration.BeanConfiguration;
import com.openim.chatserver.listener.ApplicationContextInitialized;
import com.openim.chatserver.net.netty.v1.INettyMessageHandlerV1;
import com.openim.common.im.bean.MessageType;
import com.openim.common.im.bean.ProtobufDeviceMsg;
import com.openim.common.mq.IMessageSender;
import com.openim.common.mq.constants.MQConstants;
import io.netty.channel.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by shihuacai on 2015/7/22.
 */
public class LoginHandlerV1 implements INettyMessageHandlerV1 {

    private static final Logger LOG = LoggerFactory.getLogger(LoginHandlerV1.class);

    private IMessageSender messageSender;

    public LoginHandlerV1() {
        messageSender = ApplicationContextInitialized.getBean(IMessageSender.class);
    }

    @Override
    public void handle(Channel channel,ProtobufDeviceMsg.DeviceMsg deviceMsg) {
        if (deviceMsg != null) {
            int type = deviceMsg.getType();
            if (type == MessageType.LOGIN) {
                String loginId = deviceMsg.getLoginId();
                String pwd = deviceMsg.getPwd();
                //后期完成登录验证

                ChannelUtil.add(loginId, channel);

                ProtobufDeviceMsg.DeviceMsg.Builder builder = ProtobufDeviceMsg.DeviceMsg.newBuilder(deviceMsg).setServerQueue(BeanConfiguration.chatQueueName);
                messageSender.sendMessage(MQConstants.openimExchange, MQConstants.loginRouteKey, builder.build().toByteArray());
            }
        }
    }
}
