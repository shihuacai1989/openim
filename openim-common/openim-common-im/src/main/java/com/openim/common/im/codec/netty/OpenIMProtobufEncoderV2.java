package com.openim.common.im.codec.netty;

import com.google.protobuf.CodedOutputStream;
import com.google.protobuf.MessageLite;
import com.openim.common.im.bean.ExchangeMessage;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufOutputStream;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * Created by shihuacai on 2015/8/20.
 */
//@ChannelHandler.Sharable
public class OpenIMProtobufEncoderV2 extends MessageToByteEncoder<ExchangeMessage> {

    @Override
    public void encode(ChannelHandlerContext ctx, ExchangeMessage msg, ByteBuf out) throws Exception {

        MessageLite messageLite = msg.getMessageLite();
        byte[] msgBody = messageLite.toByteArray();
        int bodyLen = msgBody.length;

        int msgType = msg.getType();
        //int typeLen = CodedOutputStream.computeRawVarint32Size(msgType);
        int typeLen = 1;

        int header = typeLen + bodyLen;
        int headerLen = CodedOutputStream.computeRawVarint32Size(header);

        out.ensureWritable(headerLen + typeLen + bodyLen);
        CodedOutputStream headerOut =
                CodedOutputStream.newInstance(new ByteBufOutputStream(out), headerLen);
        headerOut.writeRawVarint32(header);
        headerOut.flush();
        out.writeByte(msgType);
        out.writeBytes(msgBody, 0, bodyLen);
    }
}
