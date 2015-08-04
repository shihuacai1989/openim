package com.openim.common.zk;

import com.openim.common.util.CharsetUtil;
import com.openim.common.zk.client.GenericZKClient;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by shihc on 2015/8/4.
 */
public class GenericZKClientTest {

    private GenericZKClient genericZKClient;

    @Before
    public void connectZK() {
        String zkServers = "192.168.85.64:2181,192.168.85.65:2181,192.168.85.66:2181";
        genericZKClient = new GenericZKClient();
        genericZKClient.connectZK(zkServers);
    }

    @Test
    public void testPersistNode() {
        genericZKClient.addPersistNode("/openIM", "testOpenIm".getBytes(CharsetUtil.utf8));
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testEphemeralSequentialNode() {
        genericZKClient.addEphemeralSequentialNode("/openIM/chatServer", "testOpenIm".getBytes(CharsetUtil.utf8));
        try {
            Thread.sleep(Integer.MAX_VALUE);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
