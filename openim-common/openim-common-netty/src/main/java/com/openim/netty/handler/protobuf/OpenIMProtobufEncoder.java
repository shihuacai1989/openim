package com.openim.netty.handler.protobuf;

import com.google.protobuf.CodedOutputStream;
import com.google.protobuf.MessageLite;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufOutputStream;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * Created by shihuacai on 2015/8/20.
 */
public class OpenIMProtobufEncoder extends MessageToByteEncoder<ExchangeMessage> {

    @Override
    protected void encode(ChannelHandlerContext ctx, ExchangeMessage msg, ByteBuf out) throws Exception {
        MessageLite messageLite = msg.getMessageLite();
        byte[] msgBody = messageLite.toByteArray();
        int bodyLen = msgBody.length;

        int msgType = msg.getType();
        int typeLen = CodedOutputStream.computeRawVarint32Size(msgType);

        int header = typeLen + bodyLen;
        int headerLen = CodedOutputStream.computeRawVarint32Size(header);

        out.ensureWritable(headerLen + typeLen + bodyLen);

        CodedOutputStream headerOut =
                CodedOutputStream.newInstance(new ByteBufOutputStream(out), headerLen + typeLen);
        headerOut.writeRawVarint32(headerLen);
        headerOut.writeRawVarint32(typeLen);
        headerOut.flush();

        out.writeBytes(msgBody, 0, bodyLen);
    }
}
