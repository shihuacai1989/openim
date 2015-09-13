package com.openim.manager.dispatch.handler.v2;

import com.openim.common.im.annotation.HandleGroup;
import com.openim.common.im.bean.*;
import com.openim.common.im.bean.protbuf.ProtobufFriendOnLineMessage;
import com.openim.common.im.bean.protbuf.ProtobufLoginMessage;
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
@HandleGroup(name = HandleGroup.MANAGER_MQ_HANDLER_V2, type = MessageType.LOGIN)
public class LoginHandler implements IMessageHandler<ExchangeMessage> {

    private static final Logger LOG = LoggerFactory.getLogger(LoginHandler.class);

    //private static final IMQCodec<ExchangeMessage> mqCodec = new MQBsonCodecUtilV2();

    @Autowired
    private ILoginCache loginCache;

    @Autowired
    private IUserService userService;

    @Autowired
    private IMessageSender messageSender;

    @Override
    public void handle(ExchangeMessage exchangeMessage) {
        try {
            ProtobufLoginMessage.LoginMessage connectMessage = exchangeMessage.getMessageLite();
            String loginId = connectMessage.getLoginId();
            String serverQueue = connectMessage.getServerQueue();
            if (!StringUtils.isEmpty(loginId) && !StringUtils.isEmpty(serverQueue)) {
                User user = new User();
                user.setConnectServer(serverQueue);
                user.setLoginId(loginId);
                loginCache.add(loginId, user);

                userService.updateUserLoginStatus(loginId, LoginStatus.online, serverQueue);

                //通知其好友上线了
                ListResult<User> onlineResult = userService.getOnlineFriends(loginId);
                if(onlineResult.getCode() == ResultCode.success && !CollectionUtils.isEmpty(onlineResult.getData())){
                    List<User> friends = onlineResult.getData();
                    for(User friend : friends){
                        ProtobufFriendOnLineMessage.FriendOnLineMessage friendOnLineMessage = ProtobufFriendOnLineMessage.FriendOnLineMessage
                                .newBuilder()
                                .setLoginId(friend.getLoginId())
                                .setFriendLoginId(loginId)
                                .build();
                        ExchangeMessage onlineMessage = new ExchangeMessage();
                        onlineMessage.setType(MessageType.FRIEND_ONLINE);
                        onlineMessage.setMessageLite(friendOnLineMessage);
                        messageSender.sendMessage(MQConstants.openimExchange, friend.getConnectServer(), MQBsonCodecUtilV2.encode(onlineMessage));
                    }
                }
            } else {
                LOG.error("登录信息不全：loginId:{}, serverQueue:{}", loginId, serverQueue);
            }
        } catch (Exception e) {
            LOG.error(e.toString());
        }

        /*int type = deviceMsg.getIntValue(DeviceMsgField.type);
        if (type == MessageType.LOGIN) {

        } else {
            handlerChain.handle(deviceMsg, handlerChain);
        }*/
    }
}
