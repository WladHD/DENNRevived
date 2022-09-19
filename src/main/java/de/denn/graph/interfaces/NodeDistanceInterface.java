package de.denn.graph.interfaces;

import de.denn.data.enums.TSP_EdgeWeightType;

public interface NodeDistanceInterface {
	
	TSP_EdgeWeightType getEdgeWeightType();
	
	int getWeight(NodeInterface a, NodeInterface b);
	
}
