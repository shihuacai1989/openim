package com.openim.manager.handler;

import com.openim.common.im.bean.DeviceMsg;
import com.openim.manager.handler.jdk.LoginHandler;
import com.openim.manager.handler.jdk.LogoutHandler;
import com.openim.manager.handler.jdk.SendHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shihuacai on 2015/7/23.
 */
public class HandlerChain implements IMessageHandler<DeviceMsg> {
    private static final Logger LOG = LoggerFactory.getLogger(HandlerChain.class);

    private List<IMessageHandler> handlerList;

    private int index;
    private int max;

    public HandlerChain() {
        handlerList = new ArrayList<IMessageHandler>();
        handlerList.add(new LoginHandler());
        handlerList.add(new SendHandler());
        handlerList.add(new LogoutHandler());

        index = -1;
        max = handlerList.size() - 1;
    }

    @Override
    public void handle(DeviceMsg jsonObject, HandlerChain handlerChain) {
        index++;
        if (index <= max) {
            handlerList.get(index).handle(jsonObject, handlerChain);
            index = -1;
        } else {
            LOG.error("无法处理的客户端消息: {}", jsonObject.toString());
        }
    }
}
