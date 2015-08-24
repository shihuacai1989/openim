package com.openim.common.im.codec.protobuf;

import org.junit.Test;

/**
 * Created by shihuacai on 2015/8/24.
 */
public class BsonTest {

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
