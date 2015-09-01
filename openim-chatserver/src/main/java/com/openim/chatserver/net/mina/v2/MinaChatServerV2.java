package com.openim.chatserver.net.mina.v2;

import com.openim.chatserver.net.IChatServer;

/**
 * Created by shihuacai on 2015/8/19.
 */
public class MinaChatServerV2 implements IChatServer {

    @Override
    public void startServer() {

    }

    @Override
    public void afterPropertiesSet() throws Exception {

    }


    /*<bean class="org.springframework.beans.factory.config.CustomEditorConfigurer">
    <property name="customEditors">
    <map>
    <entry key="java.net.SocketAddress" value="org.apache.mina.integration.beans.InetSocketAddressEditor">
    </entry>
    </map>
    </property>
    </bean>


    <bean id="pushIOHandler" class="com.mapbar.push.server.net.ServerIOHander"/>

    <bean id="filterChainBuilder"
    class="org.apache.mina.core.filterchain.DefaultIoFilterChainBuilder">
    <property name="filters">
    <map>
    <entry key="executor">
    <bean class="org.apache.mina.filter.executor.ExecutorFilter"/>
    </entry>
    <entry key="codec">
    <bean class="org.apache.mina.filter.codec.ProtocolCodecFilter">
    <constructor-arg>
    <bean class="org.apache.mina.filter.codec.textline.TextLineCodecFactory "/>
    </constructor-arg>
    </bean>
    </entry>
    <entry key="loggingFilter">
    <bean class="org.apache.mina.filter.logging.LoggingFilter"></bean>
    </entry>
    </map>
    </property>
    </bean>

    <bean id="ioAcceptor" class="org.apache.mina.transport.socket.nio.NioSocketAcceptor"
    init-method="bind" destroy-method="unbind">
    <property name="defaultLocalAddress" value=":5222"/>
    <property name="handler" ref="pushIOHandler"/>
    <property name="filterChainBuilder" ref="filterChainBuilder"/>
    <property name="reuseAddress" value="true"/>
    </bean>

    <bean id="sessionConfig" factory-bean="ioAcceptor"
    factory-method="getSessionConfig" >
    <property name="bothIdleTime" value="${mina.idleTimeout}"/>
    <property name="writeTimeout" value="${writeTimeout}"/>-->
    </bean>*/
}
