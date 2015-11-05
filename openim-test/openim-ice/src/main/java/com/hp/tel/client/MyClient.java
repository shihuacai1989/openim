package com.hp.tel.client;

import com.hp.tel.ice.book.Message;
import com.hp.tel.ice.book.OnlineBookPrx;
import com.hp.tel.ice.book.OnlineBookPrxHelper;

/**
 * Created by shihuacai on 2015/11/3.
 */
public class MyClient {
    public static void main(String[] args){
        int status = 0;
        Ice.Communicator ic = null;
        try {
            ic = Ice.Util.initialize(args);
            Ice.ObjectPrx base = ic.stringToProxy("OnlineBook:default -p 10001");

            OnlineBookPrx onlineBook = OnlineBookPrxHelper.checkedCast(base);

            if(onlineBook == null){
                throw new Error("Invalid proxy");
            }
            Message msg = new Message();
            msg.name = "权威指南";
            msg.type = 3;
            msg.price = 99.99;
            msg.valid = true;
            msg.content = "good book";
            System.out.println(onlineBook.bookTick(msg).content);
        }catch (Exception e){
            e.printStackTrace();
            status = 1;
        }finally {
            if(ic != null){
                ic.destroy();
            }
        }
        System.exit(status);
    }
}
