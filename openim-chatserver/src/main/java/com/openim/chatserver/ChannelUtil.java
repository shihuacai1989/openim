package com.openim.chatserver;

import com.google.common.collect.Maps;
import io.netty.channel.Channel;
import io.netty.util.Attribute;
import io.netty.util.AttributeKey;
import org.springframework.util.StringUtils;

import java.util.Map;

/**
 * Created by shihuacai on 2015/7/29.
 */
public class ChannelUtil {
    public static final AttributeKey<String> loginIdKey = AttributeKey.valueOf("loginId");

    private static final Map<String, Channel> channelMap = Maps.newConcurrentMap();

    public static void add(String loginId, Channel channel){
        Attribute<String> attribute = channel.attr(loginIdKey);
        attribute.set(loginId);
        channelMap.put(loginId, channel);
    }
    public static void remove(String loginId){
        if(!StringUtils.isEmpty(loginId)){
            channelMap.remove(loginId);
        }

    }
    public static void remove(Channel channel){
        Attribute<String> attribute = channel.attr(loginIdKey);
        String loginId = attribute.get();
        remove(loginId);
    }

    /**/
    public static Channel get(String loginId){
        return channelMap.get(loginId);
    }

    public static void sendMessage(String loginId, Object message){
        Channel channel = get(loginId);
        if(channel != null){
            channel.writeAndFlush(message);
        }
    }
}
