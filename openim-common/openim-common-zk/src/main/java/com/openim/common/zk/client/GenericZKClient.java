package com.openim.common.zk.client;

import com.openim.common.zk.ChatServerNodeChangedListener;
import com.openim.common.zk.bean.Node;
import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by shihuacai on 2015/7/20.
 */
public class GenericZKClient implements IZKClient {

    protected final static Integer MAX_CONNECT_ATTEMPT = 5;
    private static final Logger LOG = LoggerFactory.getLogger(GenericZKClient.class);
    private ZooKeeper zooKeeper;

    private AtomicBoolean connected = new AtomicBoolean(false);
    private Stat stat = new Stat();

    private String listeningPath = null;
    private ChatServerNodeChangedListener nodeChangedListener = null;

    @Override
    public void connectZK(String zkServers) {
        try {
            zooKeeper = new ZooKeeper(zkServers, 5000, null);
            //Wait till connection is established.
            Integer connectAttempt = 0;
            while (zooKeeper.getState() != ZooKeeper.States.CONNECTED) {
                Thread.sleep(1000);
                connectAttempt++;
                if (connectAttempt == MAX_CONNECT_ATTEMPT) {
                    LOG.error("未连接到zk");
                }
            }
            if (connectAttempt < MAX_CONNECT_ATTEMPT) {
                connected.set(true);
            }
        } catch (IOException e) {
            LOG.error(e.toString());
        } catch (InterruptedException e) {
            LOG.error(e.toString());
        }
    }

    @Override
    public void connectZK(String zkServers, String listeningPath, ChatServerNodeChangedListener nodeChangedListener) {
        try {
            this.listeningPath = listeningPath;
            this.nodeChangedListener = nodeChangedListener;

            zooKeeper = new ZooKeeper(zkServers, 5000, new Watcher() {
                @Override
                public void process(WatchedEvent event) {
                    LOG.debug("监听到zookeeper事件：name:{}, path:{}", event.getType().name(), event.getPath());
                    // 如果发生了"/sgroup"节点下的子节点变化事件, 更新server列表, 并重新注册监听
                    if (event.getType() == Event.EventType.NodeChildrenChanged
                            && GenericZKClient.this.listeningPath.equals(event.getPath())) {
                        try {
                            updateServerList();
                        } catch (Exception e) {
                            LOG.error(e.toString());
                        }
                    }
                }
            });
            //Wait till connection is established.
            Integer connectAttempt = 0;
            while (zooKeeper.getState() != ZooKeeper.States.CONNECTED) {
                Thread.sleep(1000);
                connectAttempt++;
                if (connectAttempt == MAX_CONNECT_ATTEMPT) {
                    LOG.error("未连接到zk");
                }
            }
            if (connectAttempt < MAX_CONNECT_ATTEMPT) {
                connected.set(true);
                updateServerList();
            }
        } catch (IOException e) {
            LOG.error(e.toString());
        } catch (InterruptedException e) {
            LOG.error(e.toString());
        }
    }

    private void updateServerList() {
        boolean success = false;
        List<Node> nodeList = null;
        try {
            nodeList = getChildren(listeningPath, true);
            success = true;
        } catch (Exception e) {
            LOG.error(e.toString());
        }
        nodeChangedListener.onChanged(nodeList, success);
    }

    public AtomicBoolean getConnected() {
        return connected;
    }

    @Override
    public void addPersistNode(String path, byte[] data) {
        if (connected.get()) {
            try {
                //不能以"/"结尾
                Stat stat = zooKeeper.exists(path, true);
                if (stat != null) {
                    LOG.info("节点{}已存在", path);
                } else {
                    //创建目录，需带上"/"，否则为创建节点，导致createServerNode调用失败
                    String createdPath = zooKeeper.create(path,
                            data,
                            ZooDefs.Ids.OPEN_ACL_UNSAFE,
                            CreateMode.PERSISTENT);
                    LOG.info("已创建节点" + path);
                }
            } catch (KeeperException e) {
                LOG.error(e.toString());
            } catch (InterruptedException e) {
                LOG.error(e.toString());
            }
        }
    }

    @Override
    public void addEphemeralSequentialNode(String path, byte[] data) {
        if (connected.get()) {
            try {
                //不能以"/"结尾
                Stat stat = zooKeeper.exists(path, true);
                if (stat != null) {
                    LOG.info("节点{}已存在", path);
                } else {
                    //创建目录，需带上"/"，否则为创建节点，导致createServerNode调用失败
                    String createdPath = zooKeeper.create(path,
                            data,
                            ZooDefs.Ids.OPEN_ACL_UNSAFE,
                            CreateMode.EPHEMERAL_SEQUENTIAL);
                    LOG.info("已创建节点" + path);
                }
            } catch (KeeperException e) {
                LOG.error(e.toString());
            } catch (InterruptedException e) {
                LOG.error(e.toString());
            }
        }
    }

    @Override
    public List<Node> getChildren(String path, boolean watch) throws Exception {
        List<Node> nodeList = new ArrayList<Node>();

        // 获取并监听groupNode的子节点变化
        // watch参数为true, 表示监听子节点变化事件.
        // 每次都需要重新注册监听, 因为一次注册, 只能监听一次事件, 如果还想继续保持监听, 必须重新注册
        List<String> subList = zooKeeper.getChildren(path, watch);
        for (String subNode : subList) {
            String nodePath = path + "/" + subNode;
            byte[] data = zooKeeper.getData(nodePath, false, stat);
            nodeList.add(new Node(nodePath, data));
        }

        return nodeList;
    }

    public void deleteNode(String path) {

    }
}
