package com.openim.manager.cache.login;

import com.openim.manager.bean.User;
import com.openim.manager.exception.NotCompletionException;

/**
 * memcache存储所用用户连接的服务器信息，实际为队列名称
 * Created by shihc on 2015/7/30.
 */
public class LoginMemcacheCache implements ILoginCache {

    /**
     * @param key loginId
     * @param value chatServer 推送服务器对应的队列名称
     */
    @Override
    public void add(String key, User value) throws NotCompletionException {
        throw new NotCompletionException("LoginMemcacheCache not Completion");
    }

    @Override
    public User get(String key) throws NotCompletionException {
        throw new NotCompletionException("LoginMemcacheCache not Completion");
    }
}
