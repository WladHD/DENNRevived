package de.denn.server.cmd.out;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;

import de.denn.Main;
import de.denn.server.cmd.Command;

public class CommandOutAvailableProblems extends Command {

	private ArrayList<String> strings = new ArrayList<>();

	public CommandOutAvailableProblems() {
		super("AvailableProblemsResponse");

		File[] files = new File(Main.tspDirectory).listFiles(new FilenameFilter() {
			@Override
			public boolean accept(File dir, String name) {
				return name.endsWith(".tsp");
			}
		});

		for (File tsp : files)
			strings.add(tsp.getName().replace(".tsp", ""));
	}

}
