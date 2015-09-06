package com.openim.common.mq;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Created by shihuacai on 2015/7/28.
 */
@Component
public class TestMessageDispatch extends MessageQueueDispatch {
    private static final Logger LOG = LoggerFactory.getLogger(TestMessageDispatch.class);

    @Override
    public void dispatchMessage(String exchange, String routeKey, byte[] message) {
        //LOG.info("exchange:{}, routeKey:{}, message:{}", exchange, routeKey, message);
        //System.out.println(message);
    }
}
