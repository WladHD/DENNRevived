package de.denn.data.parser.interfaces;

import java.util.ArrayList;

import de.denn.data.enums.TSP_EdgeWeightType;
import de.denn.data.model.TSP_RawData;
import de.denn.graph.interfaces.NodeInterface;

public interface TSP_NodeCoordSectionParserInterface extends TSP_ParserHelper {
	
	TSP_EdgeWeightType getEdgeWeightType();
	
	ArrayList<NodeInterface> parseNodes(TSP_RawData data);

}
