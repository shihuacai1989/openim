package com.openim.chatserver.net.server.netty;

import com.openim.chatserver.net.server.IMessageDispatch;
import com.openim.common.im.bean.ExchangeMessage;
import io.netty.channel.ChannelHandlerContext;

/**
 * Created by shihc on 2015/8/31.
 */
public interface INettyMessageDispatch extends IMessageDispatch<ChannelHandlerContext, ExchangeMessage> {
    void dispatch(ChannelHandlerContext ctx, ExchangeMessage msg);
}
