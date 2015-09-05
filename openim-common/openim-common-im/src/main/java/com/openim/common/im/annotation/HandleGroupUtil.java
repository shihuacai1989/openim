package com.openim.common.im.annotation;

import org.springframework.context.ApplicationContext;
import org.springframework.util.CollectionUtils;

import java.util.Map;

/**
 * Created by shihuacai on 2015/9/5.
 */
public class HandleGroupUtil {
    public static <T> void add(ApplicationContext context, String filterGroup, Map<Integer, T> handleMap){
        if (CollectionUtils.isEmpty(handleMap)){
            Map<String, Object> map = context.getBeansWithAnnotation(HandleGroup.class);
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
