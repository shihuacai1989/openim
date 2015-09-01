package com.openim.chatserver.net.mina;

import com.openim.chatserver.net.IMessageDispatch;
import com.openim.common.im.bean.ExchangeMessage;
import org.apache.mina.core.session.IoSession;

/**
 * Created by shihc on 2015/8/31.
 */
public interface IMinaMessageDispatch extends IMessageDispatch<IoSession, ExchangeMessage> {
    void dispatch(IoSession ioSession, ExchangeMessage msg);
}
