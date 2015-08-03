package com.openim.common.im.convert;

/**
 * Created by shihuacai on 2015/8/3.
 */
public interface IConverter<S, D> {
    D convert(S s);
}
