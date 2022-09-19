package de.denn.data;

import java.util.ArrayList;

import de.denn.data.enums.TSP_EdgeWeightType;
import de.denn.data.model.TSP_RawData;
import de.denn.data.parser.dds.TSP_DisplayDataSectionParser_TWOD_DISPLAY;
import de.denn.data.parser.ews.TSP_EdgeWeightSectionParser_EXPLICIT;
import de.denn.data.parser.ncs.TSP_NodeCoordSectionParser_2D;
import de.denn.data.parser.ncs.TSP_NodeCoordSectionParser_2D_GEOM;
import de.denn.graph.interfaces.NodeInterface;

public class TSP_DataParser {

	private TSP_RawData container;

	public TSP_DataParser(TSP_RawData container) {
		this.container = container;
	}

	public ArrayList<NodeInterface> parseNodeCoordSection() {
		if (container.getEdgeWeightType() == TSP_EdgeWeightType.GEOM)
			return new TSP_NodeCoordSectionParser_2D_GEOM().parseNodes(container);

		return new TSP_NodeCoordSectionParser_2D().parseNodes(container);
	}

	public int[][] parseEdgeWeightSection() {
		return new TSP_EdgeWeightSectionParser_EXPLICIT().parseMatrix(container);
	}

	public ArrayList<NodeInterface> parseDisplayDataSection() {
		return new TSP_DisplayDataSectionParser_TWOD_DISPLAY().parseNodes(container);
	}

}
