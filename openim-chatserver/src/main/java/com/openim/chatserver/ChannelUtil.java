package com.openim.chatserver;

import com.google.common.collect.Maps;
import io.netty.channel.Channel;

import java.util.Map;

/**
 * Created by shihuacai on 2015/7/29.
 */
public class ChannelUtil {
    public static Map<String, Channel> channelMap = Maps.newConcurrentMap();
    public static void add(String loginId, Channel channel){
        channelMap.put(loginId, channel);
    }
    public static void remove(String loginId){
        channelMap.remove(loginId);
    }
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
