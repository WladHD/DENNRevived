package de.denn.server.cmd.out;

import de.denn.graph.interfaces.NodeInterface;
import de.denn.server.cmd.Command;

public class CommandOutNodeCoords extends Command {
	
	private NodeInterface[] nodes;
	
	public CommandOutNodeCoords(NodeInterface[] nodes) {
		super("NodeCoords");
		
		this.nodes = nodes;
	}
	
	public NodeInterface[] getNodes() {
		return nodes;
	}
	
}
