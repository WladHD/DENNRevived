package de.denn.server.cmd.out;

import de.denn.graph.interfaces.NodeInterface;
import de.denn.graph.nodes.Node2D;
import de.denn.server.cmd.Command;

public class CommandOutNodeCoordsViewBox extends Command {

	private double minX;
	private double minY;
	private double maxX;
	private double maxY;

	private double height;
	private double width;

	private double centerX;
	private double centerY;
	
	private int dimension;

	public CommandOutNodeCoordsViewBox(NodeInterface[] nodes) {
		super("NodeCoordsViewBox");
		
		dimension = nodes.length;

		for (int i = 0; i < dimension; i++) {
			Node2D n = (Node2D) nodes[i];

			if (i == 0) {
				maxX = n.getX();
				minX = n.getX();
				minY = n.getY();
				maxY = n.getY();
				continue;
			}

			if (n.getX() < minX)
				minX = n.getX();

			if (n.getY() < minY)
				minY = n.getY();

			if (n.getX() > maxX)
				maxX = n.getX();

			if (n.getY() > maxY)
				maxY = n.getY();
		}

		width = Math.abs(minX) + Math.abs(maxX);
		height = Math.abs(minY) + Math.abs(maxY);

		centerX = (minX + maxX) / 2;
		centerY = (minY + maxY) / 2;
	}
	
	// No use, just removing warnings
	public double removeWarnings() {
		return height + width + centerX + centerY;
	}

}
