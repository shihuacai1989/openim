package com.openim.analysis.service;

import com.openim.common.im.bean.CommonResult;
import com.openim.common.im.bean.ListResult;

/**
 * Created by shihc on 2015/9/6.
 */
public interface IRelationService {

    static final String LOGIN_ID_FIELD = "loginId";

    /**
     * 获取二度人脉
     * @param loginId
     */
    ListResult<String> getSecondNetwork(String loginId);

    /**
     * 获取三度人脉（只包含三度人脉，不包括一、二度人脉）
     * @return
     */
    ListResult<String> getThirdNetwork(String loginId);

    /**
     * 获取第N度人脉
     * @param loginId
     * @param n
     * @return
     */
    ListResult<String> getNNetwork(String loginId, int n);

    /**
     * 获取指定纬度范围内的人脉
     * @param loginId
     * @param startN
     * @param endN
     * @return
     */
    ListResult<String> getNNetwork(String loginId, int startN, int endN);

    /**
     * 添加用户
     * @param loginId
     * @return
     */
    CommonResult<Boolean> addUser(String loginId);

    /**
     * 删除节点，neo4j限制：必须也要删除其对应的关系
     * @param loginId
     * @return
     */
    CommonResult<Boolean> deleteUser(String loginId);

    /**
     * 为用户增加双向关系
     * @param loginId1
     * @param loginId2
     * @return
     */
    CommonResult<Boolean> addRelation(String loginId1, String loginId2);

    /**
     * 删除用户之间的关系
     * @param loginId1
     * @param loginId2
     * @return
     */
    CommonResult<Boolean> deleteRelation(String loginId1, String loginId2);
}
