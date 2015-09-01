package com.openim.chatserver.net.bean;

/**
 * Created by shihc on 2015/9/1.
 */
public interface Session {
    public static final String LOGINID_FIELD = "loginId";

    String getLoginId();
    void setAttr(String key, String value);
    void write(Object message);
}
