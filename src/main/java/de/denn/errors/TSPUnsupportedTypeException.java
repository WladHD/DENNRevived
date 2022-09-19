package de.denn.errors;

import java.security.InvalidAlgorithmParameterException;

public class TSPUnsupportedTypeException extends InvalidAlgorithmParameterException {

	private static final long serialVersionUID = 1L;
	
	public TSPUnsupportedTypeException(String attribute, String unsupportedValue) {
		super(String.format("Type '%s' for %s is not supported yet.", unsupportedValue, attribute));
	}

}
