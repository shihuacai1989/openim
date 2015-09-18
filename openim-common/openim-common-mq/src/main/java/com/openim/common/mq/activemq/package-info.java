/**
 * Created by shihc on 2015/9/18.
 * activemq的服务，其不同版本对jdk的要有也不相同，所以有时会出现activemq启动失败的情况，注意查看日志即可
 *
 * queue类型的只能有一个消息消费者.
 * topic类型的可以有多个消息消费者.
 * 每个消费者对应一个MessageListener和一个MessageListenerContainer.
 *
 */
package com.openim.common.mq.activemq;