package com.openim.manager.dispatch.handler.v2;

import com.openim.common.im.bean.ExchangeMessage;
import com.openim.common.im.bean.LoginStatus;
import com.openim.common.im.bean.protbuf.ProtobufChatMessage;
import com.openim.common.im.codec.mq.MQBsonCodecUtilV2;
import com.openim.common.mq.IMessageSender;
import com.openim.common.mq.constants.MQConstants;
import com.openim.manager.bean.User;
import com.openim.manager.cache.login.ILoginCache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

/**
 * Created by shihc on 2015/7/30.
 */
@Component
public class ChatHandler implements IMessageHandler<ExchangeMessage> {

    private static final Logger LOG = LoggerFactory.getLogger(ChatHandler.class);

    @Autowired
    private IMessageSender messageSender;

    @Autowired
    private ILoginCache loginCache;

    @Override
    public void handle(ExchangeMessage exchangeMessage) {
        try {
            ProtobufChatMessage.ChatMessage chatMessage = exchangeMessage.getMessageLite();
            String to = chatMessage.getTo();
            if (!StringUtils.isEmpty(to)) {
                User toUser = loginCache.get(to);
                if (toUser != null) {
                    int loginStatus = toUser.getLoginStatus();
                    if (loginStatus != LoginStatus.offline) {
                        String connectServer = toUser.getConnectServer();
                        messageSender.sendMessage(MQConstants.openimExchange, connectServer, MQBsonCodecUtilV2.encode(exchangeMessage));
                    }
                }
            } else {
                LOG.error("发送信息不全：to:{}", to);
            }
        } catch (Exception e) {
            LOG.error(e.toString());
        }
    }
}