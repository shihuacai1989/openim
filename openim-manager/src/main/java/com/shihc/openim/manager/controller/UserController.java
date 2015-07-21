package com.shihc.openim.manager.controller;

import com.shihc.openim.common.bean.CommonResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by shihuacai on 2015/7/20.
 * 用户控制器
 */
@RequestMapping()
@RestController
public class UserController {

    /**
     * 用户注册
     * @param loginName
     * @return
     */
    @ResponseBody
    @RequestMapping()
    public CommonResult register(String loginName){

        return null;
    }
}
