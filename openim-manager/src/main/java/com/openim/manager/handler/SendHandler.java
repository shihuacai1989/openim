package com.openim.manager.handler;

import com.alibaba.fastjson.JSONObject;
import com.openim.common.im.DeviceMsgField;
import com.openim.common.im.LoginStatus;
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
public class SendHandler implements IMessageHandler<JSONObject> {

    private static final Logger LOG = LoggerFactory.getLogger(SendHandler.class);

    @Autowired
    private IMessageSender messageSender;

    @Autowired
    private ILoginCache loginCache;

    @Override
    public void handle(JSONObject jsonObject, HandlerChain handlerChain) {
        String to = jsonObject.getString(DeviceMsgField.to);
        if(!StringUtils.isEmpty(to)){
            try{
                User user = loginCache.get(to);
                if(user != null){
                    int loginStatus = user.getLoginStatus();
                    if(loginStatus != LoginStatus.offline){
                        String connectServer = user.getConnectServer();

                        messageSender.sendMessage(MQConstants.openimExchange, connectServer, jsonObject);
                    }
                }
            }catch (Exception e){
                LOG.error(e.toString());
            }

        }else{
            LOG.error("发送信息不全：to:{}", to);
        }
        /*int type = jsonObject.getIntValue(DeviceMsgField.type);
        if (type == DeviceMsgType.SEND) {

        } else {
            handlerChain.handle(jsonObject, handlerChain);
        }*/
    }
}