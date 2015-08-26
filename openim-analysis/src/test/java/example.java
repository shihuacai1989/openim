public class Example {
	
	/*private static final String DB_PATH = "target/neo4jexample";
	private static final String PRIMARY_KEY = "name";
	private GraphDatabaseService graphDB;
	private Index<Node> nodeIndex;
	private long startNodeId;
	
	private static enum RelTypes implements RelationshipType {
		NEO_NODE,
		KNOWS,
		CODED_BY
	}
	
	private void clearDB() {
		try {
			FileUtils.deleteRecursively(new File(DB_PATH));
		}
		catch(IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	public void createDB() {
		clearDB();
		
		graphDB = new GraphDatabaseFactory().newEmbeddedDatabase(DB_PATH);
		nodeIndex = graphDB.index().forNodes("nodes");
		registerShutdownHook(graphDB);
		
		Transaction tx = graphDB.beginTx();
		try {
            Node startNode = graphDB.createNode();
            startNodeId = startNode.getId();

            Node thomas = graphDB.createNode();
            thomas.setProperty( "name", "Thomas Anderson" );
            thomas.setProperty( "age", 29 );
            nodeIndex.add(thomas, PRIMARY_KEY, thomas.getProperty("name"));

            startNode.createRelationshipTo( thomas, RelTypes.NEO_NODE );

            Node trinity = graphDB.createNode();
            trinity.setProperty( "name", "Trinity" );
            nodeIndex.add(trinity, PRIMARY_KEY, trinity.getProperty("name"));
            Relationship rel = thomas.createRelationshipTo( trinity,
                    RelTypes.KNOWS );
            rel.setProperty( "age", "3 days" );
            Node morpheus = graphDB.createNode();
            morpheus.setProperty( "name", "Morpheus" );
            morpheus.setProperty( "rank", "Captain" );
            morpheus.setProperty( "occupation", "Total badass" );
            nodeIndex.add(morpheus, PRIMARY_KEY, morpheus.getProperty("name"));
            thomas.createRelationshipTo( morpheus, RelTypes.KNOWS );
            rel = morpheus.createRelationshipTo( trinity, RelTypes.KNOWS );
            rel.setProperty( "age", "12 years" );
            Node cypher = graphDB.createNode();
            cypher.setProperty( "name", "Cypher" );
            cypher.setProperty( "last name", "Reagan" );
            nodeIndex.add(cypher, PRIMARY_KEY, cypher.getProperty("name"));
            trinity.createRelationshipTo( cypher, RelTypes.KNOWS );
            rel = morpheus.createRelationshipTo( cypher, RelTypes.KNOWS );
            rel.setProperty( "disclosure", "public" );
            Node smith = graphDB.createNode();
            smith.setProperty( "name", "Agent Smith" );
            smith.setProperty( "version", "1.0b" );
            smith.setProperty( "language", "C++" );
            nodeIndex.add(smith, PRIMARY_KEY, smith.getProperty("name"));
            rel = cypher.createRelationshipTo( smith, RelTypes.KNOWS );
            rel.setProperty( "disclosure", "secret" );
            rel.setProperty( "age", "6 months" );
            Node architect = graphDB.createNode();
            architect.setProperty( "name", "The Architect" );
            nodeIndex.add(architect, PRIMARY_KEY, architect.getProperty("name"));
            smith.createRelationshipTo( architect, RelTypes.CODED_BY );
            
			tx.success();
		}
		finally {
			tx.finish();
		}
	}
	
	public Traverser getFriends(final Node person) {
		TraversalDescription td = Traversal.description()
			.breadthFirst()
			.relationships(RelTypes.KNOWS, Direction.OUTGOING)
			.evaluator(Evaluators.excludeStartPosition());
		return td.traverse(person);
	}
	
	public void printNodeFriends(Node node) {
//		Node neo = graphDB.getNodeById(startNodeId)
//			.getSingleRelationship(RelTypes.NEO_NODE, Direction.OUTGOING)
//			.getEndNode();
		int friendsNumbers = 0;
		System.out.println(node.getProperty(PRIMARY_KEY) + "'s friends:");
		for(Path friendPath: getFriends(node)) {
			System.out.println("At depth " + friendPath.length() + " => "
					+ friendPath.endNode().getProperty(PRIMARY_KEY));
			friendsNumbers++;
		}
		System.out.println("Number of friends found: " + friendsNumbers);
	}
	
	public void printCypherFriends(String name) {
		ExecutionEngine engine = new ExecutionEngine(graphDB);
		ExecutionResult result = engine.execute(
				"start n=node:nodes(name=\"" + name + "\") "
				+ "match n-[:KNOWS*..]->f "
				+ "return distinct f, f.name");
		System.out.println(result.dumpToString());
	}
	
	public void printThomasFriends() {
		printNodeFriends(nodeIndex.get(PRIMARY_KEY, "Thomas Anderson").getSingle());
	}
	
	public Iterable<Path> findShortestPath(Node node1, Node node2) {
		PathFinder<Path> finder = GraphAlgoFactory.shortestPath(
				Traversal.expanderForTypes(RelTypes.KNOWS, Direction.OUTGOING), 5);
		Iterable<Path> paths = finder.findAllPaths(node1, node2);
		return paths;
	}
	
	public void printShortestPaths() {
		Node node1 = nodeIndex.get(PRIMARY_KEY, "Thomas Anderson").getSingle();
		Node node2 = nodeIndex.get(PRIMARY_KEY, "Agent Smith").getSingle();
		for(Path shortestPath: findShortestPath(node1, node2)) {
			System.out.println(shortestPath.toString());
		}
	}
	
	private static void registerShutdownHook(final GraphDatabaseService graphDB) {
		Runtime.getRuntime().addShutdownHook(new Thread() {
			@Override
			public void run() {
				graphDB.shutdown();
			}
		});
	}
	
	private void shutdown() {
		graphDB.shutdown();
	}

	public static void main(String[] args) {
		Example example = new Example();
		example.createDB();
		//example.printThomasFriends();
		example.printCypherFriends("Thomas Anderson");
		//example.printShortestPaths();
		example.shutdown();
	}*/

}
