package com.openim.common.mq.activemq.sender;

import com.openim.common.mq.IMessageSender;
import com.openim.common.mq.constants.MQConstants;
import org.apache.activemq.command.ActiveMQQueue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jms.core.JmsTemplate;

/**
 * Created by shihuacai on 2015/7/28.
 */
public class ActiveMQMessageSender implements IMessageSender {

    private static final Logger LOG = LoggerFactory.getLogger(ActiveMQMessageSender.class);
    @Autowired
    private JmsTemplate jmsTemplate;

    @Autowired
    @Qualifier("chatServerConsumerQueue")
    ActiveMQQueue chatServerConsumerQueue;

    @Autowired
    @Qualifier("managerConsumerQueue")
    ActiveMQQueue managerConsumerQueue;



    @Override
    public void sendMessage(String exchange, String routeKey, Object message) {
        //此种方式存在问题，消费者无法收到消息
        //Destination destination = new ActiveMQQueue(routeKey);
        //jmsTemplate.convertAndSend(destination, message);
        //此处如何调整更好

        try{
            if(routeKey.equals(MQConstants.chatRouteKey) ||
                    routeKey.equals(MQConstants.loginRouteKey) ||
                    routeKey.equals(MQConstants.logoutRouteKey)){
                //在activemq管理页面中实际生成了相应的topic，但是有奇怪前缀
                //jmsTemplate.convertAndSend(MQConstants.MANAGER_CONSUMER_TOPIC, message);
                //ActiveMQQueue destination = new ActiveMQQueue(MQConstants.MANAGER_CONSUMER_TOPIC);
                jmsTemplate.convertAndSend(managerConsumerQueue, message);
            }else{
                //Destination destination = new ActiveMQQueue(MQConstants.CHATSERVER_CONSUMER_TOPIC);
                jmsTemplate.convertAndSend(chatServerConsumerQueue, message);
            }
        }catch (Exception e){
            LOG.error(e.toString());
        }



    }
}
