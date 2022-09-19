package de.denn.graph.startnode;

import de.denn.graph.interfaces.DENNStartNodeSelectInterface;
import de.denn.graph.interfaces.NodeDistanceInterface;
import de.denn.graph.interfaces.NodeInterface;

public class DENNStartNode_FurthestFromAll implements DENNStartNodeSelectInterface {
	
	private NodeDistanceInterface nid;
	
	public DENNStartNode_FurthestFromAll(NodeDistanceInterface nid) {
		this.nid = nid;
	}
	
	@Override
	public int getStartNodeId(NodeInterface[] nodes) {
		int[] dist = new int[nodes.length];
	
		for(int i = 0; i < nodes.length; i++) {
			for(int j = i + 1; j < nodes.length; j++) {
				final int weight = nid.getWeight(nodes[i], nodes[j]);
				dist[i] += weight;
				dist[j] += weight;
			}
		}
		
		int minIndex = 0;
		
		for(int i = 1; i < dist.length; i++)
			if(dist[i] > dist[minIndex])
				minIndex = i;
		
		return minIndex + 1;
	}

}
