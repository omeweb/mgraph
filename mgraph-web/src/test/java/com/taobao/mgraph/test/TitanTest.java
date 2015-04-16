package com.taobao.mgraph.test;

import com.thinkaurelius.titan.core.TitanFactory;
import com.thinkaurelius.titan.core.TitanGraph;

public class TitanTest {
	public static void main(String[] args){
		// Connect to Cassandra on localhost using a default configuration
		TitanGraph graph = TitanFactory.open("berkeleyje:d:/data/tatan-graph");//TitanFactory.open("conf/titan-berkeleyje.properties");
		System.out.println(graph.isOpen());
		
		graph.shutdown();
	}
}
