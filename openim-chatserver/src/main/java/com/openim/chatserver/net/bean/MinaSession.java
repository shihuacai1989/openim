package com.openim.chatserver.net.bean;

import org.apache.mina.core.session.IoSession;

/**
 * Created by shihc on 2015/9/1.
 */
public class MinaSession implements Session {
    private String loginId;

    private IoSession ioSession;

    public MinaSession(String loginId, IoSession ioSession) {
        this.loginId = loginId;
        this.ioSession = ioSession;
    }

    @Override
    public String getLoginId() {
        return null;
    }

    @Override
    public void setAttr(String key, String value) {

    }

    @Override
    public void write(Object message) {
        ioSession.write(message);
    }
}
