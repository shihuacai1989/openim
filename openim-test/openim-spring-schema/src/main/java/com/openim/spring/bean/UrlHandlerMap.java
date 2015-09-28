package com.openim.spring.bean;

import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by shihc on 2015/9/28.
 */
public class UrlHandlerMap {
    private Map<String,UrlHandler> urlHandlerMapper = new HashMap<String, UrlHandler>();
    private PathMatcher matcher = new AntPathMatcher();

    public void setUrlHandlerMapper(Map<String,UrlHandler> urlHandlerMapper) {
        this.urlHandlerMapper = urlHandlerMapper;
    }

    public UrlHandler getUrlHandler(String url){
        // 先根据url直接查找handler，如果可以查找到则直接返回
        UrlHandler urlHandler = this.urlHandlerMapper.get(url);
        if (urlHandler != null){
            return urlHandler;
        }

        // 直接根据url查找不到，再根据正则匹配
        Set<String> patternSet = this.urlHandlerMapper.keySet();
        for (String pattern : patternSet){
            if (matcher.match(pattern,url)){
                return this.urlHandlerMapper.get(pattern);
            }
        }
        return null;
    }
}
