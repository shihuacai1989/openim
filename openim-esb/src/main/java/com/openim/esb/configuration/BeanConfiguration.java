package com.openim.esb.configuration;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.openim.common.zk.OpenIMZKClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

/**
 * Created by shihc on 2015/8/4.
 */
@Configuration
public class BeanConfiguration {
    @Value("${zkServers}")
    private String zkServers;

    @Bean
    public MappingJackson2HttpMessageConverter mappingJacksonHttpMessageConverter() {
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        converter.setObjectMapper(new ObjectMapper().setSerializationInclusion(JsonInclude.Include.NON_NULL));
        return converter;
    }

    @Bean
    public OpenIMZKClient imzkClient() {
        OpenIMZKClient imZKClient = new OpenIMZKClient(zkServers, OpenIMZKClient.ClientType.curator);
        return imZKClient;
    }


}
