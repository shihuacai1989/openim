package com.openim.chatserver.net.netty.v2.hander;

import com.openim.chatserver.ChannelUtil;
import com.openim.chatserver.configuration.BeanConfiguration;
import com.openim.chatserver.net.netty.v2.INettyMessageHandlerV2;
import com.openim.common.im.bean.ExchangeMessage;
import com.openim.common.im.bean.MessageType;
import com.openim.common.im.bean.protbuf.ProtobufConnectMessage;
import com.openim.common.im.codec.mq.MQBsonCodecUtilV2;
import com.openim.common.mq.IMessageSender;
import com.openim.common.mq.constants.MQConstants;
import io.netty.channel.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by shihuacai on 2015/7/22.
 */
@Component
public class LoginHandlerV2 implements INettyMessageHandlerV2 {

    private static final Logger LOG = LoggerFactory.getLogger(LoginHandlerV2.class);

    //private static final IMQCodec<ExchangeMessage> mqCodec = new MQBsonCodecUtilV2();

    @Autowired
    private IMessageSender messageSender;

    /*public LoginHandlerV2() {
        messageSender = ApplicationContextAware.getBean(IMessageSender.class);
    }*/

    @Override
    public void handle(Channel channel, ExchangeMessage exchangeMessage) {

        if (exchangeMessage.getType() == MessageType.LOGIN) {
            try {
                ProtobufConnectMessage.ConnectMessage connectMessage = (ProtobufConnectMessage.ConnectMessage)exchangeMessage.getMessageLite();
                //后期完成登录验证
                String pwd = connectMessage.getPassword();
                String loginId = connectMessage.getLoginId();
                ChannelUtil.add(loginId, channel);

                connectMessage = connectMessage.toBuilder().setServerQueue(BeanConfiguration.chatQueueName).build();
                exchangeMessage.setMessageLite(connectMessage);
                messageSender.sendMessage(MQConstants.openimExchange, MQConstants.loginRouteKey, MQBsonCodecUtilV2.encode(exchangeMessage));
            }catch (Exception e){
                LOG.error(e.toString());
            }
        } else {
            LOG.error("数据类型错误: " + MQBsonCodecUtilV2.encode(exchangeMessage));
        }
    }
}
