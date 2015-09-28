package com.openim.spring.schema;

import com.openim.spring.bean.UrlHandler;
import com.openim.spring.bean.UrlHandlerMap;
import org.springframework.beans.BeanMetadataElement;
import org.springframework.beans.factory.config.RuntimeBeanReference;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.ManagedList;
import org.springframework.beans.factory.support.ManagedMap;
import org.springframework.beans.factory.xml.AbstractSingleBeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.springframework.util.StringUtils;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.lang.reflect.Field;
import java.util.List;

/**
 * Created by shihc on 2015/9/28.
 */
public class ApiRouteParser extends AbstractSingleBeanDefinitionParser {
    private static String DEFAULT_BEFORE_HANDLER = "defaultBeforeHandler";
    private static String DEFAULT_AFTER_HANDLER = "defaultAfterHandler";
    private static String DEFAULT_EXECUTE_HANDLER = "defaultExecuteHandler";
    private static String DEFAULT_EXECUTE_PACKAGE = "com.openim.spring.handler.impl";

    private static String DOMAIN_PROPERTIES_BEAN = "domainProperties";

    private static String ROUTE_URL_FIELD = "route:url";
    private static String PATTERN_FIELD = "pattern";
    private static String BEFORE_HANDLER_FIELD = "beforeHandler";
    private static String AFTER_HANDLER_FIELD = "afterHandler";
    private static String EXECUTE_HANDLER_FIELD = "executeHandler";

    @Override
    protected Class getBeanClass(Element element) {
        return UrlHandlerMap.class;
    }

    @Override
    protected void doParse(Element element,ParserContext parserContext, BeanDefinitionBuilder builder) {
        NodeList urlList = element.getElementsByTagName(ROUTE_URL_FIELD);
        ManagedMap<String, BeanMetadataElement> urlHandlerMapperDefinition = new ManagedMap<String, BeanMetadataElement>();
        for (int i=0; i < urlList.getLength(); i++){
            Element urlNode = (Element)urlList.item(i);
            // 从xml配置中读取属性
            String pattern = urlNode.getAttribute(PATTERN_FIELD);
            String beforeHandlers = urlNode.getAttribute(BEFORE_HANDLER_FIELD);
            String afterHandlers = urlNode.getAttribute(AFTER_HANDLER_FIELD);
            String executeHandler = urlNode.getAttribute(EXECUTE_HANDLER_FIELD);

            // 设置默认值
            if (StringUtils.isEmpty(beforeHandlers)){
                beforeHandlers = DEFAULT_BEFORE_HANDLER;
            }
            if (StringUtils.isEmpty(afterHandlers)){
                afterHandlers = DEFAULT_AFTER_HANDLER;
            }
            if (StringUtils.isEmpty(executeHandler)){
                executeHandler = DEFAULT_EXECUTE_HANDLER;
            }
            // 首字母变大写，方便使用Class.forName加载类
            executeHandler = StringUtils.capitalize(executeHandler);
            // 装配UrlHandler类
            BeanDefinitionBuilder urlHandlerBuilder = BeanDefinitionBuilder.genericBeanDefinition(UrlHandler.class);
            BeanDefinitionBuilder executeHandlerBuilder = null;
            Class executeHandlerClass = null;
            try {
                executeHandlerClass = Class.forName(DEFAULT_EXECUTE_PACKAGE + "." + executeHandler);
                executeHandlerBuilder = BeanDefinitionBuilder.genericBeanDefinition(executeHandlerClass);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException("找不到名为 " + DEFAULT_EXECUTE_PACKAGE + "." + executeHandler + " 的类！！！");
            }
            setProperty(executeHandlerBuilder, executeHandlerClass, DOMAIN_PROPERTIES_BEAN, new RuntimeBeanReference(DOMAIN_PROPERTIES_BEAN));
            // 装配UrlHandler类中用到的列表
            urlHandlerBuilder.addPropertyValue("beforeHandlerList", parseList(beforeHandlers));
            urlHandlerBuilder.addPropertyValue("afterHandlerList", parseList(afterHandlers));
            urlHandlerBuilder.addPropertyValue("executeHandler", executeHandlerBuilder.getBeanDefinition());
            // 装配map
            urlHandlerMapperDefinition.put(pattern, urlHandlerBuilder.getBeanDefinition());
        }
        builder.addPropertyValue("urlHandlerMapper", urlHandlerMapperDefinition);
    }

    /**
     * 如果clazz有fieldName属性，就设置，否则什么都不做
     * @param executeHandlerBuilder
     * @param clazz
     * @param fieldName 属性名
     * @param value 属性值
     */
    private void setProperty(BeanDefinitionBuilder executeHandlerBuilder, Class clazz, String fieldName, Object value){
        try {
            Field field = clazz.getDeclaredField(fieldName);
            executeHandlerBuilder.addPropertyValue(fieldName, value);
        } catch (NoSuchFieldException e) {
            // do nothing
        }
    }

    /**
     * 将xml中指定的handler列表（逗号分隔），解析为list
     * @param handlers
     * @return
     */
    private List<BeanMetadataElement> parseList(String handlers){
        List<BeanMetadataElement> definitionList = new ManagedList<BeanMetadataElement>();
        String[] handlerArray = handlers.split(",");
        for (String handler : handlerArray){
            // 根据xml里配置的handler名字设置bean引用
            definitionList.add(new RuntimeBeanReference(handler));
        }
        return definitionList;
    }
}
