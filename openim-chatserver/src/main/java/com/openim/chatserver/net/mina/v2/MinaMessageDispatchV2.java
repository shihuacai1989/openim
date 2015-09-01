package com.openim.chatserver.net.mina.v2;

import com.openim.chatserver.net.mina.IMinaMessageDispatch;
import com.openim.common.im.bean.ExchangeMessage;
import org.apache.mina.core.session.IoSession;

/**
 * Created by shihc on 2015/9/1.
 */
public class MinaMessageDispatchV2 implements IMinaMessageDispatch {
    @Override
    public void dispatch(IoSession ioSession, ExchangeMessage msg) {

    }
}
