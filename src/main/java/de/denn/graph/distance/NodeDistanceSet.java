package de.denn.graph.distance;

import de.denn.graph.interfaces.NodeDistanceSetInterface;
import de.denn.graph.interfaces.NodeInterface;

public class NodeDistanceSet implements NodeDistanceSetInterface {
	
	private final NodeInterface a;
	private final int dist;
	
	public NodeDistanceSet(NodeInterface nodeInterface, int dist) {
		this.a = nodeInterface;
		this.dist = dist;
	}

	@Override
	public final NodeInterface getNodeInterface() {
		return a;
	}

	@Override
	public final int getDistance() {
		return dist;
	}

}
