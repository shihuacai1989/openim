package com.openim.fileserver.controller;

import com.openim.common.bean.CommonResult;
import com.openim.fileserver.service.IFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by shihc on 2015/8/17.
 */
@RequestMapping("/file")
@Controller
public class FileController {

    @Autowired
    private IFileService fileService;

    @RequestMapping(value = "/upload", method = {RequestMethod.GET, RequestMethod.POST}, produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public CommonResult upload(){
        return null;
    }

    @RequestMapping(value = "/download", method = {RequestMethod.GET, RequestMethod.POST}, produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public CommonResult download(){
        return null;
    }
}
