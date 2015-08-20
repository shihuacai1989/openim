package com.openim.netty.handler.protobuf;

import com.google.protobuf.CodedInputStream;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.CorruptedFrameException;

import java.util.List;

/**
 * Created by shihuacai on 2015/8/20.
 */
public class OpenIMProtobufDecoder extends ByteToMessageDecoder {

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        in.markReaderIndex();
        final byte[] buf = new byte[5];
        for (int i = 0; i < buf.length; i ++) {
            if (!in.isReadable()) {
                in.resetReaderIndex();
                return;
            }

            buf[i] = in.readByte();
            if (buf[i] >= 0) {
                int length = CodedInputStream.newInstance(buf, 0, i + 1).readRawVarint32();
                if (length < 0) {
                    throw new CorruptedFrameException("negative length: " + length);
                }

                if (in.readableBytes() < length) {
                    in.resetReaderIndex();
                    return;
                } else {
                    ByteBuf byteBuf = in.readBytes(length);
                    byte msgType = byteBuf.getByte(0);

                    //CodedInputStream.readRawVarint32();
                    int type = in.readByte();

                    byte[] messageBody = new byte[length - 1];
                    byteBuf.readBytes(messageBody, 1, length - 1);

                    ExchangeMessage exchangeMessage = new ExchangeMessage();
                    exchangeMessage.setType(type);
                    //待实现
                    exchangeMessage.setMessageLite(null);
                    out.add(exchangeMessage);
                    return;
                }
            }
        }

        // Couldn't find the byte whose MSB is off.
        throw new CorruptedFrameException("length wider than 32-bit");
    }
}
