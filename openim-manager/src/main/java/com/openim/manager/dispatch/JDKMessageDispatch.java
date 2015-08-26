package com.openim.manager.dispatch;

import com.openim.common.im.bean.DeviceMsg;
import com.openim.common.im.bean.MessageType;
import com.openim.common.mq.IMessageDispatch;
import com.openim.manager.dispatch.handler.jdk.LoginHandler;
import com.openim.manager.dispatch.handler.jdk.LogoutHandler;
import com.openim.manager.dispatch.handler.jdk.SendHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;
import java.nio.charset.Charset;

/**
 * Created by shihuacai on 2015/7/29.
 */
@Deprecated
public class JDKMessageDispatch implements IMessageDispatch {

    private static final Logger LOG = LoggerFactory.getLogger(JDKMessageDispatch.class);
    private static final Charset charset = Charset.forName("UTF-8");
    @Autowired
    private LoginHandler loginHandler;
    @Autowired
    private LogoutHandler logoutHandler;
    @Autowired
    private SendHandler sendHandler;

    @Override
    public void dispatchMessage(String exchange, String routeKey, byte[] bytes) {
        if (bytes != null) {
            try {
                ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
                ObjectInputStream input = new ObjectInputStream(bais);
                DeviceMsg msg = (DeviceMsg) input.readObject();

                //String message = new String(bytes, CharsetUtil.utf8);
                //JSONObject jsonObject = JSON.parseObject(message);
                //链式模式会导致频繁的创建对象
                /*HandlerChain chain = new HandlerChain();
                chain.handle(jsonObject, chain);*/
                int type = msg.getType();
                if (type == MessageType.CHAT) {
                    sendHandler.handle(msg, null);
                } else if (type == MessageType.LOGIN) {
                    loginHandler.handle(msg, null);
                } else if (type == MessageType.LOGOUT) {
                    logoutHandler.handle(msg, null);
                } else {
                    LOG.error("无法处理收到的消息：{}", msg);
                }
            } catch (Exception e) {
                LOG.error(e.toString());
            }

        }
    }
}
