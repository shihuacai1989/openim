package com.openim.common.util;

import java.util.UUID;

/**
 * Created by shihc on 2015/3/12.
 */
public class UUIDUtil {
    public static String genericUUID() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }
}
