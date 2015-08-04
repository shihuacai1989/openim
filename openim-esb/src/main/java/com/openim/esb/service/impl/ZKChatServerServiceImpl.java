package com.openim.esb.service.impl;

import com.openim.common.zk.ChatServerNodeChangedListener;
import com.openim.common.zk.OpenIMZKClient;
import com.openim.common.zk.bean.Node;
import com.openim.esb.service.IChatServerService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by shihc on 2015/8/4.
 */
@Service
public class ZKChatServerServiceImpl implements IChatServerService {

    @Value("${zkServers}")
    private String zkServers;

    @Override
    public void afterPropertiesSet() throws Exception {
        OpenIMZKClient imZKClient = new OpenIMZKClient(zkServers);
        imZKClient.connectZKServer(new ChatServerNodeChangedListener() {
            @Override
            public void onChanged(List<Node> data, boolean success) {
                System.out.println("推送服务地址：" + data);
            }
        });
    }
}
