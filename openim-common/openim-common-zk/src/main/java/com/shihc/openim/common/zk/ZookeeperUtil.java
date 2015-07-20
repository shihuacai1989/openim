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
public class ZookeeperClient {

    public final static Integer MAX_CONNECT_ATTEMPT = 5;

    private final static Logger logger = LoggerFactory.getLogger(ZookeeperClient.class);


    public ZooKeeper createZKConnection(String servers) throws IOException, InterruptedException {
        return null;
        /*Integer connectAttempt = 0;

        ZooKeeper zk = new ZooKeeper(servers, 2000, new Watcher() {
            @Override
            public void process(WatchedEvent event) {
                logger.trace("Connecting to ZK.");
            }
        });
        //Wait till connection is established.
        while (zk.getState() != ZooKeeper.States.CONNECTED) {
            Thread.sleep(30);
            connectAttempt++;
            if (connectAttempt == MAX_CONNECT_ATTEMPT) {
                break;
            }
        }
        return zk;*/
    }
}
