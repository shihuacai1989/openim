package com.openim.dubbo.common.service;

import java.util.List;
/**
 * Created by shihc on 2015/9/24.
 */
public interface IDemoService {
    String sayHello(String name);

    public List getUsers();
}
