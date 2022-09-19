package de.denn.data.parser.ncs;

import java.util.ArrayList;

import de.denn.data.enums.TSP_EdgeWeightType;
import de.denn.data.model.TSP_RawData;
import de.denn.data.parser.interfaces.TSP_NodeCoordSectionParserInterface;
import de.denn.graph.interfaces.NodeInterface;

public class TSP_NodeCoordSectionParser_2D implements TSP_NodeCoordSectionParserInterface {

	@Override
	public TSP_EdgeWeightType getEdgeWeightType() {
		return TSP_EdgeWeightType.EUC_2D;
	}
	
	@Override
	public ArrayList<NodeInterface> parseNodes(TSP_RawData data) {
		ArrayList<NodeInterface> ar = new ArrayList<NodeInterface>();
		
		for(String s : data.getNodeCoordSection())
			ar.add(parseNode2D(s));
		
		return ar;
	}

}
