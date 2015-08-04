package com.openim.common.zk.client;

import com.openim.common.zk.ChatServerNodeChangedListener;
import com.openim.common.zk.bean.Node;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.PathChildrenCache;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheEvent;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheListener;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by shihuacai on 2015/7/20.
 */
public class CuratorZKClient implements IZKClient {

    private static final Logger LOG = LoggerFactory.getLogger(CuratorZKClient.class);
    private CuratorFramework client;
    private final ExecutorService pool = Executors.newFixedThreadPool(1);

    private String listeningPath = null;
    private ChatServerNodeChangedListener nodeChangedListener = null;

    @Override
    public void connectZK(String zkServers) {
        client = CuratorFrameworkFactory.builder()
                .connectString(zkServers)
                //.namespace(Constants.rootNode)
                .sessionTimeoutMs(5000)
                .connectionTimeoutMs(5000)
                .canBeReadOnly(false)
                .retryPolicy(new ExponentialBackoffRetry(3000, Integer.MAX_VALUE))
                .build();
        client.start();
    }
    @Override
    public void connectZK(String zkServers, String listeningPath, ChatServerNodeChangedListener nodeChangedListener) {

        connectZK(zkServers);


        try {
            this.listeningPath = listeningPath;
            this.nodeChangedListener = nodeChangedListener;
            /** 监听子节点的变化情况 */
            final PathChildrenCache childrenCache = new PathChildrenCache(client, listeningPath, true);
            childrenCache.start(PathChildrenCache.StartMode.POST_INITIALIZED_EVENT);
            childrenCache.getListenable().addListener(new PathChildrenCacheListener() {
                @Override
                public void childEvent(CuratorFramework client,
                                       PathChildrenCacheEvent event) throws Exception {
                    switch (event.getType()) {
                        case CHILD_ADDED:
                        case CHILD_REMOVED:
                        case CHILD_UPDATED:
                            updateServerList();
                            break;
                        default:
                            break;
                    }
                }
            }, pool);
        }catch (Exception e){
            LOG.error(e.toString());
        }


            /** 监听数据节点的变化情况 */
            /*final NodeCache nodeCache = new NodeCache(client, "/" + Constants.rootNode,
                    false);
            nodeCache.start(true);
            nodeCache.getListenable().addListener(new NodeCacheListener() {
                @Override
                public void nodeChanged() throws Exception {
                    System.out.println("Node data is changed, new data: " +
                            new String(nodeCache.getCurrentData().getData()));
                }
            }, pool);*/

    }

    private void updateServerList(){
        boolean success = false;
        List<Node> nodeList = null;
        try{
            nodeList = getChildren(listeningPath, true);
            success = true;
        }catch (Exception e){
            LOG.error(e.toString());
        }
        nodeChangedListener.onChanged(nodeList, success);
    }

    @Override
    public void addPersistNode(String path, byte[] data) {
        try {
            client.create().forPath(path, data);
            LOG.info("节点{}创建成功", path);
        } catch (Exception e) {
            LOG.error(e.toString());
        }
    }

    @Override
    public void addEphemeralSequentialNode(String path, byte[] data) {
        try {
            //该方式会在生成的节点名称上添加前缀
            //client.create().withProtection().withMode(CreateMode.EPHEMERAL_SEQUENTIAL).forPath(path);
            client.create().withMode(CreateMode.EPHEMERAL_SEQUENTIAL).forPath(path, data);
            LOG.info("节点{}创建成功", path);
        } catch (Exception e) {
            LOG.error(e.toString());
        }
    }

    @Override
    public List<Node> getChildren(String path, boolean watch) throws Exception {
        List<Node> nodeList = new ArrayList<Node>();

        List<String> subList = client.getChildren().forPath(listeningPath);
        if(subList != null && subList.size() > 0){
            for (String subNode : subList) {
                String nodePath = listeningPath + "/" + subNode;
                byte[] data = client.getData().forPath(nodePath);
                nodeList.add(new Node(nodePath, data));
            }
        }

        return nodeList;
    }

    @Override
    public void deleteNode(String path) {

    }
}
