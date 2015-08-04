package com.openim.manager.service;

import com.openim.common.bean.CommonResult;
import com.openim.common.bean.ListResult;
import com.openim.manager.bean.Message;

/**
 * Created by shihc on 2015/8/4.
 */
public interface IMessageService {
    CommonResult<Integer> addMessage(Message message);
    ListResult<Message> listUnReadMessage(String loginId);
}
