package de.denn.errors;

import java.security.InvalidAlgorithmParameterException;

public class ClientMovedOnException extends InvalidAlgorithmParameterException {

	private static final long serialVersionUID = 1L;
	
	public ClientMovedOnException() {
		super("Client moved on");
	}

}
