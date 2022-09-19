package de.denn.data.parser.interfaces;

import java.util.ArrayList;

import de.denn.data.enums.TSP_DisplayDataType;
import de.denn.data.model.TSP_RawData;
import de.denn.graph.interfaces.NodeInterface;

public interface TSP_DisplayDataSectionParserInterface extends TSP_ParserHelper {
	
	TSP_DisplayDataType getDisplayDataType();
	
	ArrayList<NodeInterface> parseNodes(TSP_RawData data);

}
