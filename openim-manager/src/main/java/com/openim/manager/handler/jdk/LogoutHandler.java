package com.openim.manager.handler.jdk;

import com.openim.common.im.bean.DeviceMsg;
import com.openim.common.im.bean.LoginStatus;
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
public class LogoutHandler implements IMessageHandler<DeviceMsg> {

    private static final Logger LOG = LoggerFactory.getLogger(LogoutHandler.class);

    @Autowired
    private ILoginCache loginCache;

    @Override
    public void handle(DeviceMsg jsonObject, HandlerChain handlerChain) {
        try {
            String loginId = jsonObject.getLoginId();
            //String serverQueue = jsonObject.getString(DeviceMsgField.serverQueue);
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
    }
}
