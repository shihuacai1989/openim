package com.openim.common.mq.codec;

/**
 * Created by shihuacai on 2015/8/26.
 */
public interface IMQCodec<T> {
    String encode(T msg);
    T decode(byte[] bytes);
}
