import com.google.protobuf.DescriptorProtos;
import com.google.protobuf.Descriptors;
import com.google.protobuf.DynamicMessage;
import com.google.protobuf.InvalidProtocolBufferException;
import com.openim.common.im.bean.ProtobufChatMessage;
import com.openim.common.im.bean.ProtobufOnLineMessage;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by shihc on 2015/8/20.
 */
public class ProtobufTest {

    private Map<String, Descriptors.Descriptor> messageMap;

    @Before
    public void init(){
        messageMap = new HashMap<String, Descriptors.Descriptor>();
        Descriptors.Descriptor chatDescriptor = ProtobufChatMessage.ChatMessage.getDescriptor();
        String chatName = chatDescriptor.getName();

        Descriptors.Descriptor onLineDescriptor = ProtobufOnLineMessage.OnLineMessage.getDescriptor();
        String onLineName = onLineDescriptor.getName();

        messageMap.put(chatName, chatDescriptor);
        messageMap.put(onLineName, onLineDescriptor);

        System.out.println(messageMap);


        Descriptors.FileDescriptor file = ProtobufChatMessage.getDescriptor();
        String name = "ChatMessage";
        Descriptors.Descriptor type = file.findMessageTypeByName(name);
        System.out.println(type);
        DynamicMessage msg = DynamicMessage.getDefaultInstance(type);
        System.out.print(msg);
    }

    @Test
    public void dynamicTest(){

        /*ProtobufChatMessage.ChatMessage.Builder chatMessage = ProtobufChatMessage.ChatMessage.newBuilder();
        chatMessage.setFrom("aa");
        chatMessage.setTo("bb");
        chatMessage.setMsg("消息体");

        byte[] bytes = chatMessage.build().toByteArray();
        try {
            DynamicMessage dynamicMessage = DynamicMessage.parseFrom(messageMap.get("ChatMessage"), bytes);
            Object o = dynamicMessage.newBuilderForType();
            System.out.print(o);
        } catch (InvalidProtocolBufferException e) {
            e.printStackTrace();
        }*/
    }
}
