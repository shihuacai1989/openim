package com.openim.common.im.codec.mina;

import com.google.protobuf.CodedOutputStream;
import com.google.protobuf.MessageLite;
import com.openim.common.im.bean.ExchangeMessage;
import io.netty.buffer.ByteBufOutputStream;
import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolEncoderAdapter;
import org.apache.mina.filter.codec.ProtocolEncoderOutput;

import java.io.ByteArrayOutputStream;

/**
 * Created by shihc on 2015/9/1.
 */
public class OpenIMProtobufEncoderV2 extends ProtocolEncoderAdapter {

    @Override
    public void encode(IoSession session, Object message, ProtocolEncoderOutput out) throws Exception {
        ExchangeMessage exchangeMessage = (ExchangeMessage)message;
        MessageLite messageLite = exchangeMessage.getMessageLite();
        byte[] msgBody = messageLite.toByteArray();
        int bodyLen = msgBody.length;

        int msgType = exchangeMessage.getType();
        //int typeLen = CodedOutputStream.computeRawVarint32Size(msgType);
        int typeLen = 1;

        int header = typeLen + bodyLen;
        int headerLen = CodedOutputStream.computeRawVarint32Size(header);


        /*ByteArrayOutputStream baos = new ByteArrayOutputStream();
        CodedOutputStream headerOut =
                CodedOutputStream.newInstance(baos, headerLen);
        headerOut.writeRawVarint32(header);
        headerOut.flush();
        baos.write(msgType);
        baos.write(msgBody);

        out.write(IoBuffer.wrap(baos.toByteArray()));*/

        byte[] translateBytes = new byte[headerLen + typeLen + bodyLen];

        CodedOutputStream headerOut = CodedOutputStream.newInstance(translateBytes);
        headerOut.writeRawVarint32(header);
        headerOut.writeRawVarint32(msgType);
        headerOut.writeRawBytes(msgBody);
        headerOut.flush();

        out.write(IoBuffer.wrap(translateBytes));

        /*StudentMsg.Student student = (StudentMsg.Student) message;
        byte[] bytes = student.toByteArray(); // Student对象转为protobuf字节码
        int length = bytes.length;

        IoBuffer buffer = IoBuffer.allocate(length + 4);
        buffer.putInt(length); // write header
        buffer.put(bytes); // write body
        buffer.flip();
        out.write(buffer);*/
    }
}
