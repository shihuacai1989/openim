package com.openim.spring.bean;

import com.openim.spring.handler.AfterHandler;
import com.openim.spring.handler.BeforeHandler;
import com.openim.spring.handler.ExecuteHandler;

import java.util.List;

/**
 * Created by shihc on 2015/9/28.
 */
public class UrlHandler {
    private List<BeforeHandler> beforeHandlerList;
    private List<AfterHandler> afterHandlerList;
    private ExecuteHandler executeHandler;


    public List<BeforeHandler> getBeforeHandlerList() {
        return beforeHandlerList;
    }

    public void setBeforeHandlerList(List<BeforeHandler> beforeHandlerList) {
        this.beforeHandlerList = beforeHandlerList;
    }

    public List<AfterHandler> getAfterHandlerList() {
        return afterHandlerList;
    }

    public void setAfterHandlerList(List<AfterHandler> afterHandlerList) {
        this.afterHandlerList = afterHandlerList;
    }

    public ExecuteHandler getExecuteHandler() {
        return executeHandler;
    }

    public void setExecuteHandler(ExecuteHandler executeHandler) {
        this.executeHandler = executeHandler;
    }
}
