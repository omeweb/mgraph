package com.taobao.mgraph.test;

import org.neo4j.graphdb.RelationshipType;

public enum MyRelationshipType implements RelationshipType {
	// KNOWS,
	MEMBER, MANAGER, CREATOR
}
