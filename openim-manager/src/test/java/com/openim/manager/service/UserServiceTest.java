package com.openim.manager.service;

import com.openim.common.util.UUIDUtil;
import com.openim.manager.ManagerApplication;
import com.openim.manager.bean.Group;
import com.openim.manager.bean.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by shihc on 2015/8/3.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = ManagerApplication.class)
@WebAppConfiguration
//@IntegrationTest("server.port:0")
public class UserServiceTest {
    @Autowired
    private IUserService userService;

    @Test
    public void addUser() {
        User user = new User();
        user.setLoginId("shihc");
        user.setPassword("111qqq,,,");

        Group group = new Group();
        group.setId(UUIDUtil.genericUUID());
        group.setName("分组一");
        group.setCreateTime(new Date());
        List<Group> list = new ArrayList<Group>();
        list.add(group);
        user.setGroups(list);
        userService.addUser(user);
    }

    @Test
    public void deleteUser() {
        userService.deleteUser("shihc");
    }

    @Test
    public void userExist() {
        userService.userExist("shihc", "111qqq,,,");
    }

    @Test
    public void getUser() {
        userService.getUser("shihc");
    }

    @Test
    public void addGroup() {
        userService.addGroup("shihc", "新分组");
    }

    @Test
    public void listGroups() {
        userService.listGroups("shihc");
    }

    @Test
    public void addFriend() {
        userService.addFriend("shihc", "wll", "group1");
    }

    @Test
    public void listFriends() {
        userService.listFriends("shihc");
    }
}
