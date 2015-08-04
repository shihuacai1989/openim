package com.openim.common.zk;

import com.openim.common.zk.bean.Node;

import java.util.List;

/**
 * Created by shihc on 2015/8/4.
 */
public interface ChatServerNodeChangedListener {
    void onChanged(List<Node> data, boolean success);
}
