package com.openim.chatserver.net.handler.v2;

import com.openim.chatserver.SessionManager;
import com.openim.chatserver.configuration.BeanConfiguration;
import com.openim.chatserver.net.bean.Session;
import com.openim.common.im.annotation.HandleGroup;
import com.openim.common.im.annotation.HandleGroupConstants;
import com.openim.common.im.bean.ExchangeMessage;
import com.openim.common.im.bean.MessageType;
import com.openim.common.im.bean.protbuf.ProtobufLoginMessage;
import com.openim.common.im.codec.mq.MQBsonCodecUtilV2;
import com.openim.common.mq.IMessageSender;
import com.openim.common.mq.constants.MQConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by shihc on 2015/9/1.
 */
@Component
@HandleGroup(name = HandleGroupConstants.CHAT_SERVER_NiO_HANDLER_V2, type = MessageType.LOGIN)
public class LoginHandlerV2 implements IMessageHandlerV2 {
    private static final Logger LOG = LoggerFactory.getLogger(LoginHandlerV2.class);

    @Autowired
    private IMessageSender messageSender;

    @Override
    public void handle(Session session, ExchangeMessage exchangeMessage) {
        if (exchangeMessage.getType() == MessageType.LOGIN) {
            try {
                ProtobufLoginMessage.LoginMessage connectMessage = exchangeMessage.getMessageLite();
                //后期完成登录验证
                String pwd = connectMessage.getPassword();
                String loginId = connectMessage.getLoginId();
                SessionManager.add(loginId, session);
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
