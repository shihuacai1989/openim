package com.openim.analysis.relation;

import java.util.List;

/**
 * Created by shihc on 2015/9/6.
 */
public interface IRelationService {
    /**
     * 获取二度人脉
     * @param loginId
     */
    List<String> getSecondNetwork(String loginId);
}
