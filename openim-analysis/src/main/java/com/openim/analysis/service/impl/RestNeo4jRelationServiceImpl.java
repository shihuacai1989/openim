package com.openim.analysis.service.impl;

import com.openim.analysis.service.IRelationService;
import com.openim.common.im.bean.CommonResult;
import com.openim.common.im.bean.ListResult;
import com.openim.common.im.bean.ResultCode;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import io.netty.util.CharsetUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;
import sun.misc.BASE64Encoder;

import javax.ws.rs.core.MediaType;
import java.net.URI;

/**
 * Created by shihc on 2015/9/9.<br/>
 */
@Deprecated
public class RestNeo4jRelationServiceImpl implements IRelationService, InitializingBean {

    private static final Logger LOG = LoggerFactory.getLogger(RestNeo4jRelationServiceImpl.class);

    @Value("${neo4j.server.path}")
    private String serverPath;

    @Value("${neo4j.username}")
    private String username;

    @Value("${neo4j.password}")
    private String password;

    private String authCode;

    @Override
    public ListResult<String> getSecondNetwork(String loginId) {
        return null;
    }

    @Override
    public ListResult<String> getThirdNetwork(String loginId) {
        return null;
    }

    @Override
    public ListResult<String> getNNetwork(String loginId, int n) {
        return null;
    }

    @Override
    public ListResult<String> getNNetwork(String loginId, int startN, int endN) {
        return null;
    }

    @Override
    public CommonResult<Boolean> addUser(String loginId) {

        //此种插入方式分两步，先插入空白节点，返回节点id，再根据id，给节点赋属性，如何保证数据完整性
        int code = ResultCode.success;
        String address = serverPath + "node";
        ClientResponse response = request(address, "{}", Method.post);
        if(response.getStatus() == 200){
            final URI location = response.getLocation();
            response.close();
            response = addProperty(location, LOGIN_ID_FIELD, loginId);
            if(response.getStatus() != 200){
                code = response.getStatus();
            }
            response.close();
        } else {
            code = response.getStatus();
        }

        return new CommonResult<Boolean>(code);
    }

    private ClientResponse addProperty(URI nodeUri, String key, String value){
        // http://localhost:7474/db/data/node/{node_id}/properties/{property_name}
        String propertyUri = nodeUri.toString() + "/properties/" + key;

        ClientResponse response = request(propertyUri, "\"" + value + "\"", Method.put);
        return response;
    }

    @Override
    public CommonResult<Boolean> deleteUser(String loginId) {
        return null;
    }

    @Override
    public CommonResult<Boolean> addRelation(String loginId1, String loginId2) {
        return null;
    }

    @Override
    public CommonResult<Boolean> deleteRelation(String loginId1, String loginId2) {
        return null;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        if(StringUtils.isEmpty(serverPath)){
            LOG.error("请配置neo4j服务地址");
            return;
        }else if(!serverPath.trim().endsWith("/")){
            serverPath += "/";
        }

        if(StringUtils.isEmpty(username) || StringUtils.isEmpty(password)){
            LOG.error("请配置neo4j服务用户名和密码");
            return;
        }

        BASE64Encoder base64Encoder = new BASE64Encoder();
        authCode = "Basic " + base64Encoder.encode((username + ":" + password).getBytes(CharsetUtil.UTF_8));

        ClientResponse response = request(serverPath, null, Method.get);
        if(response.getStatus() != 200){
            LOG.error("neo4j服务{}异常，请检查", serverPath);
        }else{
            LOG.error("neo4j服务正常运行中", serverPath);
        }
        response.close();
    }

    private ClientResponse request(String url, String entity, Method method){
        WebResource.Builder builder = Client.create().resource(url).header("Authorization", authCode);
        builder.accept( MediaType.APPLICATION_JSON ).type(MediaType.APPLICATION_JSON);

        if(!StringUtils.isEmpty(entity)){
            builder.entity("\"" + entity + "\"");
        }

        ClientResponse response = null;
        if(method == Method.get){
            response = builder.get( ClientResponse.class );
        }else if(method == Method.put){
            response = builder.put(ClientResponse.class);
        }else if(method == Method.post){
            response = builder.post(ClientResponse.class);
        }
        return response;
    }

    private enum Method{
        put,
        get,
        post
    }
}
