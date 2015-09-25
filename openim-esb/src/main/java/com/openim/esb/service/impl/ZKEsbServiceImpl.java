package com.openim.esb.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.openim.common.im.bean.CommonResult;
import com.openim.common.im.bean.ResultCode;
import com.openim.common.util.CharsetUtil;
import com.openim.common.zk.INodeChangedListener;
import com.openim.common.zk.OpenIMZKClient;
import com.openim.common.zk.bean.Node;
import com.openim.common.zk.bean.NodeField;
import com.openim.esb.service.IEsbService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Created by shihc on 2015/8/4.
 */
@Service
public class ZKEsbServiceImpl implements IEsbService {

    private static final Logger LOG = LoggerFactory.getLogger(ZKEsbServiceImpl.class);

    @Value("${zkServers}")
    private String zkServers;

    @Autowired
    OpenIMZKClient imZKClient;

    private ReadWriteLock lock = new ReentrantReadWriteLock();
    private Lock writeLock = lock.writeLock();
    private Lock readLock = lock.readLock();

    private volatile List<String> innerServers;
    private volatile List<String> outerServers;

    //private volatile List<Node> nodeList;

    @Override
    public void afterPropertiesSet() throws Exception {
        imZKClient.connectZKServer(new INodeChangedListener() {
            @Override
            public void onChanged(List<Node> data, boolean success) {
                writeLock.lock();
                try {
                    //nodeList = data;
                    List<String> tmpInnerServers = null;
                    List<String> tmpOuterServers = null;
                    if (!CollectionUtils.isEmpty(data)) {
                        tmpInnerServers = new ArrayList<String>(data.size());
                        tmpOuterServers = new ArrayList<String>(data.size());
                        for (int i = 0; i < data.size(); i++) {
                            byte[] nodeData = data.get(i).getData();
                            JSONObject jsonObject = JSON.parseObject(new String(nodeData, CharsetUtil.utf8));
                            String outerNet = jsonObject.getString(NodeField.outerNet);
                            String innerNet = jsonObject.getString(NodeField.innerNet);
                            tmpInnerServers.add(innerNet);
                            tmpOuterServers.add(outerNet);
                        }
                    }
                    innerServers = tmpInnerServers;
                    outerServers = tmpOuterServers;
                    System.out.println("推送服务地址：" + data);
                } catch (Exception e) {
                    LOG.error(e.toString());
                }

                writeLock.unlock();
            }
        });
    }

    @Override
    public CommonResult<String> randomInnerServer() {
        readLock.lock();
        String server = null;
        if (!CollectionUtils.isEmpty(innerServers)) {
            server = innerServers.get(new Random().nextInt(innerServers.size()));
        } else {
            LOG.error("无推送服务器内网地址");
        }
        readLock.unlock();
        return new CommonResult<String>(ResultCode.success, server, null);
    }

    @Override
    public CommonResult<String> randomOuterServer() {
        readLock.lock();
        String server = null;
        if (!CollectionUtils.isEmpty(outerServers)) {
            server = outerServers.get(new Random().nextInt(outerServers.size()));
        } else {
            LOG.error("无推送服务器外网地址");
        }
        readLock.unlock();
        return new CommonResult<String>(ResultCode.success, server, null);
    }
}
