package de.denn.graph.nodes;

import de.denn.graph.interfaces.NodeInterface;

public class Node2D implements NodeInterface {

	private final int id;
	private final double x;
	private final double y;

	public Node2D(int id, double x, double y) {
		this.id = id;
		this.x = x;
		this.y = y;
	}

	@Override
	public int getID() {
		return id;
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}
}
