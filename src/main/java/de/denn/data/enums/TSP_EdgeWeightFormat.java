package de.denn.data.enums;

import de.denn.errors.TSPUnsupportedTypeException;

public enum TSP_EdgeWeightFormat {
	LOWER_DIAG_ROW;
	
	public static TSP_EdgeWeightFormat parse(String s) throws TSPUnsupportedTypeException {
		for(TSP_EdgeWeightFormat v : values())
			if(v.toString().equals(s))
				return v;
		
		throw new TSPUnsupportedTypeException("EDGE_WEIGHT_FORMAT", s);
	}
}
