package com.openim.manager.cache;

/**
 * Created by shihc on 2015/7/30.
 */
public interface ICache<K,V> {
    void add(K key, V value);
    V get(K key);
}
