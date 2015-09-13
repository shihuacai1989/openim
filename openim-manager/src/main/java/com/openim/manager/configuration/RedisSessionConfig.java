package com.openim.manager.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

@Configuration
@EnableRedisHttpSession
public class RedisSessionConfig {
    /*@Bean
    public JedisConnectionFactory connectionFactory()
    {
        JedisConnectionFactory connection = new JedisConnectionFactory();
        connection.setPort(6379);
        connection.setHostName("192.168.1.106");
        return connection;
    }*/
}

