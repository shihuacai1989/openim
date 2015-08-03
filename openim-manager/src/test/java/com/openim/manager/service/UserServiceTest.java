package com.openim.manager.service;

import com.openim.manager.ManagerApplication;
import com.openim.manager.bean.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

/**
 * Created by shihc on 2015/8/3.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = ManagerApplication.class)
@WebAppConfiguration
@IntegrationTest("server.port:0")
public class UserServiceTest {
    @Autowired
    private IUserService userService;

    @Test
    public void addUser(){
        User user = new User();
        user.setLoginId("shihc");
        user.setPassword("111qqq,,,");
        userService.addUser(user);
    }

    @Test
    public void deleteUser(){
        userService.deleteUser("shihc");
    }

    @Test
    public void userExist(){
        userService.userExist("shihc", "111qqq,,,");
    }

    @Test
    public void getUser(){
        userService.getUser("shihc");
    }
}
