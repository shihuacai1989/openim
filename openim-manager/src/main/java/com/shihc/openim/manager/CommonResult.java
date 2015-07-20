package com.shihc.openim.manager;

/**
 * Created by shihuacai on 2015/7/20.
 */
public class CommonResult {
    private int code;
    private String error;
    private Object data;

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

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
