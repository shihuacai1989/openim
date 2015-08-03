package com.openim.manager.handler.protobuf;

import com.openim.common.im.bean.LoginStatus;
import com.openim.common.im.bean.ProtobufDeviceMsg.DeviceMsg;
import com.openim.common.mq.IMessageSender;
import com.openim.common.mq.constants.MQConstants;
import com.openim.manager.bean.User;
import com.openim.manager.cache.login.ILoginCache;
import com.openim.manager.handler.HandlerChain;
import com.openim.manager.handler.IMessageHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

/**
 * Created by shihc on 2015/7/30.
 */
@Component
public class ProtobufSendHandler implements IMessageHandler<DeviceMsg> {

    private static final Logger LOG = LoggerFactory.getLogger(ProtobufSendHandler.class);

    @Autowired
    private IMessageSender messageSender;

    @Autowired
    private ILoginCache loginCache;

    @Override
    public void handle(DeviceMsg deviceMsg, HandlerChain handlerChain) {
        String to = deviceMsg.getTo();
        if(!StringUtils.isEmpty(to)){
            try{
                User toUser = loginCache.get(to);
                if(toUser != null){
                    int loginStatus = toUser.getLoginStatus();
                    if(loginStatus != LoginStatus.offline){
                        String connectServer = toUser.getConnectServer();

                        messageSender.sendMessage(MQConstants.openimExchange, connectServer, deviceMsg.toByteArray());
                    }
                }
            }catch (Exception e){
                LOG.error(e.toString());
            }

        }else{
            LOG.error("发送信息不全：to:{}", to);
        }
        /*int type = deviceMsg.getIntValue(DeviceMsgField.type);
        if (type == DeviceMsgType.SEND) {

        } else {
            handlerChain.handle(deviceMsg, handlerChain);
        }*/
    }
}