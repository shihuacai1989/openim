package com.openim.manager.dispatch;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.openim.common.im.DeviceMsgField;
import com.openim.common.im.DeviceMsgType;
import com.openim.common.mq.IMessageDispatch;
import com.openim.common.util.CharsetUtil;
import com.openim.manager.handler.LoginHandler;
import com.openim.manager.handler.LogoutHandler;
import com.openim.manager.handler.SendHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.nio.charset.Charset;

/**
 * Created by shihuacai on 2015/7/29.
 */
@Component
public class ManagerMessageDispatch implements IMessageDispatch {

    private static final Logger LOG = LoggerFactory.getLogger(ManagerMessageDispatch.class);

    @Autowired
    private LoginHandler loginHandler;

    @Autowired
    private LogoutHandler logoutHandler;

    @Autowired
    private SendHandler sendHandler;

    private static final Charset charset = Charset.forName("UTF-8");


    @Override
    public void dispatchMessage(String exchange, String routeKey, byte[] bytes) {
        if(bytes != null){
            try {
                String message = new String(bytes, CharsetUtil.utf8);
                JSONObject jsonObject = JSON.parseObject(message);
                //链式模式会导致频繁的创建对象
                /*HandlerChain chain = new HandlerChain();
                chain.handle(jsonObject, chain);*/
                int type = jsonObject.getIntValue(DeviceMsgField.type);
                if (type == DeviceMsgType.SEND) {
                    sendHandler.handle(jsonObject, null);
                }else if(type == DeviceMsgType.LOGIN){
                    loginHandler.handle(jsonObject, null);
                }else if(type == DeviceMsgType.LOGOUT){
                    logoutHandler.handle(jsonObject, null);
                }else{
                    LOG.error("无法处理收到的消息：{}", message);
                }
            }catch (Exception e){
                LOG.error(e.toString());
            }

        }
    }
}
