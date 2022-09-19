package de.denn.server.cmd.out;

import de.denn.errors.ClientMovedOnException;
import de.denn.graph.DENNAlgorithm;
import de.denn.graph.interfaces.DENNEdgeAddedEventInterface;
import de.denn.server.Connection;
import de.denn.server.cmd.Command;

public class CommandOutEdgeAddedEvent extends Command implements DENNEdgeAddedEventInterface {
	
	private int fromId;
	private int toId;
	private int dist;
	private transient Connection c;
	
	public CommandOutEdgeAddedEvent(Connection c) {
		super("EdgeAddedEvent");
		
		this.c = c;
	}

	@Override
	public void added(int fromId, int toId, int dist, DENNAlgorithm t) throws ClientMovedOnException {
		this.fromId = fromId;
		this.toId = toId;
		this.dist = dist;
		
		if(c.getSocket() == null || c.getSocket().isClosed() || c.getDENNAlgorithm() != t || !c.getDENNAlgorithm().getRawData().getName().equals(t.getRawData().getName()))
			throw new ClientMovedOnException();
		
		send(c);
	}

	public int getFromId() {
		return fromId;
	}

	public int getToId() {
		return toId;
	}

	public int getDist() {
		return dist;
	}
	
}
