package com.openim.manager.service;

import com.openim.common.bean.CommonResult;
import com.openim.manager.bean.User;

/**
 * Created by shihc on 2015/8/3.
 */
public interface IUserService {
    CommonResult addUser(User user);
    CommonResult<Integer> deleteUser(String loginId);
    CommonResult<Boolean> userExist(String loginId, String pwd);
    CommonResult<User> getUser(String loginId);
    CommonResult<Integer> addGroup(String loginId, String groupName);
}
