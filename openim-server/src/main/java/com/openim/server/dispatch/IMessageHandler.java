package com.openim.server.dispatch;

import com.alibaba.fastjson.JSONObject;

/**
 * Created by shihuacai on 2015/7/22.
 */
public interface IMessageHandler {
    void handle(JSONObject jsonObject);
}
