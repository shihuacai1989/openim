package com.openim.manager.cache.login;

import com.google.common.collect.Maps;

import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 存储所用用户连接的服务器信息，实际为队列名称
 * Created by shihc on 2015/7/30.
 */
public class LoginMemoryCache implements ILoginCache {
    private ReadWriteLock lock = new ReentrantReadWriteLock();
    private Lock writeLock = lock.writeLock();
    private Lock readLock = lock.readLock();

    private Map<String, String> loginMap = Maps.newHashMap();

    /**
     * @param key loginId
     * @param value chatServer 推送服务器对应的队列名称
     */
    @Override
    public void add(String key, String value) {
        writeLock.lock();
        loginMap.put(key, value);
        writeLock.unlock();
    }

    @Override
    public String get(String key) {
        readLock.lock();
        String value = loginMap.get(key);
        readLock.unlock();
        return value;
    }
}
