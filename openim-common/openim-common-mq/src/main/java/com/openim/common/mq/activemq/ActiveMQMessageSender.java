package com.openim.common.mq.activemq;

import com.openim.common.mq.IMessageSender;
import com.openim.common.mq.constants.MQConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;

/**
 * Created by shihuacai on 2015/7/28.
 */
public class ActiveMQMessageSender implements IMessageSender {

    @Autowired
    private JmsTemplate jmsTemplate;

    @Override
    public void sendMessage(String exchange, String routeKey, Object message) {
        //此处如何调整更好
        if(routeKey.equals(MQConstants.chatRouteKey) ||
                routeKey.equals(MQConstants.loginRouteKey) ||
                routeKey.equals(MQConstants.logoutRouteKey)){
            jmsTemplate.convertAndSend(MQConstants.MANAGER_CONSUMER_TOPIC, message);
        }else{
            jmsTemplate.convertAndSend(MQConstants.CHATSERVER_CONSUMER_TOPIC, message);
        }

    }
}
