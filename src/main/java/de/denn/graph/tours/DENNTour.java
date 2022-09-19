package de.denn.graph.tours;

import de.denn.graph.interfaces.DENNTourInterface;
import de.denn.helper.TimeStopper;

public class DENNTour extends TimeStopper implements DENNTourInterface {

	private String tspProblem;
	private int[] tour;
	private long distance;
	
	public DENNTour(String tspProblem) {
		setTSPProblem(tspProblem);
	}
	
	@Override
	public int[] getTour() {
		return tour;
	}

	@Override
	public String getTSPProblem() {
		return tspProblem;
	}

	@Override
	public long getDistance() {
		return distance;
	}

	public void setTSPProblem(String tspProblem) {
		this.tspProblem = tspProblem;
	}

	public void setTour(int[] tour) {
		this.tour = tour;
	}

	public void setDistance(long distance) {
		this.distance = distance;
	}
	
	public void addDistance(int distance) {
		this.distance += distance;
	}

}
