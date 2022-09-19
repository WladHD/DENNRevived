package de.denn.server.cmd.in;

import java.io.File;

import de.denn.Main;
import de.denn.data.TSP_DataLoader;
import de.denn.data.model.TSP_RawData;
import de.denn.errors.TSPUnsupportedTypeException;
import de.denn.graph.DENN;
import de.denn.server.Connection;
import de.denn.server.cmd.out.CommandOutEdgeAddedEvent;
import de.denn.server.cmd.out.CommandOutNotSupportedYet;

public class CommandInSelectProblem {

	// ACHTUNG! Durch .. kann durch Eltern navigiert werden!
	public static boolean process(Connection c, String problem) {
		File f = new File(Main.tspDirectory + "\\" + problem + ".tsp");

		TSP_RawData cont = null;
		try {
			cont = TSP_DataLoader.getInstance().constructFromFile(f);
			c.setDENNAlgorithm(DENN.getInstance().setupAlgorithm(cont, new CommandOutEdgeAddedEvent(c)));
		} catch (TSPUnsupportedTypeException e) {
			new CommandOutNotSupportedYet(e).send(c);
			return false;
		}
		
		return true;

	}

}
