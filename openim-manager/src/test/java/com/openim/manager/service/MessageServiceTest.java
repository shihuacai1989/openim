package com.openim.manager.service;

import com.openim.manager.ManagerApplication;
import com.openim.manager.bean.Message;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.Date;

/**
 * Created by shihc on 2015/8/3.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = ManagerApplication.class)
@WebAppConfiguration
//@IntegrationTest("server.port:0")
public class MessageServiceTest {
    @Autowired
    private IMessageService messageService;

    @Test
    public void addMessage() {
        Message message = new Message();
        message.setFrom("xml");
        message.setTo("shihc");
        message.setSendTime(new Date());
        message.setMessage("你好");
        messageService.addMessage(message);

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Message msg2 = new Message();
        msg2.setFrom("xml");
        msg2.setTo("shihc");
        msg2.setSendTime(new Date());
        msg2.setMessage("你好");
        messageService.addMessage(msg2);
    }


    @Test
    public void updateLastReadMessage() {
        messageService.updateLastReadMessageTime("shihc", new Date().getTime());
    }

    @Test
    public void listUnReadMessage(){
        messageService.listUnReadMessage("shihc", null);
    }

}
