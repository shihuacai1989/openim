package com.shihc.openim.manager.bean;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Date;

/**
 * Created by shihuacai on 2015/7/20.
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
    private String loginName;

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

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
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
}
