package com.openim.manager.cache.login;

import com.google.common.collect.Maps;
import com.openim.manager.bean.User;

import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 内存存储所用用户实时状态信息
 * Created by shihc on 2015/7/30.
 */
public class LoginMemoryCache implements ILoginCache {
    private ReadWriteLock lock = new ReentrantReadWriteLock();
    private Lock writeLock = lock.writeLock();
    private Lock readLock = lock.readLock();

    private Map<String, User> loginMap = Maps.newHashMap();

    /**
     * @param key loginId
     * @param value chatServer 推送服务器对应的队列名称
     */
    @Override
    public void add(String key, User value) {
        writeLock.lock();
        loginMap.put(key, value);
        writeLock.unlock();
    }

    @Override
    public User get(String key) {
        readLock.lock();
        User value = loginMap.get(key);
        readLock.unlock();
        return value;
    }
}
