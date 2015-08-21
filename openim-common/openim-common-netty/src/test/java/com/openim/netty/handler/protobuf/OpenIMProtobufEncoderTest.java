package com.openim.netty.handler.protobuf;

import org.junit.Test;

/**
 * Created by shihuacai on 2015/8/20.
 */
public class OpenIMProtobufEncoderTest {
    @Test
    public void testEncoder() throws Exception {


        ExchangeMessage message = new ExchangeMessage();
        message.setType(1);
        message.setMessageLite(null);
        OpenIMProtobufEncoder encoder = new OpenIMProtobufEncoder();
        encoder.encode(null, null, null);
    }
}
