package com.openim.chatserver.jmx.configuration;

import com.openim.chatserver.jmx.bean.IJmxTestBean;
import com.openim.chatserver.jmx.bean.JmxTestBean;
import com.openim.chatserver.net.NetMessageDispatch;
import com.openim.chatserver.net.mina.v2.MinaMessageDispatchV2;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jmx.export.MBeanExporter;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by shihc on 2015/9/23.
 */
@Configuration
public class JmxConfiguration {
    @Bean
    MBeanExporter mBeanExporter(){

        MBeanExporter exporter = new MBeanExporter();

        Map<String, Object> map = new HashMap<>();
        map.put("bean:name=testBean1", jmxTestBean());
        exporter.setBeans(map);
        return exporter;
    }

    @Bean
    IJmxTestBean jmxTestBean(){
        return new JmxTestBean();
    }

}
