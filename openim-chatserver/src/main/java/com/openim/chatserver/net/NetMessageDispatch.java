package com.openim.chatserver.net;

import com.openim.common.im.AbstractMessageDispatch;

/**
 * Created by shihc on 2015/8/31.
 */
public abstract class NetMessageDispatch<S, T> extends AbstractMessageDispatch{
    public abstract void dispatch(S session, T message);
}
