import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Output;
import com.google.protobuf.ByteString;
import com.openim.common.im.bean.AvroDeviceMsg;
import com.openim.common.im.bean.DeviceMsg;
import com.openim.common.im.bean.ProtobufDeviceMsg;
import com.openim.common.im.bean.ThriftDeviceMsg;
import org.apache.avro.io.DatumWriter;
import org.apache.avro.io.Encoder;
import org.apache.avro.io.EncoderFactory;
import org.apache.avro.specific.SpecificDatumWriter;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.transport.TIOStreamTransport;
import org.junit.Test;
import org.msgpack.MessagePack;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;

/**
 * Created by shihuacai on 2015/8/2.
 */
public class SerializeTest {

    private static final String msg = "测试内容";
    private static final int type = 1;

    @Test
    public void protobufTest() {
        /*ProtobufChatMessage.ChatMessage.Builder builder = ProtobufChatMessage.ChatMessage.newBuilder();
        builder.set*/
        // 经测试，如果增加DeviceMsg的属性，赋予相同的值，其序列化后的大小保持不变，
        // 序列化后的大小与类的字段多少无关，
        // 如果某些字段不赋值，则该字段不会序列化？？？
        ProtobufDeviceMsg.DeviceMsg.Builder builder = ProtobufDeviceMsg.DeviceMsg.newBuilder();
        builder.setType(type);
        builder.setMsg(msg);
        ProtobufDeviceMsg.DeviceMsg deviceMsg = builder.build();
        try {
            FileOutputStream fsout = new FileOutputStream(new File("D:\\protobuf.data"));
            deviceMsg.writeTo(fsout);
            fsout.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        //System.out.println(deviceMsg.toString());
        //输出16
        ByteString byteString = deviceMsg.toByteString();

        System.out.println("protobuf序列化后大小:" + deviceMsg.toByteArray().length);
    }

    @Test
    public void jdkTest() {
        // 经测试，如果增加DeviceMsg的属性，赋予相同的值，其序列化后的内容变大
        // 故序列化后的大小与类的字段多少相关
        // 如果某些字段不赋值，该字段仍然会在序列化对象中
        DeviceMsg deviceMsg = new DeviceMsg();
        deviceMsg.setType(type);
        deviceMsg.setMsg(msg);
        try {
            FileOutputStream fos = new FileOutputStream("D:\\jdkSerial.data");
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(deviceMsg);
            oos.flush();
            oos.close();
            File file = new File("D:\\jdkSerial.data");
            long length = file.length();
            //输出181
            System.out.println("jdk序列化后大小: " + length);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void kyroTest(){
        Kryo kryo = new Kryo();
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        Output output = new Output(bos);
        DeviceMsg deviceMsg = new DeviceMsg();
        deviceMsg.setType(type);
        deviceMsg.setMsg(msg);
        kryo.writeObject(output, deviceMsg);
        output.close();

        byte[] bytes = bos.toByteArray();
        //String str = new String(bos.toByteArray());
        //输出21
        System.out.println("kyro序列化后大小: " + bytes.length);

        /*Input input = new Input(new ByteArrayInputStream(bytes));
        DeviceMsg someObject = kryo.readObject(input, DeviceMsg.class);
        input.close();*/
    }

    @Test
    public void avroTest(){
        try {
            AvroDeviceMsg deviceMsg = new AvroDeviceMsg();
            deviceMsg.setType(type);
            deviceMsg.setMsg(msg);
            ByteArrayOutputStream out=new ByteArrayOutputStream();
            //不再需要传schema了，直接用StringPair作为范型和参数，
            DatumWriter<AvroDeviceMsg> writer=new SpecificDatumWriter<AvroDeviceMsg>(AvroDeviceMsg.class);
            Encoder encoder= EncoderFactory.get().binaryEncoder(out,null);
            writer.write(deviceMsg, encoder);
            encoder.flush();
            out.close();
            //输出21
            System.out.println("avro序列化后大小: " + out.toByteArray().length);
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    @Test
    public void messagePackTest(){
        try {
            DeviceMsg deviceMsg = new DeviceMsg();
            deviceMsg.setType(type);
            deviceMsg.setMsg(msg);

            MessagePack msgpack = new MessagePack();
            msgpack.register(DeviceMsg.class);
            byte[] raw = msgpack.write(deviceMsg);
            //输出20
            System.out.println("messagePack序列化后大小: " + raw.length);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Test
    public void thriftTest(){
        try {
            ThriftDeviceMsg thriftDeviceMsg = new ThriftDeviceMsg();
            thriftDeviceMsg.setType(type);
            thriftDeviceMsg.setMsg(msg);

            ByteArrayOutputStream baos = new ByteArrayOutputStream();

            thriftDeviceMsg.write(new TBinaryProtocol(new TIOStreamTransport(baos)));

            baos.close();
            //输出27
            System.out.println("thrift序列化后大小: " + baos.toByteArray().length);

            //采用TCompactProtocol，输出17

        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
