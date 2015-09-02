package com.openim.chatserver.net.bean;

import io.netty.channel.Channel;

/**
 * Created by shihc on 2015/9/1.
 */
public class NettySession extends Session {

    private Channel channel;

    public NettySession(String loginId, Channel channel) {
        super(loginId);
        this.channel = channel;
    }

    @Override
    public void write(Object message) {
        channel.writeAndFlush(message);
    }
}
