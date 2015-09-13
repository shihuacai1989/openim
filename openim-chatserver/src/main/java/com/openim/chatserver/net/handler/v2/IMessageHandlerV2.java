package com.openim.chatserver.net.handler.v2;

import com.openim.chatserver.bean.Session;
import com.openim.common.im.bean.ExchangeMessage;

/**
 * Created by shihuacai on 2015/7/22.
 */
public interface IMessageHandlerV2 {
    void handle(Session session, ExchangeMessage exchangeMessage);
}
