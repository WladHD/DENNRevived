package de.denn.data.enums;

import de.denn.errors.TSPUnsupportedTypeException;

public enum TSP_DisplayDataType {
	TWOD_DISPLAY;
	
	public static TSP_DisplayDataType parse(String s) throws TSPUnsupportedTypeException {
		for(TSP_DisplayDataType v : values())
			if(v.toString().equals(s))
				return v;
		
		throw new TSPUnsupportedTypeException("DISPLAY_DATA_TYPE", s);
	}
}
