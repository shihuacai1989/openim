package com.openim.chatserver.register.impl;

import com.alibaba.fastjson.JSONObject;
import com.openim.chatserver.register.IServerRegister;
import com.openim.common.util.CharsetUtil;
import com.openim.common.util.IPUtil;
import com.openim.common.zk.OpenIMZKClient;
import com.openim.common.zk.bean.NodeField;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Created by shihc on 2015/8/4.
 */
@Component
public class ZKServerRegister implements IServerRegister {

    @Value("${zkServers}")
    private String zkServers;

    @Value("${outerNet}")
    private String outerNet;

    @Value("${chat.port}")
    private String chatPort;

    @Override
    public void afterPropertiesSet() throws Exception {
        //如果zk未启动，则连接出错，阻塞在此处，影响系统其他组件的初始化工作，故新开一个线程注册服务;
        new Thread(new Runnable() {
            @Override
            public void run() {
                register();
            }
        }).start();

    }

    @Override
    public void register() {
        OpenIMZKClient openIMZKClient = new OpenIMZKClient(zkServers);
        openIMZKClient.connectZKServer();
        openIMZKClient.addRootNode();

        JSONObject jsonObject = new JSONObject();
        jsonObject.put(NodeField.innerNet, IPUtil.getLocalIP() + ":" + chatPort);
        jsonObject.put(NodeField.outerNet, outerNet);

        openIMZKClient.addChatSeverNode(jsonObject.toString().getBytes(CharsetUtil.utf8));
    }
}
