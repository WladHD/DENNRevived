package de.denn.graph.interfaces;

import de.denn.helper.TimeStopperInterface;

public interface DENNTourInterface extends TimeStopperInterface {
	
	int[] getTour();
	long getDistance();
	String getTSPProblem();
	
}
