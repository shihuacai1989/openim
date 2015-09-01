package com.openim.chatserver.net.handler.v2;

import com.openim.chatserver.SessionManager;
import com.openim.chatserver.net.bean.Session;
import com.openim.common.im.bean.ExchangeMessage;
import com.openim.common.im.bean.MessageType;
import com.openim.common.im.bean.protbuf.ProtobufDisconnectMessage;
import com.openim.common.im.codec.mq.MQBsonCodecUtilV2;
import com.openim.common.mq.IMessageSender;
import com.openim.common.mq.constants.MQConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

/**
 * Created by shihuacai on 2015/7/22.
 */
@Component
public class LogoutHandlerV2 implements IMessageHandlerV2 {

    private static final Logger LOG = LoggerFactory.getLogger(LogoutHandlerV2.class);

    //private static final IMQCodec<ExchangeMessage> mqCodec = new MQBsonCodecUtilV2();

    @Autowired
    private IMessageSender messageSender;

    /*public LogoutHandlerV2() {
        messageSender = ApplicationContextAware.getBean(IMessageSender.class);
    }*/

    @Override
    public void handle(Session session, ExchangeMessage exchangeMessage) {
        if (exchangeMessage.getType() == MessageType.LOGOUT) {
            try {
                String loginId = session.getLoginId();
                SessionManager.remove(loginId);

                if(!StringUtils.isEmpty(loginId)){
                    ProtobufDisconnectMessage.DisconnectMessage disconnectMessage = exchangeMessage.getMessageLite();
                    disconnectMessage = disconnectMessage.toBuilder().setLoginId(loginId).build();
                    exchangeMessage.setMessageLite(disconnectMessage);

                    messageSender.sendMessage(MQConstants.openimExchange, MQConstants.logoutRouteKey, MQBsonCodecUtilV2.encode(exchangeMessage));
                }
            }catch (Exception e){
                LOG.error(e.toString());
            }
        } else {
            LOG.error("数据类型错误: " + MQBsonCodecUtilV2.encode(exchangeMessage));
        }
    }
}
