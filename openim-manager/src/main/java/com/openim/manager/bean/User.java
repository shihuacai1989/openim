package com.openim.manager.bean;

import com.openim.common.im.bean.LoginStatus;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Date;
import java.util.List;

/**
 * Created by shihuacai on 2015/7/20.
 * 注册用户
 */
@Document(collection = "user")
public class User {

    @Id
    private String id;

    /**
     * 昵称
     */
    @Field
    private String nickName;

    /**
     * 登录名
     */
    @Field
    private String loginId;

    /**
     * 登录密码
     */
    @Field
    private String password;

    /**
     * 注册时间
     */
    @Field
    private Date registerTime;

    /**
     * 最后一次登录时间
     */
    @Field
    private Date lastLoginTime;

    /**
     * 终端最后收到的消息的发送时间，对应Message中的sendTime字段
     */
    @Field
    private Date lastReadMsgTime;

    @Field
    private List<Group> groups;

    @Field
    private List<Friend> friends;

    @Field
    private int loginStatus = LoginStatus.online;

    @Field
    private String connectServer;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getLoginId() {
        return loginId;
    }

    public void setLoginId(String loginId) {
        this.loginId = loginId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getRegisterTime() {
        return registerTime;
    }

    public void setRegisterTime(Date registerTime) {
        this.registerTime = registerTime;
    }

    public Date getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(Date lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

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

    public List<Group> getGroups() {
        return groups;
    }

    public void setGroups(List<Group> groups) {
        this.groups = groups;
    }

    public List<Friend> getFriends() {
        return friends;
    }

    public void setFriends(List<Friend> friends) {
        this.friends = friends;
    }

    public Date getLastReadMsgTime() {
        return lastReadMsgTime;
    }

    public void setLastReadMsgTime(Date lastReadMsgTime) {
        this.lastReadMsgTime = lastReadMsgTime;
    }
}
