package com.openim.chatserver.dispatch.handle.v2;

import com.openim.common.im.bean.ExchangeMessage;

/**
 * Created by shihc on 2015/8/26.
 */
public interface IMessageHandler {
    void handle(ExchangeMessage exchangeMessage);
}
