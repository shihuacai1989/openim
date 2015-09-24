package com.openim.chatserver.jmx.bean;

/**
 * Created by shihc on 2015/9/23.
 */
public interface IJmxTestBean {
    public int getAge();

    public void setAge(int age);

    public void setName(String name);

    public String getName();

    public int add(int x, int y);

    public void dontExposeMe();
}
