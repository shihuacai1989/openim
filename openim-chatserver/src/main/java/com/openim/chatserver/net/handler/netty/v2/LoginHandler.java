package com.openim.chatserver.net.handler.netty.v2;

import com.openim.chatserver.ChannelUtil;
import com.openim.chatserver.configuration.BeanConfiguration;
import com.openim.chatserver.listener.ApplicationContextAware;
import com.openim.chatserver.net.handler.IMessageHandler;
import com.openim.common.im.bean.DeviceMsgType;
import com.openim.common.im.bean.protbuf.ProtobufConnectMessage;
import com.openim.common.im.codec.netty.v2.ExchangeMessage;
import com.openim.common.mq.IMessageSender;
import com.openim.common.mq.constants.MQConstants;
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
    public void handle(ExchangeMessage exchangeMessage, Channel channel) {
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
        if (exchangeMessage.getType() == DeviceMsgType.LOGIN) {
            try {
                ProtobufConnectMessage.ConnectMessage connectMessage = (ProtobufConnectMessage.ConnectMessage)exchangeMessage.getMessageLite();
                //后期完成登录验证
                String pwd = connectMessage.getPassword();
                String loginId = connectMessage.getLoginId();
                ChannelUtil.add(loginId, channel);

                ProtobufConnectMessage.ConnectMessage.Builder builder = connectMessage.toBuilder().setServerQueue(BeanConfiguration.chatQueueName);
                exchangeMessage.setMessageLite(builder.build());
                messageSender.sendMessage(MQConstants.openimExchange, MQConstants.loginRouteKey, exchangeMessage.toBsonString());
            }catch (Exception e){
                LOG.error(e.toString());
            }
        } else {
            LOG.error("数据类型错误: " + exchangeMessage.toBsonString());
        }
    }
}
