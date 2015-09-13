package com.openim.manager.dispatch;

import com.openim.common.im.annotation.HandleGroupConstants;
import com.openim.common.im.bean.ExchangeMessage;
import com.openim.common.im.codec.mq.MQBsonCodecUtilV2;
import com.openim.common.mq.MessageQueueDispatch;
import com.openim.common.util.CharsetUtil;
import com.openim.manager.dispatch.handler.v2.IMessageHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by shihuacai on 2015/7/29.
 */
public class ManagerMessageQueueDispatchV2 extends MessageQueueDispatch implements InitializingBean {

    private static final Logger LOG = LoggerFactory.getLogger(ManagerMessageQueueDispatchV2.class);


    private static Map<Integer, IMessageHandler> msgHandler = new HashMap<Integer, IMessageHandler>();

    //private static final Charset charset = Charset.forName("UTF-8");
    //private IMQCodec<ExchangeMessage> mqCodec = new MQBsonCodecUtilV2();

    /*@Autowired
    private ConnectHandler loginHandler;
    @Autowired
    private DisconnectHandler logoutHandler;
    @Autowired
    private ChatHandler sendHandler;*/

    @Override
    public void dispatchMessage(String exchange, String routeKey, byte[] c) {
        try {
            ExchangeMessage exchangeMessage = MQBsonCodecUtilV2.decode(c);
            if(exchangeMessage != null){
                int type = exchangeMessage.getType();
                IMessageHandler messageHandler = msgHandler.get(type);
                if(messageHandler != null) {
                    messageHandler.handle(exchangeMessage);
                } else {
                    LOG.error("无法处理收到的消息：{}", new String(c, CharsetUtil.utf8));
                }
            }
        } catch (Exception e) {
            LOG.error(e.toString());
        }
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        registerHandler(HandleGroupConstants.MANAGER_MQ_HANDLER_V2, msgHandler);
    }
}
