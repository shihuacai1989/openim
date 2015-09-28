package com.openim.spring.handler;

import javax.servlet.http.HttpServletResponse;

/**
 * Created by shihc on 2015/9/28.
 */
public interface AfterHandler {
    void execute(Object responseObj, HttpServletResponse response);
}
