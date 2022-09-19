package de.denn.data.parser.interfaces;

import de.denn.data.enums.TSP_EdgeWeightType;
import de.denn.data.model.TSP_RawData;

public interface TSP_EdgeWeightSectionParserInterface extends TSP_ParserHelper {
	
	TSP_EdgeWeightType getEdgeWeightType();
	
	int[][] parseMatrix(TSP_RawData data);

}
