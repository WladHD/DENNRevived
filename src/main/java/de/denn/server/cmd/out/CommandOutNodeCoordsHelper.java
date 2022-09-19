package de.denn.server.cmd.out;

import de.denn.graph.interfaces.NodeInterface;
import de.denn.server.Connection;

public class CommandOutNodeCoordsHelper {
	
	private static final int stepper = 1000;
	
	public static void send(Connection c, NodeInterface[] t) {
		new CommandOutNodeCoordsViewBox(t).send(c);
		
		for(int i = 0; i < t.length; i += stepper) {
			int arrLength = ((t.length - (i + stepper)) < 0 ? t.length - i : stepper);
			
			if(arrLength == 0)
				break;
			NodeInterface[] ni = new NodeInterface[arrLength];
			
			System.arraycopy(t, i, ni, 0, arrLength);
			
			new CommandOutNodeCoords(ni).send(c);
		}
	}
	
}
