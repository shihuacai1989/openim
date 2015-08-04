package com.openim.common.zk.client;

import com.openim.common.zk.ChatServerNodeChangedListener;
import com.openim.common.zk.bean.Node;

import java.util.List;

/**
 * Created by shihuacai on 2015/7/21.
 */
public interface IZKClient {


    void connectZK(String zkServers);

    void connectZK(String zkServers, String listeningPath, ChatServerNodeChangedListener listener);

    void addPersistNode(String path, byte[] data);

    void addEphemeralSequentialNode(String path, byte[] data);

    List<Node> getChildren(String path, boolean watch) throws Exception;

    void deleteNode(String path);
}
