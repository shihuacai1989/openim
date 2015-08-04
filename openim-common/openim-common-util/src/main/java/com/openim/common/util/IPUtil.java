package com.openim.common.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Created by shihc on 2015/3/23.
 */
public class IPUtil {

    private static final Logger LOG = LoggerFactory.getLogger(IPUtil.class);

    private static String ip;

    static {
        try {
            ip = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            LOG.error(e.toString());
            ip = null;
        }


    }

    public static String getLocalIP() {
        /*try {
            return InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            LOG.error(e.toString());
            System.exit(-1);
        }*/
        return ip;
    }
}
