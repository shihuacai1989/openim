package com.openim.chatserver.net.handler.netty.v2;

import com.openim.chatserver.net.handler.IMessageHandler;
import com.openim.chatserver.listener.ApplicationContextAware;
import com.openim.common.im.codec.netty.ExchangeMessage;
import com.openim.common.mq.IMessageSender;
import io.netty.channel.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by shihuacai on 2015/7/22.
 */
public class LoginHandler implements IMessageHandler<ExchangeMessage, Channel> {

    private static final Logger LOG = LoggerFactory.getLogger(LoginHandler.class);

    private IMessageSender messageSender;

    public LoginHandler() {
        messageSender = ApplicationContextAware.getBean(IMessageSender.class);
    }

    @Override
    public void handle(ExchangeMessage deviceMsg, Channel channel) {
        /*if (deviceMsg != null) {
            int type = deviceMsg.getType();
            if (type == DeviceMsgType.LOGIN) {
                String loginId = deviceMsg.getLoginId();
                String pwd = deviceMsg.getPwd();
                //后期完成登录验证

                ChannelUtil.add(loginId, channel);

                ProtobufDeviceMsg.DeviceMsg.Builder builder = ProtobufDeviceMsg.DeviceMsg.newBuilder(deviceMsg).setServerQueue(BeanConfiguration.chatQueueName);
                messageSender.sendMessage(MQConstants.openimExchange, MQConstants.loginRouteKey, builder.build().toByteArray());
            }
        }*/
    }
}
