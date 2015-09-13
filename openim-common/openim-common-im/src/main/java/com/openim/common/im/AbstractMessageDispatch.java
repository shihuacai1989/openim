package com.openim.common.im;

import com.openim.common.im.annotation.HandleGroup;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.util.CollectionUtils;

import java.util.Map;

/**
 * Created by shihuacai on 2015/9/5.
 */
public class AbstractMessageDispatch implements ApplicationContextAware {

    protected ApplicationContext applicationContext;


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }


    protected <T> void registerHandler(String filterGroup, Map<Integer, T> handleMap){
        if (CollectionUtils.isEmpty(handleMap)){
            Map<String, Object> map = applicationContext.getBeansWithAnnotation(HandleGroup.class);
            if(!CollectionUtils.isEmpty(map)){
                for(Map.Entry<String, Object> entry : map.entrySet()){
                    HandleGroup handleGroup = entry.getValue().getClass().getAnnotation(HandleGroup.class);

                    String name = handleGroup.name();
                    int type = handleGroup.type();
                    if(name.equals(filterGroup)){
                        handleMap.put(type, (T)entry.getValue());
                    }
                }
            }
        }
    }
}
