package com.openim.manager.service;

import com.openim.common.im.bean.CommonResult;
import com.openim.common.im.bean.ListResult;
import com.openim.manager.bean.Message;

/**
 * Created by shihc on 2015/8/4.
 */
public interface IMessageService {
    CommonResult<Integer> addMessage(Message message);

    ListResult<Message> listUnReadMessage(String loginId, Long lastReadMsgTime);

    CommonResult<Long> updateLastReadMessageTime(String loginId, Long lastReadMsgTime);
}
