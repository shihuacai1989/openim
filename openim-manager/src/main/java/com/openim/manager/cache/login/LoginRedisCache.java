package com.openim.manager.cache.login;

import com.openim.manager.bean.User;
import com.openim.manager.exception.NotCompletionException;

/**
 * redis存储所用用户连接的服务器信息，实际为队列名称
 * Created by shihc on 2015/7/30.
 */
public class LoginRedisCache implements ILoginCache {

    /**
     * @param key loginId
     * @param value chatServer 推送服务器对应的队列名称
     */
    @Override
    public void add(String key, User value) throws NotCompletionException {
        throw new NotCompletionException("LoginRedisCache not Completion");
    }

    @Override
    public User get(String key) throws NotCompletionException {
        throw new NotCompletionException("LoginRedisCache not Completion");
    }
}
