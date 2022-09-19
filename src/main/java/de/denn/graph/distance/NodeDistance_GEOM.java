package de.denn.graph.distance;

import de.denn.data.enums.TSP_EdgeWeightType;
import de.denn.graph.interfaces.NodeDistanceInterface;
import de.denn.graph.interfaces.NodeInterface;
import de.denn.graph.nodes.Node2D_GEOM;

public class NodeDistance_GEOM implements NodeDistanceInterface {
	
	private final double RRR = 6378388.0;

	@Override
	public TSP_EdgeWeightType getEdgeWeightType() {
		return TSP_EdgeWeightType.GEOM;
	}

	@Override
	public int getWeight(NodeInterface a, NodeInterface b) {
		return getWeight((Node2D_GEOM) a, (Node2D_GEOM) b);
	}

	public int getWeight(Node2D_GEOM a, Node2D_GEOM b) {
		final double q3 = Math.pow(Math.sin((a.getYL() - b.getYL()) / 2.0), 2d);
		final double q4 = Math.pow(Math.cos((a.getYL() - b.getYL()) / 2.0), 2d);
		
		return (int) (RRR * Math.atan2(Math.sqrt(Math.pow(Math.cos(b.getXL()) * Math.sin(a.getYL() - b.getYL()), 2d) + Math.pow(Math.sin(a.getXL() + b.getXL()) * q3 - Math.sin(a.getXL() - b.getXL()) * q4, 2d)), Math.cos(a.getXL() - b.getXL()) * q4 - Math.cos(a.getXL() + b.getXL()) * q3) + 1.0);
	}

}
