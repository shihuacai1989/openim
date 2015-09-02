package com.openim.chatserver.net.bean;

import org.apache.mina.core.session.IoSession;

/**
 * Created by shihc on 2015/9/1.
 */
public class MinaSession extends Session {

    private IoSession ioSession;

    public MinaSession(String loginId, IoSession ioSession) {
        super(loginId);
        this.ioSession = ioSession;
    }

    @Override
    public void write(Object message) {
        ioSession.write(message);
    }
}
