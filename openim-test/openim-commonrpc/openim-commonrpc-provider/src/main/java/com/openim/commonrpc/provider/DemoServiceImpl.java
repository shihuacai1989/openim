package com.openim.commonrpc.provider;

import com.openim.commonrpc.common.IDemoService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Created by shihuacai on 2015/9/29.
 */
public class DemoServiceImpl implements IDemoService {

    private static final Log LOGGER = LogFactory
            .getLog(DemoServiceImpl.class);

    @Override
    public String sayDemo(String params) {
        // TODO Auto-generated method stub
        //LOGGER.info("sayDemo params:"+params);
        return "from server:"+params;
    }


    @Override
    public String getParam(String params) {
        // TODO Auto-generated method stub
        //LOGGER.info("getParam params:"+params);
        return "from server:"+params;
    }

}
