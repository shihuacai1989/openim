package com.openim.common.im.codec.mq;

import com.google.protobuf.MessageLite;
import com.openim.common.im.bean.ExchangeMessage;
import com.openim.common.im.containter.MessageParserV2;
import org.bson.BSONObject;
import org.bson.BasicBSONDecoder;
import org.bson.BasicBSONEncoder;
import org.bson.BasicBSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by shihuacai on 2015/8/26.
 */
public class MQBsonCodec implements IMQCodec<ExchangeMessage> {

    private static final Logger LOG = LoggerFactory.getLogger(MQBsonCodec.class);

    @Override
    public byte[] encode(ExchangeMessage msg) {
        if(msg != null){
            /*BasicDBObject obj = new BasicDBObject();
            obj.put("type", msg.getType());
            obj.put("message", msg.getMessageLite().toByteArray());
            return obj.toString();*/

            BasicBSONObject bsonObject = new BasicBSONObject();
            bsonObject.put("type", msg.getType());
            bsonObject.put("message", msg.getMessageLite().toByteArray());

            BasicBSONEncoder encoder = new BasicBSONEncoder();
            return encoder.encode(bsonObject);
        }
        return null;
    }

    @Override
    public ExchangeMessage decode(byte[] bytes) {
        try {
            BasicBSONDecoder decoder = new BasicBSONDecoder();
            BSONObject bsonObject = decoder.readObject(bytes);
            int type = Integer.valueOf(String.valueOf(bsonObject.get("type")));
            byte[] messageBytes = (byte[])bsonObject.get("message");

            ExchangeMessage exchangeMessage = new ExchangeMessage();
            exchangeMessage.setType(type);
            MessageLite messageLite = MessageParserV2.parse(type, messageBytes);
            exchangeMessage.setMessageLite(messageLite);
            return exchangeMessage;



            /*BSONObject bsonObject = BSON.decode(bytes);
            int type = Integer.valueOf(String.valueOf(bsonObject.get("type")));
            byte[] messageBytes = (byte[])bsonObject.get("message");

            ExchangeMessage exchangeMessage = new ExchangeMessage();
            exchangeMessage.setType(type);
            MessageLite messageLite = MessageParserV2.parse(type, messageBytes);
            exchangeMessage.setMessageLite(messageLite);
            return exchangeMessage;*/
        }catch (Exception e){
            LOG.error(e.toString());
        }
        return null;
    }
}
