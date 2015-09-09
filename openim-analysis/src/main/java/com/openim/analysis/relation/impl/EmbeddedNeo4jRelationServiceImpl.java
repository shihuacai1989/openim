package com.openim.analysis.relation.impl;

import com.openim.analysis.relation.IRelationService;
import com.openim.common.im.bean.CommonResult;
import com.openim.common.im.bean.ListResult;
import com.openim.common.im.bean.ResultCode;
import org.neo4j.cypher.ExecutionEngine;
import org.neo4j.cypher.ExecutionResult;
import org.neo4j.graphdb.*;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;
import org.neo4j.graphdb.index.Index;
import org.neo4j.graphdb.traversal.Evaluators;
import org.neo4j.graphdb.traversal.TraversalDescription;
import org.neo4j.graphdb.traversal.Traverser;
import org.neo4j.kernel.impl.util.StringLogger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by shihc on 2015/9/6.
 */
@Service
public class EmbeddedNeo4jRelationServiceImpl implements IRelationService, InitializingBean, DisposableBean {

    private static final Logger LOG = LoggerFactory.getLogger(EmbeddedNeo4jRelationServiceImpl.class);

    protected static final String LOGIN_ID_FIELD = "loginId";


    @Value("${neo4j.db.path}")
    private String dbPath;
    @Value("${neo4j.log.path}")
    private String logPath;

    private GraphDatabaseService graphDB;

    private Index<Node> nodeIndex;

    @Override
    public void afterPropertiesSet() throws Exception {
        connectNeo4j();
    }

    private void connectNeo4j() {
        graphDB = new GraphDatabaseFactory().newEmbeddedDatabase(dbPath);
        Transaction tx = graphDB.beginTx();
        try {
            nodeIndex = graphDB.index().forNodes("nodes");
            //Node startNode = graphDB.createNode();
            //startNodeId = startNode.getId();

            tx.success();
        } catch (Exception e) {
            LOG.error(e.toString());
            tx.failure();
        } finally {
            tx.close();
        }
    }

    @Override
    public void destroy() throws Exception {
        graphDB.shutdown();
    }

    @Override
    public ListResult<String> getSecondNetwork(String loginId) {
        return getNNetwork(loginId, 2, 2);

    }

    @Override
    public ListResult<String> getThirdNetwork(String loginId) {
        return getNNetwork(loginId, 3, 3);
    }

    @Override
    public ListResult<String> getNNetwork(String loginId, int n) {
        return getNNetwork(loginId, n, n);
    }

    @Override
    public ListResult<String> getNNetwork(String loginId, int startN, int endN) {
        int code = ResultCode.success;
        List<String> secondNetwork = null;
        Transaction tx = graphDB.beginTx();
        try {
            Node node = nodeIndex.get(LOGIN_ID_FIELD, loginId).getSingle();

            TraversalDescription td = graphDB.traversalDescription()
                    .breadthFirst()
                    .relationships(RelTypes.KNOWS, Direction.OUTGOING)
                    .evaluator(Evaluators.excludeStartPosition())
                            //以下两个限制条件用于查找二度人脉
                    .evaluator(Evaluators.fromDepth(startN))
                    .evaluator(Evaluators.toDepth(endN));

            Traverser traverser = td.traverse(node);

            secondNetwork = new LinkedList<String>();
            for (Path friendPath : traverser) {
                secondNetwork.add((String) friendPath.endNode().getProperty(LOGIN_ID_FIELD));
                /*System.out.println("At depth " + friendPath.length() + " => "
                        + friendPath.endNode().getProperty(LOGIN_ID_FIELD));*/
            }
            tx.success();
        } catch (Exception e) {
            LOG.error(e.toString());
            code = ResultCode.error;
            tx.failure();
        } finally {
            tx.close();
        }

        return new ListResult<String>(code, secondNetwork);
    }

    @Override
    public CommonResult<Boolean> addUser(String loginId) {
        int code = ResultCode.success;
        Transaction tx = graphDB.beginTx();
        try {
            Node trinity = graphDB.createNode();
            trinity.addLabel(DynamicLabel.label("user"));
            trinity.setProperty(LOGIN_ID_FIELD, loginId);
            nodeIndex.add(trinity, LOGIN_ID_FIELD, loginId);
            tx.success();
        }catch (Exception e){
            code = ResultCode.error;
            LOG.error(e.toString());
            tx.failure();
        }finally {
            tx.close();
        }

        return new CommonResult<Boolean>(code);
    }

    @Override
    public CommonResult<Boolean> deleteUser(String loginId) {

        int code = ResultCode.success;
        String error = null;
        try {
            ExecutionEngine engine = new ExecutionEngine(graphDB, StringLogger.logger(new File(logPath)));
            //如果节点无关系，则节点不会被删除
            //如果节点存在关系，则需要连同关系一起删除
            String format = "MATCH (n {%s:'%s'})-[r1]-() DELETE n,r1";
            String cypher = String.format(format, LOGIN_ID_FIELD, loginId);
            ExecutionResult result = engine.execute( cypher );
            System.out.println(result.dumpToString());
        }catch (Exception e){
            code = ResultCode.error;
            error = e.toString();
            LOG.error(error);
        }

        return new CommonResult<Boolean>(code, null, error);
    }

    @Override
    public CommonResult<Boolean> addRelation(String loginId1, String loginId2) {
        int code = ResultCode.success;
        String error = null;
        Transaction tx = graphDB.beginTx();
        try {
            Node node1 = nodeIndex.get(LOGIN_ID_FIELD, loginId1).getSingle();

            Node node2 = nodeIndex.get(LOGIN_ID_FIELD, loginId2).getSingle();
            if(node1 != null && node2 != null){
                Relationship relationship1 = node1.createRelationshipTo(node2, RelTypes.KNOWS);
                relationship1.setProperty(LOGIN_ID_FIELD, loginId1 + "-" + loginId2);
                Relationship relationship2 = node2.createRelationshipTo(node1, RelTypes.KNOWS);
                relationship2.setProperty(LOGIN_ID_FIELD, loginId2 + "-" + loginId1);
            }else{
                code = ResultCode.parameter_error;
                error = "loginId1或loginId2未找到";
            }
            tx.success();
        }catch (Exception e){
            code = ResultCode.error;
            LOG.error(e.toString());
            tx.failure();
        }finally {
            tx.close();
        }

        return new CommonResult<Boolean>(code, null, error);
    }

    @Override
    public CommonResult<Boolean> deleteRelation(String loginId1, String loginId2) {

        int code = ResultCode.success;
        String error = null;
        try {
            ExecutionEngine engine = new ExecutionEngine(graphDB, StringLogger.logger(new File(logPath)));


            /*Map<String, Object> params = new HashMap<String, Object>();
            params.put( "key", LOGIN_ID_FIELD );
            params.put( "value", loginId1 + "-" + loginId2 );
            ExecutionResult result = engine.execute( "MATCH (n)-[r { {key} : {value} }]-() DELETE r", params );*/
            //删除一条关系
            //ExecutionResult result = engine.execute( "MATCH (n)-[r { " + LOGIN_ID_FIELD + " : \"" + loginId1 + "-" + loginId2  + "\" }]-() DELETE r" );
            //删除两条关系
            String rel1Name  = loginId1 + "-" + loginId2;
            String rel2Name  = loginId2 + "-" + loginId1;
            String format = "MATCH (n)-[r]-() where r.%s='%s' or r.%s='%s' DELETE r";
            String cypher = String.format(format, LOGIN_ID_FIELD, rel1Name, LOGIN_ID_FIELD, rel2Name);
            //String cypher = "MATCH (n)-[r]-() where r." + LOGIN_ID_FIELD + "='" + rel1Name + "' or r." + LOGIN_ID_FIELD + "='"+ rel2Name + "' DELETE r";
            ExecutionResult result = engine.execute( cypher );
            System.out.println(result.dumpToString());
        }catch (Exception e){
            code = ResultCode.error;
            error = e.toString();
            LOG.error(error);
        }

        return new CommonResult<Boolean>(code, null, error);
    }

    protected static enum RelTypes implements RelationshipType {
        NEO_NODE,
        KNOWS
    }
}
