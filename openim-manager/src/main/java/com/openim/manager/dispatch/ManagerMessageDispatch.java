package com.openim.manager.dispatch;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.openim.common.mq.IMessageDispatch;
import com.openim.common.util.CharsetUtil;
import org.springframework.stereotype.Component;

import java.nio.charset.Charset;

/**
 * Created by shihuacai on 2015/7/29.
 */
@Component
public class ManagerMessageDispatch implements IMessageDispatch {

    private static final Charset charset = Charset.forName("UTF-8");

    @Override
    public void dispatchMessage(String exchange, String routeKey, byte[] bytes) {
        if(bytes != null){
            String message = new String(bytes, CharsetUtil.utf8);
            JSONObject jsonObject = JSON.parseObject(message);

        }
    }
}
