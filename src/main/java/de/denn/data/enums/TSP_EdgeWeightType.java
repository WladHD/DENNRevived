package de.denn.data.enums;

import de.denn.errors.TSPUnsupportedTypeException;

public enum TSP_EdgeWeightType {
	EUC_2D,
	EXPLICIT,
	CEIL_2D,
	GEOM;
	
	public static TSP_EdgeWeightType parse(String s) throws TSPUnsupportedTypeException {
		for(TSP_EdgeWeightType v : values())
			if(v.toString().equals(s))
				return v;
		
		throw new TSPUnsupportedTypeException("EDGE_WEIGHT_TYPE", s);
	}
}
