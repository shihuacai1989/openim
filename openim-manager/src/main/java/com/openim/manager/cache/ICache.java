package com.openim.manager.cache;

import com.openim.manager.exception.NotCompletionException;

/**
 * Created by shihc on 2015/7/30.
 */
public interface ICache<K,V> {
    void add(K key, V value) throws NotCompletionException;
    V get(K key) throws NotCompletionException;
}
