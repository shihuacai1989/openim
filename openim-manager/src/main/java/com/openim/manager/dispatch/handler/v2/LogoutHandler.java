package com.openim.manager.dispatch.handler.v2;

import com.openim.common.im.annotation.HandleGroup;
import com.openim.common.im.annotation.HandleGroupConstants;
import com.openim.common.im.bean.*;
import com.openim.common.im.bean.protbuf.ProtobufFriendOffLineMessage;
import com.openim.common.im.bean.protbuf.ProtobufLogoutMessage;
import com.openim.common.im.codec.mq.MQBsonCodecUtilV2;
import com.openim.common.mq.IMessageSender;
import com.openim.common.mq.constants.MQConstants;
import com.openim.manager.bean.User;
import com.openim.manager.cache.login.ILoginCache;
import com.openim.manager.service.IUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * Created by shihc on 2015/7/30.
 */
@Component
@HandleGroup(name = HandleGroupConstants.MANAGER_MQ_HANDLER_V2, type = MessageType.LOGOUT)
public class LogoutHandler implements IMessageHandler<ExchangeMessage> {

    private static final Logger LOG = LoggerFactory.getLogger(LogoutHandler.class);

    @Autowired
    private ILoginCache loginCache;

    @Autowired
    private IUserService userService;

    @Autowired
    private IMessageSender messageSender;

    @Override
    public void handle(ExchangeMessage exchangeMessage) {
        try {
            ProtobufLogoutMessage.LogoutMessage disconnectMessage = exchangeMessage.getMessageLite();
            String loginId = disconnectMessage.getLoginId();
            if (!StringUtils.isEmpty(loginId)) {
                User user = loginCache.get(loginId);
                if (user != null) {
                    user.setLoginStatus(LoginStatus.offline);
                    loginCache.add(loginId, user);
                }
                userService.updateUserLoginStatus(loginId, LoginStatus.offline, null);

                //通知其好友下线了
                ListResult<User> onlineResult = userService.getOnlineFriends(loginId);
                if(onlineResult.getCode() == ResultCode.success && !CollectionUtils.isEmpty(onlineResult.getData())){
                    List<User> friends = onlineResult.getData();
                    for(User friend : friends){
                        ProtobufFriendOffLineMessage.FriendOffLineMessage friendOffLineMessage = ProtobufFriendOffLineMessage.FriendOffLineMessage
                                                        .newBuilder()
                                                        .setLoginId(friend.getLoginId())
                                                        .setFriendLoginId(loginId)
                                                        .build();
                        ExchangeMessage offlineMessage = new ExchangeMessage();
                        offlineMessage.setType(MessageType.FRIEND_OFFLINE);
                        offlineMessage.setMessageLite(friendOffLineMessage);
                        messageSender.sendMessage(MQConstants.openimExchange, friend.getConnectServer(), MQBsonCodecUtilV2.encode(offlineMessage));
                    }
                }
            } else {
                LOG.error("下线信息不全：loginId:{}", loginId);
            }
        } catch (Exception e) {
            LOG.error(e.toString());
        }

    }
}
