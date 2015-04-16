package com.taobao.mgraph.test;

import org.neo4j.cypher.javacompat.ExecutionEngine;
import org.neo4j.cypher.javacompat.ExecutionResult;
import org.neo4j.graphdb.Direction;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Path;
import org.neo4j.graphdb.Transaction;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;
import org.neo4j.graphdb.traversal.Evaluators;
import org.neo4j.graphdb.traversal.TraversalDescription;
import org.neo4j.graphdb.traversal.Traverser;
import org.neo4j.kernel.Traversal;

public class StartNeo4j {

	static GraphDatabaseService graphDb = null;

	public static void createNodes(int start, int num) {
		for (int i = start; i <= start + num; i++) {
			Node n = graphDb.createNode();
			n.setProperty("id", i);
		}
	}

	public static void createR(int num) {
		for (int i = 0; i <= num; i++) {
			Node n1 = graphDb.getNodeById(tools.StringUtil.RANDOM.nextInt(10000 * 1));
			Node n2 = graphDb.getNodeById(tools.StringUtil.RANDOM.nextInt(100));
			n1.createRelationshipTo(n2, MyRelationshipType.MEMBER);
		}
	}

	public static void main(String[] args) {
		String DB_PATH = "D:\\data\\neodb";
		graphDb = new GraphDatabaseFactory().newEmbeddedDatabase(DB_PATH);
		registerShutdownHook(graphDb);

		Transaction tx = graphDb.beginTx();

		// ------------------
		try {
			new tools.code.RunTimer().run("save node", 1, new Runnable() {
				@Override
				public void run() {
					// createNodes(0, 10000 * 1000);// 1w = 480ms
					// createR(10000);//700ms
				}
			});

			// System.out.println(graphDb.getNodeById(3).getClass());// class org.neo4j.kernel.impl.core.NodeProxy
			// System.out.println(graphDb.getNodeById(3).getProperty("id"));
			// graphDb.getNodeById(66663333).setProperty("n", "橘子香水");// 修改个属性
			tx.success();
		} finally {
			tx.finish();
		}

		// ------------------tarvesal 策略
		TraversalDescription traversal = Traversal.description()
				.relationships(MyRelationshipType.MEMBER, Direction.OUTGOING).order(Traversal.preorderBreadthFirst())
				.evaluator(Evaluators.atDepth(1));

		// 遍历结果
		Traverser traverse = traversal.traverse(graphDb.getNodeById(1));
		for (Path path : traverse) {
			System.out.println("path: " + path);
			// System.out.print("\t\t" + path.endNode().getProperty("id"));

			// System.out.print("\n------------------\n");
		}

		// for (Node n : traverse.nodes()) {
		// System.out.print("node id: " + n.getId());
		// }
		//
		// for (Relationship r : traverse.relationships()) {
		// System.out.print("relation id: " + r.getId());
		// }

		// ------------------let's execute a query now
		ExecutionEngine engine = new ExecutionEngine(graphDb);
		ExecutionResult result = engine.execute("start n=node(66663333) return n");

		System.out.println(result.dumpToString());
	}

	private static void registerShutdownHook(final GraphDatabaseService graphDb) {
		Runtime.getRuntime().addShutdownHook(new Thread() {
			@Override
			public void run() {
				graphDb.shutdown();
			}
		});
	}
}
