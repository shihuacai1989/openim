package com.openim.common.zk.bean;

import com.openim.common.util.CharsetUtil;

/**
 * Created by shihc on 2015/8/4.
 */
public class Node {
    public Node(String path, byte[] data) {
        this.path = path;
        this.data = data;
    }

    private String path;
    private byte[] data;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return String.format("path:%s, data:%s", path, new String(data, CharsetUtil.utf8));
    }
}
