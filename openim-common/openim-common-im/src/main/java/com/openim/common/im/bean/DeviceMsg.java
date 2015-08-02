package com.openim.common.im.bean;

import java.io.Serializable;

/**
 * Created by shihuacai on 2015/8/1.
 */
public class DeviceMsg implements Serializable {
    private int type;
    private String to;
    private String msg;
    private String from;
    private String loginId;
    private String pwd;
    private String serverQueue;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getLoginId() {
        return loginId;
    }

    public void setLoginId(String loginId) {
        this.loginId = loginId;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getServerQueue() {
        return serverQueue;
    }

    public void setServerQueue(String serverQueue) {
        this.serverQueue = serverQueue;
    }

    @Override
    public String toString() {
        return "type:" + type;
    }
}
