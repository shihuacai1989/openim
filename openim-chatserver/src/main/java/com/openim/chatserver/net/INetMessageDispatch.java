package com.openim.chatserver.net;

/**
 * Created by shihc on 2015/8/31.
 */
public interface INetMessageDispatch<S, T> {
    void dispatch(S session, T message);
}
