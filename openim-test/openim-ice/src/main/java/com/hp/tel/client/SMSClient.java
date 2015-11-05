package com.hp.tel.client;

import com.hp.tel.ice.message.SMSServicePrx;
import com.hp.tel.ice.message.SMSServicePrxHelper;

/**
 * Created by shihuacai on 2015/11/3.
 */
public class SMSClient {
    public static void main(String[] args) {
        int status = 0;
        Ice.Communicator ic = null;
        try {
            ic = Ice.Util.initialize(args);
            Ice.ObjectPrx base = ic.stringToProxy("SMSService:default -p 10002");

            SMSServicePrx smsService = SMSServicePrxHelper.checkedCast(base);

            if (smsService == null) {
                throw new Error("Invalid proxy");
            }
            smsService.sendSMS("xxxxxxxxx");
        } catch (Exception e) {
            e.printStackTrace();
            status = 1;
        } finally {
            if (ic != null) {
                ic.destroy();
            }
        }
        System.exit(status);
    }
}
