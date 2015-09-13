package com.openim.chatserver.bean;

/**
 * Created by shihc on 2015/9/1.
 */
public abstract class Session {

    public static final String LOGINID_FIELD = "loginId";

    private String loginId;

    public Session(String loginId) {
        this.loginId = loginId;
    }

    public String getLoginId(){
        return loginId;
    }

    public abstract void write(Object message);

    public abstract void close();
}
