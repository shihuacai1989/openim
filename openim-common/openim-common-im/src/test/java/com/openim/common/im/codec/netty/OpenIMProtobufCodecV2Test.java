package com.openim.common.im.codec.netty;

import com.openim.common.im.bean.ExchangeMessage;
import com.openim.common.im.bean.MessageType;
import com.openim.common.im.bean.protbuf.ProtobufConnectMessage;
import com.openim.common.im.codec.netty.OpenIMProtobufDecoderV2;
import com.openim.common.im.codec.netty.OpenIMProtobufEncoderV2;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import org.junit.Test;

import java.util.ArrayList;

/**
 * Created by shihuacai on 2015/8/20.
 */
public class OpenIMProtobufCodecV2Test {

    @Test
    public void testCodec() throws Exception {
        byte[] encoderData = testEncoder();
        testDecoder(encoderData);
    }


    private byte[] testEncoder() throws Exception {
        //http://www.oschina.net/translate/netty-4-0-new-and-noteworthy

        ByteBuf byteBuf = Unpooled.buffer();

        ProtobufConnectMessage.ConnectMessage connectMessage = ProtobufConnectMessage.ConnectMessage.newBuilder().setLoginId("测试").setPassword("测试").build();

        ExchangeMessage message = new ExchangeMessage();
        message.setType(MessageType.CHAT);
        message.setMessageLite(connectMessage);
        OpenIMProtobufEncoderV2 encoder = new OpenIMProtobufEncoderV2();
        encoder.encode(null, message, byteBuf);

        return byteBuf.array();

    }

    private void testDecoder(byte[] encoderData) throws Exception {
        OpenIMProtobufDecoderV2 decoder = new OpenIMProtobufDecoderV2();
        ByteBuf byteBuf = Unpooled.copiedBuffer(encoderData);
        decoder.decode(null,byteBuf, new ArrayList<Object>());
    }
}
