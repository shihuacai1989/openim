package com.openim.common.im.bean;

/**
 * Created by shihuacai on 2015/7/21.
 * 终端发送消息类型
 */
public class MessageType {

    public static final int LOGIN = 1;

    public static final int CHAT = 2;

    public static final int LOGOUT = 3;

    /**
     * 消息接收回执
     */
    public static final int RECEIVE = 4;

    /**
     * 终端心跳
     */
    public static final int HEART_BEAT = 5;

    public static final int LEAVE = 6;

    public static final int Working = 7;

    /**
     * 消息打开回执
     */
    //public static final int OPEN = 4;

    /**
     * 终端信息采集，包括设备名称，应用列表
     */
    //public static final int INFO = 5;

    /**
     * 终端位置信息
     */
    //public static final int LOCATION = 6;
}
