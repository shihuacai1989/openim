package com.openim.manager.configuration;

import com.openim.common.mq.IMessageDispatch;
import com.openim.common.mq.constants.MQConstants;
import com.openim.manager.cache.login.ILoginCache;
import com.openim.manager.cache.login.LoginMemoryCache;
import com.openim.manager.dispatch.ProtobufMessageDispatch;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by shihc on 2015/7/30.
 */
@Configuration
public class ManagerConfiguration {

    @Bean
    ILoginCache loginMemoryCache(){
        return new LoginMemoryCache();
    }

    @Bean
    IMessageDispatch messageDispatch(){
        return new ProtobufMessageDispatch();
    }

    @Bean
    SimpleMessageListenerContainer rabbitMQListenerContainer(ConnectionFactory connectionFactory, MessageListenerAdapter listenerAdapter) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        /*try {
            //后期改为从zookeeper中获取消息推送服务器列表并实时添加
            String chatServer = PropertiesLoaderUtils.loadAllProperties("application.properties").getProperty("chatServer");
            if(!StringUtils.isEmpty(chatServer)){
                String[] serverArray = chatServer.split(";");
                container.setQueueNames(serverArray);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }*/

        container.setQueueNames(MQConstants.chatQueue, MQConstants.loginQueue, MQConstants.logoutQueue);
        container.setMessageListener(listenerAdapter);
        return container;
    }

    /*public @Bean
    MongoTemplate mongoTemplate(Mongo mongo) {
        MongoTemplate mongoTemplate = new MongoTemplate(mongo, "test");
        return mongoTemplate;
    }

    public @Bean
    MongoClientFactoryBean mongo() {
        MongoClientFactoryBean mongo = new MongoClientFactoryBean();
        mongo.setHost("localhost");
        return mongo;
    }

    public @Bean
    PersistenceExceptionTranslationPostProcessor persistenceExceptionTranslationPostProcessor() {
        return new PersistenceExceptionTranslationPostProcessor();
    }*/
}
