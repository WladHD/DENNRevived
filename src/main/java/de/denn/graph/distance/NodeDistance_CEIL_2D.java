package de.denn.graph.distance;

import de.denn.data.enums.TSP_EdgeWeightType;
import de.denn.graph.interfaces.NodeDistanceInterface;
import de.denn.graph.interfaces.NodeInterface;
import de.denn.graph.nodes.Node2D;

public class NodeDistance_CEIL_2D implements NodeDistanceInterface {

	@Override
	public TSP_EdgeWeightType getEdgeWeightType() {
		return TSP_EdgeWeightType.EUC_2D;
	}

	@Override
	public int getWeight(NodeInterface a, NodeInterface b) {
		return getWeight((Node2D) a, (Node2D) b);
	}

	public int getWeight(Node2D a, Node2D b) {
		return (int) Math.ceil(Math.sqrt(Math.pow(a.getX() - b.getX(), 2) + Math.pow(a.getY() - b.getY(), 2)));
	}

}
