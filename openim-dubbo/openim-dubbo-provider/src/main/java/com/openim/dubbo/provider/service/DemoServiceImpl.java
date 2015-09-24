package com.openim.dubbo.provider.service;

import com.openim.dubbo.common.service.IDemoService;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shihc on 2015/9/24.
 */
public class DemoServiceImpl implements IDemoService {
    @Override
    public String sayHello(String name) {
        return "hello " + name;
    }

    @Override
    public List getUsers() {
        List list = new ArrayList();
        list.add("xx");
        list.add("测试");
        return list;
    }
}
