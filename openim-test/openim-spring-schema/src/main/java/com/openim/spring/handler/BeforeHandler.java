package com.openim.spring.handler;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * Created by shihc on 2015/9/28.
 */
public interface BeforeHandler {
    void execute(Map<String, Object> body,HttpServletRequest request) throws Exception;
}
