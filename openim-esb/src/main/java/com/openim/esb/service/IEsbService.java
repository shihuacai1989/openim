package com.openim.esb.service;

import com.openim.common.im.bean.CommonResult;
import org.springframework.beans.factory.InitializingBean;

/**
 * Created by shihc on 2015/8/4.
 */
public interface IEsbService extends InitializingBean {

    CommonResult<String> randomInnerServer();

    CommonResult<String> randomOuterServer();
}
