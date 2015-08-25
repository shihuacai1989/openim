package com.openim.chatserver.net.handler.netty.v2;

import com.openim.chatserver.ChannelUtil;
import com.openim.chatserver.listener.ApplicationContextAware;
import com.openim.chatserver.net.handler.IMessageHandler;
import com.openim.common.im.bean.DeviceMsgType;
import com.openim.common.im.bean.protbuf.ProtobufDisconnectMessage;
import com.openim.common.im.codec.netty.v2.ExchangeMessage;
import com.openim.common.mq.IMessageSender;
import com.openim.common.mq.constants.MQConstants;
import io.netty.channel.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

/**
 * Created by shihuacai on 2015/7/22.
 */
//@Component
public class LogoutHandler implements IMessageHandler<ExchangeMessage, Channel> {

    private static final Logger LOG = LoggerFactory.getLogger(LogoutHandler.class);

    private IMessageSender messageSender;

    public LogoutHandler() {
        messageSender = ApplicationContextAware.getBean(IMessageSender.class);
    }

    @Override
    public void handle(ExchangeMessage exchangeMessage, Channel channel) {
        if (exchangeMessage.getType() == DeviceMsgType.LOGOUT) {
            try {
                String loginId = ChannelUtil.remove(channel);
                if(!StringUtils.isEmpty(loginId)){
                    ProtobufDisconnectMessage.DisconnectMessage disconnectMessage = (ProtobufDisconnectMessage.DisconnectMessage)exchangeMessage.getMessageLite();
                    disconnectMessage = disconnectMessage.toBuilder().setLoginId(loginId).build();
                    exchangeMessage.setMessageLite(disconnectMessage);

                    messageSender.sendMessage(MQConstants.openimExchange, MQConstants.logoutRouteKey, exchangeMessage.toBsonString());
                }
            }catch (Exception e){
                LOG.error(e.toString());
            }
        } else {
            LOG.error("数据类型错误: " + exchangeMessage.toBsonString());
        }
    }
}
