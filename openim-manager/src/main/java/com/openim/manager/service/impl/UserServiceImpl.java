package com.openim.manager.service.impl;

import com.openim.common.bean.CommonResult;
import com.openim.manager.bean.User;
import com.openim.manager.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

/**
 * Created by shihc on 2015/8/3.
 */
@Component
public class UserServiceImpl implements IUserService {

    @Autowired
    MongoTemplate mongoTemplate;

    @Override
    public CommonResult addUser(User user) {
        return null;
    }

    @Override
    public CommonResult<Boolean> deleteUser(String loginId) {

        try{
            Criteria criteria = new Criteria("loginId").is(loginId);
            mongoTemplate.remove(new Query(criteria), User.class);
            //return true;
        }catch(Exception e){
            e.printStackTrace();
            //return false;
        }

        return null;
    }

    @Override
    public CommonResult checkUser(String loginId, String pwd) {
        return null;
    }

    @Override
    public CommonResult getUser(String loginId) {
        return null;
    }
}
