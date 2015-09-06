package com.openim.analysis.relation.impl;

import com.openim.analysis.relation.IRelationService;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;
import org.neo4j.graphdb.index.Index;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * Created by shihc on 2015/9/6.
 */
@Service
public class EmbeddedNeo4jRelationServiceImpl implements IRelationService, InitializingBean, DisposableBean {

    @Value("${neo4j.db.path}")
    private String dbPath;

    private GraphDatabaseService graphDB;

    private Index<Node> nodeIndex;

    @Override
    public void afterPropertiesSet() throws Exception {
        graphDB = new GraphDatabaseFactory().newEmbeddedDatabase(dbPath);
    }

    private void connectNeo4j(){

    }

    @Override
    public void destroy() throws Exception {

    }
}
