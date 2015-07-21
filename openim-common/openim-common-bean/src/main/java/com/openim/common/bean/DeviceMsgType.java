package com.openim.common.bean;

/**
 * Created by shihuacai on 2015/7/21.
 * 终端发送消息类型
 */
public class DeviceMsgType {

    /**
     * 应用上线
     */
    public static final int ONLINE = 1;

    /**
     * 应用下线
     */
    public static final int OFFLINE = 2;

    /**
     * 消息接收回执
     */
    public static final int RECEIVE = 3;

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
    public static final int LOCATION = 6;
}
