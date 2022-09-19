package de.denn.graph.nodes;

public class Node2D_GEOM extends Node2D {

	private final double xL;
	private final double yL;

	public Node2D_GEOM(int id, double x, double y) {
		super(id, x, y);
		
		this.xL = translate(x);
		this.yL = translate(y);
	}

	public double getXL() {
		return xL;
	}

	public double getYL() {
		return yL;
	}
	
	public final double translate(double coord) {
		return Math.PI * coord / 180.0d;
	}
}
