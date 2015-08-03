package com.openim.manager.handler.protobuf;

import com.openim.common.im.bean.ProtobufDeviceMsg.DeviceMsg;
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
public class ProtobufLoginHandler implements IMessageHandler<DeviceMsg> {

    private static final Logger LOG = LoggerFactory.getLogger(ProtobufLoginHandler.class);

    @Autowired
    private ILoginCache loginCache;

    @Override
    public void handle(DeviceMsg deviceMsg, HandlerChain handlerChain) {
        try{
            String loginId = deviceMsg.getLoginId();
            String serverQueue = deviceMsg.getServerQueue();
            if(!StringUtils.isEmpty(loginId) && !StringUtils.isEmpty(serverQueue)){
                User user = new User();
                user.setConnectServer(serverQueue);
                user.setLoginId(loginId);
                loginCache.add(loginId, user);
                //通知其好友上线了，待完成
            }else{
                LOG.error("登录信息不全：loginId:{}, serverQueue:{}", loginId, serverQueue);
            }
        }catch (Exception e){
            LOG.error(e.toString());
        }

        /*int type = deviceMsg.getIntValue(DeviceMsgField.type);
        if (type == DeviceMsgType.LOGIN) {

        } else {
            handlerChain.handle(deviceMsg, handlerChain);
        }*/
    }
}
