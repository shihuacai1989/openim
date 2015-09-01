package com.openim.emulator.client.netty.v2;

import com.openim.common.im.bean.ExchangeMessage;
import com.openim.common.im.bean.MessageType;
import com.openim.common.im.bean.protbuf.ProtobufChatMessage;
import com.openim.common.im.bean.protbuf.ProtobufFriendOffLineMessage;
import com.openim.common.im.bean.protbuf.ProtobufFriendOnLineMessage;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * Created by shihuacai on 2015/7/22.
 */
public class ProtobufChatClientHandler extends SimpleChannelInboundHandler<ExchangeMessage> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ExchangeMessage exchangeMessage) throws Exception {
        int type = exchangeMessage.getType();

        if(type == MessageType.CHAT) {
            ProtobufChatMessage.ChatMessage chatMessage = exchangeMessage.getMessageLite();
            System.out.println("收到消息, from:" + chatMessage.getFrom() + " msg:" + chatMessage.getMsg());
        }else if(type == MessageType.FRIEND_ONLINE){
            ProtobufFriendOnLineMessage.FriendOnLineMessage friendOnLineMessage = exchangeMessage.getMessageLite();
            System.out.println("好友上线, friendLoginId:" + friendOnLineMessage.getFriendLoginId());
        }else if(type == MessageType.FRIEND_OFFLINE){
            ProtobufFriendOffLineMessage.FriendOffLineMessage friendOffLineMessage = exchangeMessage.getMessageLite();
            System.out.println("好友下线, friendLoginId:" + friendOffLineMessage.getFriendLoginId());
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
            throws Exception {
        cause.printStackTrace();
        System.out.print("exceptionCaught");
        ctx.fireExceptionCaught(cause);
    }

    /**
     * 网络正常情况下，服务端主动关闭长连接后，该方法会触发
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.print("channelInactive");
        ctx.fireChannelInactive();

    }
}