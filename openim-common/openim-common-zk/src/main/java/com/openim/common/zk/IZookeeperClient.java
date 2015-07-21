package com.openim.common.zk;

import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

/**
 * Created by shihuacai on 2015/7/21.
 */
public interface IZookeeperClient {

    void connectZK(String zkServers);

    void connectZK(String zkServers, Watcher watcher);

    void addNode(String path, String data);

    void deleteNode(String path);
}
