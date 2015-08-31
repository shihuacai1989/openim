package com.openim.chatserver.net.server.netty.v2.hander;

import com.openim.chatserver.ChannelUtil;
import com.openim.chatserver.configuration.BeanConfiguration;
import com.openim.chatserver.listener.ApplicationContextAware;
import com.openim.chatserver.net.server.netty.v2.INettyMessageHandlerV2;
import com.openim.common.im.bean.ExchangeMessage;
import com.openim.common.im.bean.MessageType;
import com.openim.common.im.bean.protbuf.ProtobufConnectMessage;
import com.openim.common.mq.IMessageSender;
import com.openim.common.im.codec.mq.IMQCodec;
import com.openim.common.im.codec.mq.MQBsonCodec;
import com.openim.common.mq.constants.MQConstants;
import io.netty.channel.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by shihuacai on 2015/7/22.
 */
public class LoginHandlerV2 implements INettyMessageHandlerV2 {

    private static final Logger LOG = LoggerFactory.getLogger(LoginHandlerV2.class);

    private static final IMQCodec<ExchangeMessage> mqCodec = new MQBsonCodec();

    private IMessageSender messageSender;

    public LoginHandlerV2() {
        messageSender = ApplicationContextAware.getBean(IMessageSender.class);
    }

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
                messageSender.sendMessage(MQConstants.openimExchange, MQConstants.loginRouteKey, mqCodec.encode(exchangeMessage));
            }catch (Exception e){
                LOG.error(e.toString());
            }
        } else {
            LOG.error("数据类型错误: " + mqCodec.encode(exchangeMessage));
        }
    }
}
