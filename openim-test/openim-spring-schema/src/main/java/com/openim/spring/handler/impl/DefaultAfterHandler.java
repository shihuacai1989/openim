package com.openim.spring.handler.impl;

import com.openim.spring.handler.AfterHandler;

import javax.servlet.http.HttpServletResponse;

/**
 * Created by shihc on 2015/9/28.
 */
public class DefaultAfterHandler implements AfterHandler {

    @Override
    public void execute(Object responseObj, HttpServletResponse response) {
        System.out.println("DefaultAfterHandler");
    }
}
