package com.openim.common.bean;

/**
 * Created by shihuacai on 2015/7/20.
 */
public class CommonResult<T> {
    private int code;
    private String error;
    private T data;

    public CommonResult(int code, T data, String error) {
        this.code = code;
        this.error = error;
        this.data = data;
    }

    public CommonResult(int code, T data) {
        this.code = code;
        this.data = data;
    }

    public CommonResult(int code) {
        this.code = code;
        this.data = null;
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

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        String data = String.format("code:%s, error:%s", code, error);
        return data;
    }
}
