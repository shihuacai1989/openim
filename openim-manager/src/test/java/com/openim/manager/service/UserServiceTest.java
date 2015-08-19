package com.openim.manager.service;

import com.openim.common.im.bean.LoginStatus;
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
    public void deleteAll(){

    }

    @Test
    public void addUser() {
        User user = new User();
        user.setLoginId("shihc");
        user.setPassword("shihc");
        user.setRegisterTime(new Date());
        user.setLastReadMsgTime(new Date());
        Group group = new Group();
        group.setId(UUIDUtil.genericUUID());
        group.setName("分组一");
        group.setCreateTime(new Date());
        List<Group> list = new ArrayList<Group>();
        list.add(group);
        user.setGroups(list);
        userService.addUser(user);


        User wll = new User();
        wll.setLoginId("wll");
        wll.setPassword("wll");
        wll.setLoginStatus(LoginStatus.online);
        wll.setRegisterTime(new Date());
        wll.setLastReadMsgTime(new Date());
        userService.addUser(wll);

        User xml = new User();
        xml.setLoginId("xml");
        xml.setPassword("xml");
        xml.setLoginStatus(LoginStatus.offline);
        xml.setRegisterTime(new Date());
        xml.setLastReadMsgTime(new Date());
        userService.addUser(xml);

        User gym = new User();
        gym.setLoginId("gym");
        gym.setPassword("gym");
        gym.setLoginStatus(LoginStatus.online);
        gym.setRegisterTime(new Date());
        gym.setLastReadMsgTime(new Date());
        userService.addUser(gym);

        User lkh = new User();
        lkh.setLoginId("lkh");
        lkh.setPassword("lkh");
        lkh.setLoginStatus(LoginStatus.online);
        lkh.setRegisterTime(new Date());
        lkh.setLastReadMsgTime(new Date());
        userService.addUser(lkh);


    }

    @Test
    public void deleteUser() {
        userService.deleteUser("shihc");
    }

    @Test
    public void checkLogin() {
        userService.checkLogin("shihc", "shihc");
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
        userService.addFriend("shihc", "xml", "group1");
        userService.addFriend("shihc", "gym", "group1");
        userService.addFriend("shihc", "lkh", "group2");
    }

    @Test
    public void listFriends() {
        userService.listFriends("shihc");
    }

    @Test
    public void listFriendsLoginId() {
        userService.listFriendsLoginId("shihc");
    }

    @Test
    public void getOnlineFriends() {
        userService.getOnlineFriends("shihc");
    }
}
