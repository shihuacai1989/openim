package com.openim.chatserver.net.server.netty.v2.hander;

import com.openim.chatserver.ChannelUtil;
import com.openim.chatserver.listener.ApplicationContextAware;
import com.openim.chatserver.net.server.netty.v2.INettyMessageHandlerV2;
import com.openim.common.im.bean.ExchangeMessage;
import com.openim.common.im.bean.MessageType;
import com.openim.common.im.bean.protbuf.ProtobufDisconnectMessage;
import com.openim.common.im.codec.mq.IMQCodec;
import com.openim.common.im.codec.mq.MQBsonCodec;
import com.openim.common.mq.IMessageSender;
import com.openim.common.mq.constants.MQConstants;
import io.netty.channel.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

/**
 * Created by shihuacai on 2015/7/22.
 */
@Component
public class LogoutHandlerV2 implements INettyMessageHandlerV2 {

    private static final Logger LOG = LoggerFactory.getLogger(LogoutHandlerV2.class);

    private static final IMQCodec<ExchangeMessage> mqCodec = new MQBsonCodec();

    @Autowired
    private IMessageSender messageSender;

    /*public LogoutHandlerV2() {
        messageSender = ApplicationContextAware.getBean(IMessageSender.class);
    }*/

    @Override
    public void handle(Channel channel, ExchangeMessage exchangeMessage) {
        if (exchangeMessage.getType() == MessageType.LOGOUT) {
            try {
                String loginId = ChannelUtil.remove(channel);
                if(!StringUtils.isEmpty(loginId)){
                    ProtobufDisconnectMessage.DisconnectMessage disconnectMessage = (ProtobufDisconnectMessage.DisconnectMessage)exchangeMessage.getMessageLite();
                    disconnectMessage = disconnectMessage.toBuilder().setLoginId(loginId).build();
                    exchangeMessage.setMessageLite(disconnectMessage);

                    messageSender.sendMessage(MQConstants.openimExchange, MQConstants.logoutRouteKey, mqCodec.encode(exchangeMessage));
                }
            }catch (Exception e){
                LOG.error(e.toString());
            }
        } else {
            LOG.error("数据类型错误: " + mqCodec.encode(exchangeMessage));
        }
    }
}
