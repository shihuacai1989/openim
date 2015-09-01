package com.openim.manager.dispatch;

import com.openim.common.im.bean.ExchangeMessage;
import com.openim.common.im.bean.MessageType;
import com.openim.common.im.codec.mq.MQBsonCodecUtilV2;
import com.openim.common.mq.IMessageQueueDispatch;
import com.openim.common.util.CharsetUtil;
import com.openim.manager.dispatch.handler.v2.ChatHandler;
import com.openim.manager.dispatch.handler.v2.ConnectHandler;
import com.openim.manager.dispatch.handler.v2.DisconnectHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by shihuacai on 2015/7/29.
 */
public class MessageDispatchV2 implements IMessageQueueDispatch {

    private static final Logger LOG = LoggerFactory.getLogger(MessageDispatchV2.class);
    //private static final Charset charset = Charset.forName("UTF-8");
    //private IMQCodec<ExchangeMessage> mqCodec = new MQBsonCodecUtilV2();

    @Autowired
    private ConnectHandler loginHandler;
    @Autowired
    private DisconnectHandler logoutHandler;
    @Autowired
    private ChatHandler sendHandler;

    @Override
    public void dispatchMessage(String exchange, String routeKey, byte[] c) {
        try {
            ExchangeMessage exchangeMessage = MQBsonCodecUtilV2.decode(c);
            if(exchangeMessage != null){
                int type = exchangeMessage.getType();
                if (type == MessageType.CHAT) {
                    sendHandler.handle(exchangeMessage);
                } else if (type == MessageType.LOGIN) {
                    loginHandler.handle(exchangeMessage);
                } else if (type == MessageType.LOGOUT) {
                    logoutHandler.handle(exchangeMessage);
                } else {
                    LOG.error("无法处理收到的消息：{}", new String(c, CharsetUtil.utf8));
                }
            }
        } catch (Exception e) {
            LOG.error(e.toString());
        }
    }
}
