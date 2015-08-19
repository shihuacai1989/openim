package com.openim.manager.controller;

import com.openim.common.bean.ListResult;
import com.openim.manager.bean.Message;
import com.openim.manager.service.IMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by shihuacai on 2015/7/20.
 * 用户控制器
 */
@RequestMapping("/message")
@Controller
public class MessageController {

    @Autowired
    private IMessageService messageService;

    @RequestMapping("/listUnRead")
    @ResponseBody
    public ListResult<Message> listUnReadMessage(String loginId, Long lastReadMsgTime){
        return messageService.listUnReadMessage(loginId, lastReadMsgTime);
    }
}
