package de.denn.data.enums;

import de.denn.errors.TSPUnsupportedTypeException;

public enum TSP_Type {
	TSP;
	
	public static TSP_Type parse(String s) throws TSPUnsupportedTypeException {
		for(TSP_Type v : values())
			if(v.toString().equals(s))
				return v;
		
		throw new TSPUnsupportedTypeException("TYPE", s);
	}
}
