package com.openim.manager.service.impl;

import com.openim.common.im.bean.CommonResult;
import com.openim.common.im.bean.ListResult;
import com.openim.common.im.bean.ResultCode;
import com.openim.manager.bean.Message;
import com.openim.manager.bean.User;
import com.openim.manager.service.IMessageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

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
    public ListResult<Message> listUnReadMessage(String loginId, Long lastReadMsgTime) {
        int code = ResultCode.success;
        List<Message> messageList = null;
        try {
            CommonResult<Long> result = updateLastReadMessageTime(loginId, lastReadMsgTime);
            if(result.getCode() == ResultCode.success){
                Long lastReadMessageTime = result.getData();
                Criteria criteria = Criteria.where("to").is(loginId).and("sendTime").gt(new Date(lastReadMessageTime));;
                Query msgQuery = new Query(criteria);
                messageList = mongoTemplate.find(msgQuery, Message.class);
            }
        }catch (Exception e){
            code = ResultCode.error;
            LOG.error(e.toString());
        }


        return new ListResult<Message>(code, messageList);
    }

    @Override
    public CommonResult<Long> updateLastReadMessageTime(String loginId, Long lastReadMsgTime){
        int  code = ResultCode.success;
        String error = null;
        Long lastTime = 0L;
        try {
            Query userQuery = new Query();
            userQuery.addCriteria(Criteria.where("loginId").is(loginId));
            userQuery.fields().include("lastReadMsgTime");
            User user = mongoTemplate.findOne(userQuery, User.class);
            if(user != null){
                lastTime = user.getLastReadMsgTime().getTime();
                if(lastReadMsgTime != null && lastReadMsgTime > lastTime){
                    lastTime = lastReadMsgTime;

                    Query query = new Query(new Criteria("loginId").is(loginId));
                    Update update = new Update();
                    update.set("lastReadMsgTime", new Date(lastTime));
                    mongoTemplate.updateFirst(query, update, User.class);
                }
            }else{
                code = ResultCode.parameter_error;
                error = "该用户不存在";
            }
        }catch (Exception e){
            code = ResultCode.error;
            LOG.error(e.toString());
        }
        return new CommonResult<Long>(code, lastTime, error);
    }
}
