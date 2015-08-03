package com.openim.manager.controller;

import com.openim.common.bean.CommonResult;
import com.openim.common.bean.ResultCode;
import com.openim.manager.bean.User;
import com.openim.manager.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by shihuacai on 2015/7/20.
 * 用户控制器
 */
@RequestMapping("/user")
@Controller
public class UserController {

    @Autowired
    private IUserService userService;

    /**
     * 用户注册
     * @param loginId
     * @param password
     * @param password2
     * @return
     */
    @RequestMapping(value = "/register", method = {RequestMethod.GET, RequestMethod.POST}, produces = { "application/json;charset=UTF-8" })
    @ResponseBody
    public CommonResult register(String loginId, String password, String password2){
        int code = ResultCode.success;
        String error = null;
        if(StringUtils.isEmpty(loginId) ||
           StringUtils.isEmpty(password) ||
           StringUtils.isEmpty(password2)){
            code = ResultCode.parameter_null;
        }else if(!password.equals(password2)){
            code = ResultCode.parameter_error;
            error = "密码与确认密码不一致";
        }else{
            User user = new User();
            user.setLoginId(loginId);
            user.setPassword(password);
            CommonResult addResult = userService.addUser(user);
            code = addResult.getCode();
            error = addResult.getError();
        }

        return new CommonResult(code, null, error);
    }

    @RequestMapping(value = "/checkLogin", method = {RequestMethod.GET, RequestMethod.POST}, produces = { "application/json;charset=UTF-8" })
    @ResponseBody
    public CommonResult checkLogin(String loginId, String password){
        return userService.userExist(loginId, password);
    }

    @RequestMapping(value = "/getUser", method = {RequestMethod.GET, RequestMethod.POST}, produces = { "application/json;charset=UTF-8" })
    @ResponseBody
    public CommonResult getUser(String loginId){
        return userService.getUser(loginId);
    }

    @RequestMapping(value = "/addGroup", method = {RequestMethod.GET, RequestMethod.POST}, produces = { "application/json;charset=UTF-8" })
    @ResponseBody
    public CommonResult addGroup(String loginId, String groupName){
        return userService.addGroup(loginId, groupName);
    }
}
