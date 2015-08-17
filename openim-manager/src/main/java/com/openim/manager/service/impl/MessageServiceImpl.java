package com.openim.manager.service.impl;

import com.openim.common.bean.CommonResult;
import com.openim.common.bean.ListResult;
import com.openim.common.bean.ResultCode;
import com.openim.manager.bean.Message;
import com.openim.manager.bean.User;
import com.openim.manager.service.IMessageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * Created by shihc on 2015/8/4.
 */
@Component
public class MessageServiceImpl implements IMessageService {

    private static final Logger LOG = LoggerFactory.getLogger(MessageServiceImpl.class);

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public CommonResult<Integer> addMessage(Message message) {
        int code = ResultCode.success;
        String error = null;
        try {
            mongoTemplate.save(message);
        } catch (Exception e) {
            LOG.error(e.toString());
            code = ResultCode.error;
            error = e.toString();
        }
        return new CommonResult(code, null, error);
    }

    @Override
    public ListResult<Message> listUnReadMessage(String loginId) {
        Query userQuery = new Query();
        userQuery.addCriteria(Criteria.where("loginId").is(loginId));
        userQuery.fields().include("lastReadMsgTime");
        User user = mongoTemplate.findOne(userQuery, User.class);
        if(user != null){
            Date lastReadMsgTime = user.getLastReadMsgTime();
        }



        return null;
    }
}
