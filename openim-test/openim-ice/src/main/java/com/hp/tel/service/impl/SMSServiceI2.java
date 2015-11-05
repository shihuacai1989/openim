package com.hp.tel.service.impl;

import Ice.Communicator;
import Ice.Current;
import Ice.ObjectAdapter;
import IceBox.Service;
import com.hp.tel.ice.message._SMSServiceDisp;

/**
 * Created by shihuacai on 2015/10/31.
 */
public class SMSServiceI2 extends _SMSServiceDisp implements Service {
    private Ice.Logger logger;
    //private Logger logger = LoggerFactory.getLogger(SMSServiceI2.class);
    private ObjectAdapter _adapter;



    @Override
    public void start(String name, Communicator communicator, String[] strings) {
        logger = communicator.getLogger().cloneWithPrefix(name);

        //创建objectAdapter，这里和service同名
        _adapter = communicator.createObjectAdapter(name);
        //创建servant并激活
        Ice.Object object = this;
        _adapter.add(this, communicator.stringToIdentity(name));
        _adapter.activate();
        logger.print(name + " started");
    }

    @Override
    public void stop() {
        logger.trace(this._adapter.getName(), " stopped");
        //销毁adaptor
        _adapter.destroy();
    }

    @Override
    public void sendSMS(String msg, Current __current) {
        System.out.println("send msg " + msg);
        //_adapter.getCommunicator().stringToIdentity("OnlineBook")
    }
}
