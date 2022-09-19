package de.denn.data.parser.dds;

import java.util.ArrayList;

import de.denn.data.enums.TSP_DisplayDataType;
import de.denn.data.model.TSP_RawData;
import de.denn.data.parser.interfaces.TSP_DisplayDataSectionParserInterface;
import de.denn.graph.interfaces.NodeInterface;

public class TSP_DisplayDataSectionParser_TWOD_DISPLAY implements TSP_DisplayDataSectionParserInterface {

	@Override
	public TSP_DisplayDataType getDisplayDataType() {
		return TSP_DisplayDataType.TWOD_DISPLAY;
	}

	@Override
	public ArrayList<NodeInterface> parseNodes(TSP_RawData data) {
		ArrayList<NodeInterface> ar = new ArrayList<NodeInterface>();
		
		for(String s : data.getDisplayDataSection())
			ar.add(parseNode2D(s));
		
		return ar;
	}

}
