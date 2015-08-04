package com.openim.manager.bean;

import java.util.Date;

/**
 * Created by shihc on 2015/8/3.
 * 用户分组
 */
public class Group {
    private String name;
    private String id;
    private Date createTime;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
