package com.openim.common.mq;

import com.openim.common.im.AbstractMessageDispatch;

/**
 * Created by shihuacai on 2015/7/28.
 */
public abstract class MessageQueueDispatch extends AbstractMessageDispatch{
    public abstract void dispatchMessage(String exchange, String routeKey, byte[] message);
}
