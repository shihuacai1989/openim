package com.openim.chatserver.net.mina.v2;

import org.apache.mina.core.service.IoService;
import org.apache.mina.integration.jmx.IoServiceMBean;

import javax.management.*;
import java.lang.management.ManagementFactory;

/**
 * Created by shihuacai on 2015/9/22.
 */
public class JmxService {
    private static MBeanServer mBeanServer = ManagementFactory.getPlatformMBeanServer();

    public static void registerBean(IoService ioService) throws NotCompliantMBeanException, InstanceAlreadyExistsException, MBeanRegistrationException, MalformedObjectNameException {
        IoServiceMBean acceptorMBean = new IoServiceMBean(ioService);

        // create a JMX ObjectName.  This has to be in a specific format.
        ObjectName acceptorName = new ObjectName(ioService.getClass().getPackage().getName() +
                ":type=acceptor,name=" + ioService.getClass().getSimpleName());

        // register the bean on the MBeanServer.  Without this line, no JMX will happen for
        // this acceptor.
        mBeanServer.registerMBean(acceptorMBean, acceptorName);
    }
}
