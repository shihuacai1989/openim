package com.openim.common.mq.activemq.listener;

import com.openim.common.mq.MessageQueueDispatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.jms.BytesMessage;
import javax.jms.Message;
import javax.jms.MessageListener;

/**
 * Created by shihuacai on 2015/7/28.
 */
public class ActiveMQMessageListener implements MessageListener {

    private static final Logger LOG = LoggerFactory.getLogger(ActiveMQMessageListener.class);

    @Autowired
    MessageQueueDispatch messageDispatch;

    //@JmsListener(destination = ActiveMQConfiguration.HELLO_QUEUE)
    public void onMessage(Message message) {
        try {
            if (message instanceof BytesMessage) {
                BytesMessage textMessage = (BytesMessage) message;
                byte[] bytes = new byte[(int)textMessage.getBodyLength()];
                textMessage.readBytes(bytes);

                messageDispatch.dispatchMessage(null, null, bytes);
            } else {
                LOG.error("activemq消息格式错误，发送时，必须为byte[]类型");
            }
        }catch (Exception e){
            LOG.error(e.toString());
        }
    }
}
