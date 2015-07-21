package com.openim.common.bean;

import java.util.List;

/**
 * Created by shihuacai on 2015/7/20.
 */
public class ListResult {
    private int code;
    private String error;
    private List<Object> data;

    public ListResult(int code, String error, List<Object> data) {
        this.code = code;
        this.error = error;
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public List<Object> getData() {
        return data;
    }

    public void setData(List<Object> data) {
        this.data = data;
    }
}
