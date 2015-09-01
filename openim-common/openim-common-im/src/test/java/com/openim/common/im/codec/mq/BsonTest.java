package com.openim.common.im.codec.mq;

import com.openim.common.im.bean.ExchangeMessage;
import com.openim.common.im.bean.MessageType;
import com.openim.common.im.bean.protbuf.ProtobufChatMessage;
import org.junit.Test;

/**
 * Created by shihuacai on 2015/8/24.
 */
public class BsonTest {

    @Test
    public void mongoBsonTest(){
        ProtobufChatMessage.ChatMessage chatMessage = ProtobufChatMessage.ChatMessage.newBuilder().setTo("接受者").setMsg("消息体").build();
        ExchangeMessage exchangeMessage = new ExchangeMessage();
        exchangeMessage.setType(MessageType.CHAT);
        exchangeMessage.setMessageLite(chatMessage);

        byte[] encodeData = MQBsonCodecUtilV2.encode(exchangeMessage);


        ExchangeMessage exchangeMessage1 = MQBsonCodecUtilV2.decode(encodeData);
        System.out.println(exchangeMessage1);

    }

    @Test
    public void bson4jacksonTest() {
        /*try {
            //create dummy POJO
            ProtobufChatMessage.ChatMessage chatMessage = ProtobufChatMessage.ChatMessage.newBuilder()
                    .setFrom("测试A").setMsg("消息").build();

            ExchangeMessage exchangeMessage = new ExchangeMessage();
            exchangeMessage.setMessageLite(chatMessage);
            exchangeMessage.setType(DeviceMsgType.CHAT);
            //serialize data
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectMapper mapper = new ObjectMapper(new BsonFactory());
            mapper.writeValue(baos, exchangeMessage);
            //deserialize data
            ByteArrayInputStream bais = new ByteArrayInputStream(
                    baos.toByteArray());
            ExchangeMessage clone_of_bob = mapper.readValue(bais, ExchangeMessage.class);
            System.out.println(clone_of_bob.toString());
            //assert exchangeMessage.getType().getName().equals(clone_of_bob.getName());
        }catch (Exception e){
            e.printStackTrace();
        }*/

    }
}
