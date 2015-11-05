package com.hp.tel.service.impl;

import Ice.Communicator;
import Ice.Current;
import Ice.Logger;
import Ice.ObjectAdapter;
import IceBox.Service;
import com.hp.tel.ice.book.Message;
import com.hp.tel.ice.book._OnlineBookDisp;

/**
 * Created by shihuacai on 2015/10/31.
 */
public class OnlineBookI2 extends _OnlineBookDisp implements Service {
    private Logger logger;
    //private Logger logger = LoggerFactory.getLogger(OnlineBookI2.class);
    private ObjectAdapter _adapter;


    /**
     * IceBox.Service.OnlineBook=com.hp.tel.service.impl.OnlineBookI2 prop1=1 prop2=2 prop3=3
     * 参数name=OnlineBook, strings = prop1=1 prop2=2 prop3=3
     */
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
    public Message bookTick(Message msg, Current __current) {
        logger.print("bookTick to call . " + logger.getClass().getName());

        logger.print("bookTick called " + msg.content);
        return msg;
    }
}
