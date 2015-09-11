package com.openim.chatserver.dispatch;

import com.openim.chatserver.dispatch.handle.v2.IMessageHandler;
import com.openim.common.im.annotation.HandleGroupConstants;
import com.openim.common.im.annotation.HandleGroupUtil;
import com.openim.common.im.bean.ExchangeMessage;
import com.openim.common.im.codec.mq.MQBsonCodecUtilV2;
import com.openim.common.mq.MessageQueueDispatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by shihuacai on 2015/7/29.
 */
public class ChatServerMessageQueueDispatchV2 extends MessageQueueDispatch implements InitializingBean{

    private static final Logger LOG = LoggerFactory.getLogger(ChatServerMessageQueueDispatchV2.class);

    //private IMQCodec<ExchangeMessage> mqCodec = new MQBsonCodecUtilV2();

    /*@Autowired
    private ChatHandler chatHandler;
    @Autowired
    private FriendOnLineHandler friendOnLineHandler;
    @Autowired
    private FriendOffLineHandler friendOffLineHandler;*/

    private static Map<Integer, IMessageHandler> msgHandler = new HashMap<Integer, IMessageHandler>(){
        /*{
            put(MessageType.CHAT, new ChatHandler());
            put(MessageType.FRIEND_ONLINE, new FriendOnLineHandler());
            put(MessageType.FRIEND_OFFLINE, new FriendOffLineHandler());
        }*/
    };

    @Override
    public void dispatchMessage(String exchange, String routeKey, byte[] bytes) {
        try {
            ExchangeMessage exchangeMessage = MQBsonCodecUtilV2.decode(bytes);
            if(exchangeMessage != null){
                int type = exchangeMessage.getType();
                IMessageHandler messageHandler = msgHandler.get(type);
                if(messageHandler != null){
                    messageHandler.handle(exchangeMessage);
                }else{
                    LOG.error("未找到指定type的消息处理器, {}", exchangeMessage);
                }
            }
        }catch (Exception e){
            LOG.error(e.toString());
        }
    }

    /**/
    /*@Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        // 执行了两次，其不为同一个对象，why??
        // 如果监听ContextStartedEvent，则不执行该方法
        // event.getApplicationContext().getParent()  springboot中执行，永远为null，tomcat中则有一次不为null
        //if(event.getApplicationContext().getParent() != null){
        *//*if (!CollectionUtils.isEmpty(msgHandler)){
            ApplicationContext context = event.getApplicationContext();
            Map<String, Object> map = context.getBeansWithAnnotation(HandleGroup.class);
            if(!CollectionUtils.isEmpty(map)){
                for(Map.Entry<String, Object> entry : map.entrySet()){
                    HandleGroup handleGroup = entry.getValue().getClass().getAnnotation(HandleGroup.class);

                    String groupName = handleGroup.name();
                    int type = handleGroup.type();
                    if(groupName.equals(HandleGroupConstants.CHAT_SERVER_MQ_HANDLER)){
                        msgHandler.put(type, (IMessageHandler)entry.getValue());
                    }
                }
            }
        }*//*

        //HandleGroupUtil.add(event.getApplicationContext(), HandleGroupConstants.CHAT_SERVER_MQ_HANDLER, msgHandler);

        //}
    }*/

    @Override
    public void afterPropertiesSet() throws Exception {
        HandleGroupUtil.add(applicationContext, HandleGroupConstants.CHAT_SERVER_MQ_HANDLER, msgHandler);

        /*addHandler(chatHandler);
        addHandler(friendOnLineHandler);
        addHandler(friendOffLineHandler);*/
        //afterPropertiesSet执行时，ApplicationContextAware还未初始化，如何解决？？？
        //Map<String, Object> map = ApplicationContextInitialized.getBeansByAnnotation(HandleGroup.class);
        //LOG.debug(map.toString());
    }

    /*private void addHandler(IMessageHandler messageHandler){
        HandleGroup handleGroup = messageHandler.getClass().getAnnotation(HandleGroup.class);

        String groupName = handleGroup.name();
        int type = handleGroup.type();
        if(groupName.equals("chatServerMQ")){
            msgHandler.put(type, messageHandler);
        }
    }*/
}
