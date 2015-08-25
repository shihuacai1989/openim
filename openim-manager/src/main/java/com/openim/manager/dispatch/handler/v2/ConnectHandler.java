package com.openim.manager.dispatch.handler.v2;

import com.openim.common.im.bean.protbuf.ProtobufConnectMessage;
import com.openim.common.im.codec.netty.v2.ExchangeMessage;
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
public class ConnectHandler implements IMessageHandler<ExchangeMessage> {

    private static final Logger LOG = LoggerFactory.getLogger(ConnectHandler.class);

    @Autowired
    private ILoginCache loginCache;

    @Override
    public void handle(ExchangeMessage exchangeMessage) {
        try {
            ProtobufConnectMessage.ConnectMessage connectMessage = (ProtobufConnectMessage.ConnectMessage)exchangeMessage.getMessageLite();
            String loginId = connectMessage.getLoginId();
            String serverQueue = connectMessage.getServerQueue();
            if (!StringUtils.isEmpty(loginId) && !StringUtils.isEmpty(serverQueue)) {
                User user = new User();
                user.setConnectServer(serverQueue);
                user.setLoginId(loginId);
                loginCache.add(loginId, user);
                //通知其好友上线了，待完成
            } else {
                LOG.error("登录信息不全：loginId:{}, serverQueue:{}", loginId, serverQueue);
            }
        } catch (Exception e) {
            LOG.error(e.toString());
        }

        /*int type = deviceMsg.getIntValue(DeviceMsgField.type);
        if (type == DeviceMsgType.LOGIN) {

        } else {
            handlerChain.handle(deviceMsg, handlerChain);
        }*/
    }
}
