package com.openim.spring.handler;

import java.util.Map;

/**
 * Created by shihc on 2015/9/28.
 */
public interface ExecuteHandler {
    Object execute(String url, Map<String, Object> body) throws Exception;
}
