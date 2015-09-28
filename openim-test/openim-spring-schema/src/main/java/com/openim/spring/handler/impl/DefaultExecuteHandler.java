package com.openim.spring.handler.impl;

import com.openim.spring.handler.ExecuteHandler;

import java.util.Map;

/**
 * Created by shihc on 2015/9/28.
 */
public class DefaultExecuteHandler implements ExecuteHandler {
    @Override
    public Object execute(String url, Map<String, Object> body) throws Exception {
        System.out.println("DefaultExecuteHandler");
        return null;
    }
}
