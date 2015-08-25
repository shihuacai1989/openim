package com.openim.manager.dispatch.handler.v1;

import com.openim.common.im.bean.LoginStatus;
import com.openim.common.im.bean.ProtobufDeviceMsg.DeviceMsg;
import com.openim.manager.bean.User;
import com.openim.manager.cache.login.ILoginCache;
import com.openim.manager.dispatch.handler.HandlerChain;
import com.openim.manager.dispatch.handler.IMessageHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

/**
 * Created by shihc on 2015/7/30.
 */
//@Component
@Deprecated
public class LogoutHandler implements IMessageHandler<DeviceMsg> {

    private static final Logger LOG = LoggerFactory.getLogger(LogoutHandler.class);

    @Autowired
    private ILoginCache loginCache;

    @Override
    public void handle(DeviceMsg deviceMsg, HandlerChain handlerChain) {
        try {
            String loginId = deviceMsg.getLoginId();
            //String serverQueue = deviceMsg.getString(DeviceMsgField.serverQueue);
            if (!StringUtils.isEmpty(loginId)) {
                User user = loginCache.get(loginId);
                if (user != null) {
                    user.setLoginStatus(LoginStatus.offline);
                    loginCache.add(loginId, user);
                }
                //通知其好友下线了，待完成
            } else {
                LOG.error("下线信息不全：loginId:{}", loginId);
            }
        } catch (Exception e) {
            LOG.error(e.toString());
        }

        /*int type = deviceMsg.getIntValue(DeviceMsgField.type);
        if (type == DeviceMsgType.LOGOUT) {

        } else {
            handlerChain.handle(deviceMsg, handlerChain);
        }*/

    }
}
