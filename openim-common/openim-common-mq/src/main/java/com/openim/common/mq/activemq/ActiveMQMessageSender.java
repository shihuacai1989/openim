package com.openim.common.mq.activemq;

import com.openim.common.mq.IMessageSender;
import com.openim.common.mq.constants.MQConstants;
import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;

import javax.jms.Destination;

/**
 * Created by shihuacai on 2015/7/28.
 */
public class ActiveMQMessageSender implements IMessageSender {

    @Autowired
    private JmsTemplate jmsTemplate;

    @Override
    public void sendMessage(String exchange, String routeKey, Object message) {
        //此种方式存在问题，消费者无法收到消息
        //Destination destination = new ActiveMQQueue(routeKey);
        //jmsTemplate.convertAndSend(destination, message);
        //此处如何调整更好

        if(routeKey.equals(MQConstants.chatRouteKey) ||
                routeKey.equals(MQConstants.loginRouteKey) ||
                routeKey.equals(MQConstants.logoutRouteKey)){
            //在activemq管理页面中实际生成了相应的topic，但是有奇怪前缀
            //jmsTemplate.convertAndSend(MQConstants.MANAGER_CONSUMER_TOPIC, message);
            ActiveMQQueue destination = new ActiveMQQueue(MQConstants.MANAGER_CONSUMER_TOPIC);
            jmsTemplate.convertAndSend(destination, message);
        }else{
            Destination destination = new ActiveMQQueue(MQConstants.CHATSERVER_CONSUMER_TOPIC);
            jmsTemplate.convertAndSend(destination, message);
        }

    }
}
