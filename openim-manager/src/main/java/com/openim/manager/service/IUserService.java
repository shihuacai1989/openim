package com.openim.manager.service;

import com.openim.common.bean.CommonResult;
import com.openim.manager.bean.User;

/**
 * Created by shihc on 2015/8/3.
 */
public interface IUserService {
    CommonResult addUser(User user);
    CommonResult deleteUser(String loginId);
    CommonResult checkUser(String loginId, String pwd);
    CommonResult getUser(String loginId);
}
