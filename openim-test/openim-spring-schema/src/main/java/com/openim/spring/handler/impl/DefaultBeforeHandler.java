package com.openim.spring.handler.impl;

import com.openim.spring.handler.BeforeHandler;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * Created by shihc on 2015/9/28.
 */
public class DefaultBeforeHandler implements BeforeHandler {
    @Override
    public void execute(Map<String, Object> body, HttpServletRequest request) throws Exception {
        System.out.println("DefaultBeforeHandler");

    }
}
