import com.openim.common.im.bean.DeviceMsg;
import com.openim.common.im.bean.ProtobufDeviceMsg;
import org.junit.Test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;

/**
 * Created by shihuacai on 2015/8/2.
 */
public class SerializeTest {
    @Test
    public void protobufTest() {
        // 经测试，如果增加DeviceMsg的属性，赋予相同的值，其序列化后的大小保持不变，
        // 序列化后的大小与类的字段多少无关，
        // 如果某些字段不赋值，则该字段不会序列化？？？
        ProtobufDeviceMsg.DeviceMsg.Builder builder = ProtobufDeviceMsg.DeviceMsg.newBuilder();
        builder.setType(1);
        builder.setMsg("测试内容");
        ProtobufDeviceMsg.DeviceMsg deviceMsg = builder.build();
        try {
            FileOutputStream fsout = new FileOutputStream(new File("D:\\protobuf.data"));
            deviceMsg.writeTo(fsout);
            fsout.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println(deviceMsg.toString());
        //输出16
        System.out.println("protobuf序列化后大小:" + deviceMsg.toByteArray().length);
    }

    @Test
    public void jdkTest() {
        // 经测试，如果增加DeviceMsg的属性，赋予相同的值，其序列化后的内容变大
        // 故序列化后的大小与类的字段多少相关
        // 如果某些字段不赋值，该字段仍然会在序列化对象中
        DeviceMsg deviceMsg = new DeviceMsg();
        deviceMsg.setType(1);
        deviceMsg.setMsg("测试内容");
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
}
