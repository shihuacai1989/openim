package com.openim.common.bean;

/**
 * Created by shihuacai on 2015/7/21.
 * 终端发送消息类型
 */
public class DeviceLoginMsg extends DeviceMsg {

    public String loginId;

    public String pwd;

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
}
