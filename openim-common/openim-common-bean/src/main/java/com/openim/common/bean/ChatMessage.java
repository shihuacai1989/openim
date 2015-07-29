package com.openim.common.bean;

import java.io.Serializable;

/**
 * Created by shihuacai on 2015/7/29.
 */
public class ChatMessage implements Serializable {
    private String body;
    private String from;
    private String to;
    private long index;
    private long time;

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public long getIndex() {
        return index;
    }

    public void setIndex(long index) {
        this.index = index;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }
}
