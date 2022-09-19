package de.denn.server.cmd.out;

import de.denn.graph.interfaces.DENNTourInterface;
import de.denn.server.cmd.Command;

public class CommandOutDENNTour extends Command {
	
	private DENNTourInterface dti;
	
	public CommandOutDENNTour(DENNTourInterface dti) {
		super("TourResult");
		
		this.dti = dti;
	}
	
	public DENNTourInterface getDENNTourInterface() {
		return dti;
	}
	
}
