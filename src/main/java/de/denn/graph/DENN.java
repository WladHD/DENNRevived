package de.denn.graph;

import java.util.ArrayList;

import de.denn.data.TSP_DataParser;
import de.denn.data.enums.TSP_EdgeWeightType;
import de.denn.data.model.TSP_RawData;
import de.denn.graph.distance.NodeDistance_CEIL_2D;
import de.denn.graph.distance.NodeDistance_EUC_2D;
import de.denn.graph.distance.NodeDistance_EXPLICIT;
import de.denn.graph.distance.NodeDistance_GEOM;
import de.denn.graph.interfaces.DENNEdgeAddedEventInterface;
import de.denn.graph.interfaces.NodeDistanceInterface;
import de.denn.graph.interfaces.NodeInterface;

public class DENN {

	private static DENN inst;

	public static DENN getInstance() {
		if (inst == null)
			inst = new DENN();

		return inst;
	}

	private DENN() {

	}

	public DENNAlgorithm setupAlgorithm(TSP_RawData data, DENNEdgeAddedEventInterface edgeAddedEvent) {
		TSP_DataParser parser = new TSP_DataParser(data);

		boolean matrix = data.getEdgeWeightType() == TSP_EdgeWeightType.EXPLICIT;

		ArrayList<NodeInterface> nodes = matrix ? parser.parseDisplayDataSection() : parser.parseNodeCoordSection();

		NodeDistanceInterface ndi = null;

		switch (data.getEdgeWeightType()) {
		case CEIL_2D:
			ndi = new NodeDistance_CEIL_2D();
			break;
		case EUC_2D:
			ndi = new NodeDistance_EUC_2D();
			break;
		case GEOM:
			ndi = new NodeDistance_GEOM();
			break;
		case EXPLICIT:
			ndi = new NodeDistance_EXPLICIT(parser.parseEdgeWeightSection());
			break;
		default:
		}
		
		return new DENNAlgorithm(nodes.toArray(new NodeInterface[nodes.size()]), ndi, data, edgeAddedEvent);
	}

}
