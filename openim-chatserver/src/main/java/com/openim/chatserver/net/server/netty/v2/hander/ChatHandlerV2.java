package com.openim.chatserver.net.server.netty.v2.hander;

import com.openim.chatserver.ChannelUtil;
import com.openim.chatserver.listener.ApplicationContextAware;
import com.openim.chatserver.net.server.netty.v2.INettyMessageHandlerV2;
import com.openim.common.im.bean.ExchangeMessage;
import com.openim.common.im.bean.MessageType;
import com.openim.common.im.bean.protbuf.ProtobufChatMessage;
import com.openim.common.im.codec.mq.IMQCodec;
import com.openim.common.im.codec.mq.MQBsonCodec;
import com.openim.common.mq.IMessageSender;
import com.openim.common.mq.constants.MQConstants;
import io.netty.channel.Channel;
import io.netty.util.Attribute;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by shihuacai on 2015/7/22.
 */
//@Component
public class ChatHandlerV2 implements INettyMessageHandlerV2 {

    private static final Logger LOG = LoggerFactory.getLogger(ChatHandlerV2.class);

    private static final IMQCodec<ExchangeMessage> mqCodec = new MQBsonCodec();

    private IMessageSender messageSender;

    public ChatHandlerV2() {
        messageSender = ApplicationContextAware.getBean(IMessageSender.class);
    }

    @Override
    public void handle(Channel channel, ExchangeMessage exchangeMessage) {
        if (exchangeMessage.getType() == MessageType.CHAT) {
            try {
                Attribute<String> attribute = channel.attr(ChannelUtil.loginIdKey);

                ProtobufChatMessage.ChatMessage chatMessage = (ProtobufChatMessage.ChatMessage)exchangeMessage.getMessageLite();
                chatMessage = chatMessage.toBuilder().setFrom(attribute.get()).build();
                exchangeMessage.setMessageLite(chatMessage);
                messageSender.sendMessage(MQConstants.openimExchange, MQConstants.chatRouteKey, mqCodec.encode(exchangeMessage));
            }catch (Exception e){
                LOG.error(e.toString());
            }
        } else {
            LOG.error("数据类型错误: " + mqCodec.encode(exchangeMessage));
        }
    }
}
