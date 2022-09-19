package de.denn.data;

import com.google.gson.GsonBuilder;
import de.denn.graph.interfaces.DENNTourInterface;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class TourFileWriter {

	private DENNTourInterface dt;

	public TourFileWriter(DENNTourInterface dt) {
		this.dt = dt;
	}

	public void save(File f) {
		try {
			f.createNewFile();

			FileWriter myWriter = new FileWriter(f);
			myWriter.write(new GsonBuilder().setPrettyPrinting().create().toJson(dt));
			myWriter.close();
		} catch (IOException e) {
		}
	}

}
