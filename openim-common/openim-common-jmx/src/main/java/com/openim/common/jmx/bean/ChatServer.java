package com.openim.common.jmx.bean;

/**
 * Created by shihuacai on 2015/9/22.
 */
public class ChatServer implements ChatServerMBean {
    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    private String name;

    public String status() {
        return "this is a Controller MBean,name is " + this.name;
    }

    public void start() {
        // TODO Auto-generated method stub
    }


    public void stop() {
        // TODO Auto-generated method stub
    }
}
