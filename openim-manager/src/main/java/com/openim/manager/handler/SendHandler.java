package com.openim.manager.handler;

import com.alibaba.fastjson.JSONObject;
import com.openim.common.im.DeviceMsgField;
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
    private ILoginCache loginCache;

    @Override
    public void handle(JSONObject jsonObject, HandlerChain handlerChain) {
        String to = jsonObject.getString(DeviceMsgField.to);
        if(!StringUtils.isEmpty(to)){
            loginCache.get(to);
        }else{
            //LOG.error("登录信息不全：loginId:{}, serverQueue:{}", loginId,);
        }
        /*int type = jsonObject.getIntValue(DeviceMsgField.type);
        if (type == DeviceMsgType.SEND) {

        } else {
            handlerChain.handle(jsonObject, handlerChain);
        }*/
    }
}