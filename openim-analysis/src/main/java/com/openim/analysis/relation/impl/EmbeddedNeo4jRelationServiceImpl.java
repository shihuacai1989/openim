package com.openim.analysis.relation.impl;

import com.openim.analysis.relation.IRelationService;
import org.neo4j.graphdb.*;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;
import org.neo4j.graphdb.index.Index;
import org.neo4j.graphdb.traversal.Evaluators;
import org.neo4j.graphdb.traversal.TraversalDescription;
import org.neo4j.graphdb.traversal.Traverser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

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

    private GraphDatabaseService graphDB;

    private Index<Node> nodeIndex;

    @Override
    public void afterPropertiesSet() throws Exception {

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
        } finally {
            tx.close();
        }
    }

    @Override
    public void destroy() throws Exception {
        graphDB.shutdown();
    }

    @Override
    public List<String> getSecondNetwork(String loginId) {
        List<String> secondNetwork = null;
        Transaction tx = graphDB.beginTx();
        try {
            Node node = nodeIndex.get(LOGIN_ID_FIELD, loginId).getSingle();

            TraversalDescription td = graphDB.traversalDescription()
                    .breadthFirst()
                    .relationships(RelTypes.KNOWS, Direction.OUTGOING)
                    .evaluator(Evaluators.excludeStartPosition())
                            //以下两个限制条件用于查找二度人脉
                    .evaluator(Evaluators.fromDepth(2))
                    .evaluator(Evaluators.toDepth(2));

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
        } finally {
            tx.close();
        }

        return secondNetwork;

    }

    protected static enum RelTypes implements RelationshipType {
        NEO_NODE,
        KNOWS
    }
}
