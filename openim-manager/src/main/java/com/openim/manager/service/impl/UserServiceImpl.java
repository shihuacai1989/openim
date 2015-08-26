package com.openim.manager.service.impl;

import com.mongodb.WriteResult;
import com.openim.common.im.bean.CommonResult;
import com.openim.common.im.bean.ListResult;
import com.openim.common.im.bean.LoginStatus;
import com.openim.common.im.bean.ResultCode;
import com.openim.common.util.UUIDUtil;
import com.openim.manager.bean.Friend;
import com.openim.manager.bean.Group;
import com.openim.manager.bean.User;
import com.openim.manager.service.IUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by shihc on 2015/8/3.
 */
@Service
public class UserServiceImpl implements IUserService {

    private static final Logger LOG = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public CommonResult addUser(User user) {
        int code = ResultCode.success;
        String error = null;
        try {
            if (!StringUtils.isEmpty(user.getLoginId()) && !StringUtils.isEmpty(user.getPassword())) {
                mongoTemplate.save(user);
            } else {
                code = ResultCode.parameter_null;
                error = "用户名和密码不能为空";
            }
        } catch (Exception e) {
            LOG.error(e.toString());
            code = ResultCode.error;
            error = e.toString();
        }
        return new CommonResult(code, null, error);
    }

    @Override
    public CommonResult<Integer> deleteUser(String loginId) {
        int code = ResultCode.success;
        int deleteCount = 0;
        String error = null;
        try {
            Criteria criteria = new Criteria("loginId").is(loginId);
            WriteResult writeResult = mongoTemplate.remove(new Query(criteria), User.class);
            deleteCount = writeResult.getN();
        } catch (Exception e) {
            code = ResultCode.error;
            error = e.toString();
            LOG.error(e.toString());
        }

        return new CommonResult<Integer>(code, deleteCount, error);
    }

    @Override
    public CommonResult<Boolean> checkLogin(String loginId, String pwd) {
        int code = ResultCode.success;
        String error = null;
        boolean data = false;
        try {
            if (StringUtils.isEmpty(loginId) || StringUtils.isEmpty(pwd)) {
                code = ResultCode.parameter_null;
                error = "用户名和密码不可为空";
            } else {
                Query query = new Query();
                query.addCriteria(Criteria.where("loginId").is(loginId));
                query.addCriteria(Criteria.where("password").is(pwd));
                query.fields().include("_id");
                User user = mongoTemplate.findOne(query, User.class);
                if (user != null) {
                    data = true;
                } else {
                    code = ResultCode.parameter_error;
                    error = "用户名或密码错误";
                }
            }

        } catch (Exception e) {
            code = ResultCode.error;
            LOG.error(e.toString());
        }
        return new CommonResult<Boolean>(code, data, error);
    }

    @Override
    public CommonResult<User> getUser(String loginId) {
        int code = ResultCode.success;
        User data = null;
        String error = null;
        try {
            if (StringUtils.isEmpty(loginId)) {
                code = ResultCode.parameter_null;
            } else {
                Criteria criteria = new Criteria("loginId").is(loginId);
                Query query = new Query(criteria);
                query.fields().exclude("groups");
                query.fields().exclude("friends");

                data = mongoTemplate.findOne(query, User.class);
            }
        } catch (Exception e) {
            code = ResultCode.error;
            LOG.error(e.toString());
        }
        return new CommonResult<User>(code, data, error);
    }

    @Override
    public CommonResult<Boolean> userExist(String loginId) {
        int code = ResultCode.success;
        String error = null;
        boolean data = false;
        try {
            if (StringUtils.isEmpty(loginId)) {
                code = ResultCode.parameter_null;
            } else {
                Query query = new Query();
                query.addCriteria(Criteria.where("loginId").is(loginId));

                query.fields().include("_id");
                User user = mongoTemplate.findOne(query, User.class);
                if (user != null) {
                    data = true;
                } else {
                    code = ResultCode.parameter_error;
                    error = "用户不存在";
                }
            }
        } catch (Exception e) {
            code = ResultCode.error;
            LOG.error(e.toString());
        }
        return new CommonResult<Boolean>(code, data, error);
    }

    @Override
    public CommonResult<Integer> addGroup(String loginId, String groupName) {
        int code = ResultCode.success;
        int data = 0;
        String error = null;
        if (StringUtils.isEmpty(groupName) || StringUtils.isEmpty(loginId)) {
            code = ResultCode.parameter_null;
        } else {
            try {
                Group group = new Group();
                group.setName(groupName);
                group.setId(UUIDUtil.genericUUID());
                group.setCreateTime(new Date());

                Criteria criteria = new Criteria("loginId").is(loginId);
                Query query = new Query(criteria);
                Update update = new Update();
                update.addToSet("groups", group);

                WriteResult writeResult = mongoTemplate.updateFirst(query, update, User.class);
                data = writeResult.getN();
            } catch (Exception e) {
                error = e.toString();
                code = ResultCode.error;
                LOG.error(e.toString());
            }
        }

        return new CommonResult<Integer>(code, data, error);
    }

    @Override
    public ListResult<Group> listGroups(String loginId) {
        int code = ResultCode.success;
        List<Group> data = null;
        String error = null;
        try {
            if (StringUtils.isEmpty(loginId)) {
                code = ResultCode.parameter_null;
            } else {
                Criteria criteria = new Criteria("loginId").is(loginId);
                Query query = new Query(criteria);
                query.fields().include("groups");
                User user = mongoTemplate.findOne(query, User.class);
                if (user != null) {
                    data = user.getGroups();
                }
            }
        } catch (Exception e) {
            code = ResultCode.error;
            LOG.error(e.toString());
        }
        return new ListResult<Group>(code, data, error);
    }

    @Override
    public CommonResult<Integer> addFriend(String loginId, String friendLoginId, String groupId) {
        int code = ResultCode.success;
        int data = 0;
        String error = null;
        if (StringUtils.isEmpty(loginId) || StringUtils.isEmpty(friendLoginId) || StringUtils.isEmpty(groupId)) {
            code = ResultCode.parameter_null;
        } else {
            try {
                CommonResult<Boolean> existResult = userExist(friendLoginId);
                if(existResult.getCode() == ResultCode.success && existResult.getData() == true){
                    Friend friend = new Friend();
                    friend.setFriendLoginId(friendLoginId);
                    friend.setGroupId(groupId);

                    Criteria criteria = new Criteria("loginId").is(loginId);
                    Query query = new Query(criteria);
                    Update update = new Update();
                    update.addToSet("friends", friend);

                    WriteResult writeResult = mongoTemplate.updateFirst(query, update, User.class);
                    data = writeResult.getN();
                }else{
                    code = existResult.getCode();
                    error = existResult.getError();
                }
            } catch (Exception e) {
                error = e.toString();
                code = ResultCode.error;
                LOG.error(e.toString());
            }
        }

        return new CommonResult<Integer>(code, data, error);
    }

    @Override
    public ListResult<Friend> listFriends(String loginId) {
        int code = ResultCode.success;
        List<Friend> data = null;
        String error = null;
        try {
            if (StringUtils.isEmpty(loginId)) {
                code = ResultCode.parameter_null;
            } else {
                Criteria criteria = new Criteria("loginId").is(loginId);
                Query query = new Query(criteria);
                query.fields().include("friends");
                User user = mongoTemplate.findOne(query, User.class);
                if (user != null) {
                    data = user.getFriends();
                }
            }
        } catch (Exception e) {
            code = ResultCode.error;
            LOG.error(e.toString());
        }
        return new ListResult<Friend>(code, data, error);
    }

    @Override
    public ListResult<String> listFriendsLoginId(String loginId) {
        int code = ResultCode.success;
        List<String> data = null;
        String error = null;
        try {
            if (StringUtils.isEmpty(loginId)) {
                code = ResultCode.parameter_null;
            } else {
                Criteria criteria = new Criteria("loginId").is(loginId);
                Query query = new Query(criteria);
                query.fields().include("friends.friendLoginId");
                User user = mongoTemplate.findOne(query, User.class);
                if (user != null) {
                    //data = user.getFriends();
                    List<Friend> friends = user.getFriends();
                    if (!CollectionUtils.isEmpty(friends)) {
                        data = new ArrayList<String>(friends.size());
                        for (Friend friend : friends) {
                            data.add(friend.getFriendLoginId());
                        }
                    }
                }
            }
        } catch (Exception e) {
            code = ResultCode.error;
            LOG.error(e.toString());
        }
        return new ListResult<String>(code, data, error);
    }

    @Override

    public ListResult<User> getOnlineFriends(String loginId) {
        int code = ResultCode.success;
        List<User> users = null;
        try {
            ListResult<String> result = listFriendsLoginId(loginId);
            if(result.getCode() == ResultCode.success && !CollectionUtils.isEmpty(result.getData())){
                List<String> allFriends = result.getData();
                Criteria criteria = new Criteria("loginId").in(allFriends).and("loginStatus").is(LoginStatus.online);
                Query query = new Query(criteria);
                query.fields().include("loginId");
                query.fields().include("connectServer");
                users = mongoTemplate.find(query, User.class);
            }else{
                code = result.getCode();
            }
        }catch (Exception e){
            code = ResultCode.error;
            LOG.error(e.toString());
        }

        return new ListResult<User>(code, users);
    }
}
