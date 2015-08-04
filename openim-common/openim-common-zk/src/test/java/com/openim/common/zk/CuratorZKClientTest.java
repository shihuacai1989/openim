package com.openim.common.zk;

import com.openim.common.util.CharsetUtil;
import com.openim.common.zk.client.CuratorZKClient;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by shihc on 2015/8/4.
 */
public class CuratorZKClientTest {

    private CuratorZKClient curatorZKClient;

    @Before
    public void connectZK() {
        String zkServers = "192.168.85.64:2181,192.168.85.65:2181,192.168.85.66:2181";
        curatorZKClient = new CuratorZKClient();
        curatorZKClient.connectZK(zkServers);
    }

    @Test
    public void testPersistNode() {
        curatorZKClient.addPersistNode("/openIM", "testOpenIm".getBytes(CharsetUtil.utf8));
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testEphemeralSequentialNode() {
        curatorZKClient.addEphemeralSequentialNode("/openIM/chatServer", "testOpenIm".getBytes(CharsetUtil.utf8));
        try {
            Thread.sleep(50000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
