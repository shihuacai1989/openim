package com.openim.chatserver.net.server;

import com.openim.common.im.bean.ExchangeMessage;
import io.netty.channel.ChannelHandlerContext;

/**
 * Created by shihc on 2015/8/31.
 */
public interface IMessageDispatch<C, T> {
    void dispatch(C channel, T message);
}
