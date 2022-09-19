package de.denn.server.cmd.out;

import de.denn.errors.TSPUnsupportedTypeException;
import de.denn.server.cmd.Command;

public class CommandOutNotSupportedYet extends Command {
	
	private String error;
	
	public CommandOutNotSupportedYet(TSPUnsupportedTypeException e) {
		super("NotSupportedYet");
		
		this.error = e.getMessage();
	}

	public String getError() {
		return error;
	}
	
}
