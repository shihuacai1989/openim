package com.openim.spring.schema;

import org.springframework.beans.factory.xml.NamespaceHandlerSupport;

/**
 * Created by shihc on 2015/9/28.
 */
public class ApiRouteNamespaceHandlerSupport extends NamespaceHandlerSupport {
    @Override
    public void init(){
        // 指定根节点“urls”对应的解析器
        registerBeanDefinitionParser("urls",new ApiRouteParser());
    }
}
