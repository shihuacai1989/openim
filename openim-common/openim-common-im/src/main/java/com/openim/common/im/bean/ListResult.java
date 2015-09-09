package com.openim.common.im.bean;

import java.util.List;

/**
 * Created by shihuacai on 2015/7/20.
 */
public class ListResult<T> {
    private int code;
    private String error;
    private List<T> data;

    public ListResult(int code, List<T> data) {
        this.code = code;
        this.data = data;
    }

    public ListResult(int code, List<T> data, String error) {
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

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "code: " + code + ", data: " + data + ", error:" + error;
    }
}
