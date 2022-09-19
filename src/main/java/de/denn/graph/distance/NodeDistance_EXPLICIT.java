package de.denn.graph.distance;

import de.denn.data.enums.TSP_EdgeWeightType;
import de.denn.graph.interfaces.NodeDistanceInterface;
import de.denn.graph.interfaces.NodeInterface;

public class NodeDistance_EXPLICIT implements NodeDistanceInterface {
	
	private int[][] matrix;
	
	public NodeDistance_EXPLICIT(int[][] matrix) {
		this.matrix = matrix;
	}

	@Override
	public TSP_EdgeWeightType getEdgeWeightType() {
		return TSP_EdgeWeightType.EXPLICIT;
	}

	@Override
	public int getWeight(NodeInterface a, NodeInterface b) {
		if(a.getID() == -1 || b.getID() == -1)
			return new NodeDistance_EUC_2D().getWeight(a, b);
		
		return matrix[a.getID() - 1][b.getID() - 1];
	}

}
