package com.openim.manager.bean;

import com.openim.common.im.LoginStatus;

/**
 * Created by shihc on 2015/7/30.
 */
public class UserStatus {
    private int loginStatus = LoginStatus.online;
    private String connectServer;

    public int getLoginStatus() {
        return loginStatus;
    }

    public void setLoginStatus(int loginStatus) {
        this.loginStatus = loginStatus;
    }

    public String getConnectServer() {
        return connectServer;
    }

    public void setConnectServer(String connectServer) {
        this.connectServer = connectServer;
    }
}
