package com.shihc.openim.common.zk;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * Created by shihuacai on 2015/7/20.
 */
public class ZookeeperClient implements IZookeeperClient {

    //public final static Integer MAX_CONNECT_ATTEMPT = 5;

    //private final static Logger logger = LoggerFactory.getLogger(ZookeeperClient.class);


    /*public ZooKeeper createZKConnection(String servers) throws IOException, InterruptedException {
        return null;
    }*/

    public void connectZK(String zkServers) {

    }

    public void connectZK(String zkServers, Watcher watcher) {

    }

    public void addNode(String path, String data) {

    }

    public void deleteNode(String path) {

    }
}
