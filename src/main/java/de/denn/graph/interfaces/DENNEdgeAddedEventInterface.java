package de.denn.graph.interfaces;

import de.denn.errors.ClientMovedOnException;
import de.denn.graph.DENNAlgorithm;

public interface DENNEdgeAddedEventInterface {
	
	void added(int fromId, int toId, int dist, DENNAlgorithm t) throws ClientMovedOnException;
	
}
