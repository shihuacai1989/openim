package com.openim.spring.controller;

import com.openim.spring.bean.UrlHandler;
import com.openim.spring.bean.UrlHandlerMap;
import com.openim.spring.handler.AfterHandler;
import com.openim.spring.handler.BeforeHandler;
import com.openim.spring.handler.ExecuteHandler;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * Created by shihc on 2015/9/28.
 */
@Controller
@RequestMapping("/total")
public class TotalController {
    private static Logger logger = LoggerFactory.getLogger(TotalController.class);


    @Resource
    private UrlHandlerMap urlHandlerMap;
    /**
     * 接收所有的请求
     */
    @RequestMapping(value = "/**", method = {RequestMethod.GET, RequestMethod.POST}, produces = { "application/json;charset=UTF-8" })
    @ResponseBody
    public Object total(@RequestBody Map<String, Object> body,
                        HttpServletRequest request,
                        HttpServletResponse response) throws Exception {
        String[] urls = StringUtils.splitPreserveAllTokens(request.getRequestURI(), "/", 4);
        String api = urls[urls.length - 1];
        logger.info("请求api:{} body:{}", api,body.toString());

        // 之前
        UrlHandler urlHandler = urlHandlerMap.getUrlHandler(api);
        if(urlHandler == null) {
            return "系统异常，找不到可用的handler";
        }

        for (BeforeHandler beforeHandler : urlHandler.getBeforeHandlerList()) {
            beforeHandler.execute(body, request);
        }

        // 路由请求
        ExecuteHandler executeHandler = urlHandler.getExecuteHandler();
        Object resultData = executeHandler.execute(api, body);

        // 之后
        for (AfterHandler afterHandler : urlHandler.getAfterHandlerList()) {
            afterHandler.execute(resultData, response);
        }
        return resultData;
    }
}
