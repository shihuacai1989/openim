package com.openim.manager.service;

import com.openim.common.bean.CommonResult;
import com.openim.common.bean.ListResult;
import com.openim.manager.bean.Friend;
import com.openim.manager.bean.Group;
import com.openim.manager.bean.User;

/**
 * Created by shihc on 2015/8/3.
 */
public interface IUserService {
    CommonResult addUser(User user);

    CommonResult<Integer> deleteUser(String loginId);

    CommonResult<Boolean> checkLogin(String loginId, String pwd);

    CommonResult<User> getUser(String loginId);

    CommonResult<Boolean> userExist(String loginId);

    CommonResult<Integer> addGroup(String loginId, String groupName);

    ListResult<Group> listGroups(String loginId);

    CommonResult<Integer> addFriend(String loginId, String friendLoginId, String groupId);

    ListResult<Friend> listFriends(String loginId);

    ListResult<String> listFriendsLoginId(String loginId);

    ListResult<User> getOnlineFriends(String loginId);
}
