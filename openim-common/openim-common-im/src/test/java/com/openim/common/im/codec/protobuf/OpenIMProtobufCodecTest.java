package com.openim.common.im.codec.protobuf;

import com.openim.common.im.bean.protbuf.ProtobufConnectMessage;
import com.openim.common.im.codec.netty.v2.ExchangeMessage;
import com.openim.common.im.codec.netty.v2.OpenIMProtobufDecoder;
import com.openim.common.im.codec.netty.v2.OpenIMProtobufEncoder;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import org.junit.Test;

import java.util.ArrayList;

/**
 * Created by shihuacai on 2015/8/20.
 */
public class OpenIMProtobufCodecTest {

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
        message.setType(127);
        message.setMessageLite(connectMessage);
        OpenIMProtobufEncoder encoder = new OpenIMProtobufEncoder();
        encoder.encode(null, message, byteBuf);

        return byteBuf.array();

    }

    private void testDecoder(byte[] encoderData) throws Exception {
        OpenIMProtobufDecoder decoder = new OpenIMProtobufDecoder();
        ByteBuf byteBuf = Unpooled.copiedBuffer(encoderData);
        decoder.decode(null,byteBuf, new ArrayList<Object>());
    }
}
