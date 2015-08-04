package com.openim.esb.controller;

import com.openim.common.bean.CommonResult;
import com.openim.esb.service.IChatServerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by shihuacai on 2015/7/21.
 * 推送服务控制器
 */
@Controller
@RequestMapping(value = "/server")
public class ServerController {

    @Autowired
    private IChatServerService chatServerService;
    /**
     * 随机获取局域网的ip地址
     * @return
     */
    @RequestMapping(value = "/localNet", method = {RequestMethod.GET, RequestMethod.POST}, produces = { "application/json;charset=UTF-8" })
    @ResponseBody
    public CommonResult localNet() {
        return chatServerService.randomInnerServer();
    }

    /**
     * 随机获取公网的ip地址
     * @return
     */
    @RequestMapping(value = "/outerNet", method = {RequestMethod.GET, RequestMethod.POST}, produces = { "application/json;charset=UTF-8" })
    @ResponseBody
    public CommonResult outerNet() {
        return chatServerService.randomOuterServer();
    }
}
