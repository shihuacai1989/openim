package com.openim.chatserver;

import com.google.common.collect.Maps;
import com.openim.chatserver.net.bean.Session;
import io.netty.util.AttributeKey;
import org.springframework.util.StringUtils;

import java.util.Map;

/**
 * Created by shihuacai on 2015/7/29.
 */
public class SessionManager {
    public static final String LOGINID_FIELD = "loginId";
    public static final AttributeKey<String> loginIdKey = AttributeKey.valueOf("loginId");

    private static final Map<String, Session> channelMap = Maps.newConcurrentMap();

    public static void add(String loginId, Session session) {
        channelMap.put(loginId, session);
    }

    public static void remove(String loginId) {
        if (!StringUtils.isEmpty(loginId)) {
            channelMap.remove(loginId);
        }

    }


    /**/
    public static Session get(String loginId) {
        return channelMap.get(loginId);
    }

    public static void sendMessage(String loginId, Object message) {
        Session session = get(loginId);
        if (session != null) {
            session.write(message);
        }
    }
}
