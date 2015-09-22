package com.openim.common.jmx.bean;

/**
 * Created by shihuacai on 2015/9/20.
 */
public interface ChatServerMBean{
    //参考http://www.ibm.com/developerworks/cn/java/j-jtp09196/
    //http://maimode.iteye.com/blog/1354377

    //属性
    void setName(String name);
    String getName();


    //操作
    /**
     * 获取当前信息
     * @return
     */
    String status();
    void start();
    void stop();
}
