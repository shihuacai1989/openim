package com.openim.common.im.codec.mina;

import com.google.protobuf.CodedInputStream;
import com.google.protobuf.MessageLite;
import com.openim.common.im.bean.ExchangeMessage;
import com.openim.common.im.containter.MessageParserV2;
import io.netty.handler.codec.CorruptedFrameException;
import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.CumulativeProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by shihuacai on 2015/8/20.
 */
public class OpenIMProtobufDecoderV2 extends CumulativeProtocolDecoder {

    private static final Logger LOG = LoggerFactory.getLogger(OpenIMProtobufDecoderV2.class);

    @Override
    protected boolean doDecode(IoSession session, IoBuffer in,
                               ProtocolDecoderOutput out) throws Exception {
        in.mark();
        final byte[] buf = new byte[5];
        for (int i = 0; i < buf.length; i ++) {
            if (!in.hasRemaining()) {
                in.reset();
                return false;
            }

            buf[i] = in.get();
            if (buf[i] >= 0) {
                int length = CodedInputStream.newInstance(buf, 0, i + 1).readRawVarint32();
                if (length < 0) {
                    throw new CorruptedFrameException("negative length: " + length);
                }

                if (in.remaining() < length) {
                    in.reset();
                    return false;
                } else {
                    byte msgType = in.get();
                    byte[] messageBody = new byte[length - 1];
                    in.get(messageBody);

                    MessageLite messageLite = MessageParserV2.parse(msgType, messageBody);
                    if(messageLite == null){
                        LOG.error("msgType:{}不存在", msgType);
                    }else{
                        ExchangeMessage exchangeMessage = new ExchangeMessage();
                        exchangeMessage.setType(msgType);
                        exchangeMessage.setMessageLite(messageLite);
                        out.write(exchangeMessage);
                    }
                    return true;
                }
            }
        }
        return false;


        /*// 如果没有接收完Header部分（4字节），直接返回false
        if (in.remaining() < 4) {
            return false;
        } else {

            // 标记开始位置，如果一条消息没传输完成则返回到这个位置
            in.mark();

            // 读取header部分，获取body长度
            int bodyLength = in.getInt();

            // 如果body没有接收完整，直接返回false
            if (in.remaining() < bodyLength) {
                in.reset(); // IoBuffer position回到原来标记的地方
                return false;
            } else {
                byte[] bodyBytes = new byte[bodyLength];
                in.get(bodyBytes); // 读取body部分
                StudentMsg.Student student = StudentMsg.Student.parseFrom(bodyBytes); // 将body中protobuf字节码转成Student对象
                out.write(student); // 解析出一条消息
                return true;
            }
        }*/
    }
}
