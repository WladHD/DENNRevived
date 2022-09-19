package de.denn.graph.interfaces;

import de.denn.data.model.TSP_RawData;
import de.denn.errors.ClientMovedOnException;
import de.denn.graph.startnode.DENNStartNodeSelectType;

public interface DENNAlgorithmInterface {
	
	DENNTourInterface getTourByPreference(DENNStartNodeSelectType type) throws ClientMovedOnException;
	
	DENNTourInterface getTourById(int aId, int bId) throws ClientMovedOnException;
	
	DENNTourInterface getTourByIndex(int aIndex, int bIndex) throws ClientMovedOnException;
	
	DENNTourInterface getTour(NodeInterface a, NodeInterface b) throws ClientMovedOnException;
	
	TSP_RawData getRawData();

	DENNTourInterface getTourById(int aId) throws ClientMovedOnException;
	
}
