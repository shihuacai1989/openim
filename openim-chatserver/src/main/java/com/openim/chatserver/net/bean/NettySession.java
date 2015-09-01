package com.openim.chatserver.net.bean;

import io.netty.channel.Channel;

/**
 * Created by shihc on 2015/9/1.
 */
public class NettySession implements Session {
    private String loginId;

    private Channel channel;

    public NettySession(String loginId, Channel channel) {
        this.loginId = loginId;
        this.channel = channel;
    }

    @Override
    public String getLoginId() {
        return null;
    }

    @Override
    public void setAttr(String key, String value) {

    }

    @Override
    public void write(Object message) {
        channel.writeAndFlush(message);
    }
}
