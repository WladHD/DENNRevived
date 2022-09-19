package de.denn.data;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Scanner;

import de.denn.data.enums.TSP_DisplayDataType;
import de.denn.data.enums.TSP_EdgeWeightFormat;
import de.denn.data.enums.TSP_EdgeWeightType;
import de.denn.data.enums.TSP_Type;
import de.denn.data.model.TSP_RawData;
import de.denn.errors.TSPUnsupportedTypeException;

public class TSP_DataLoader {
	private static TSP_DataLoader inst;

	public static TSP_DataLoader getInstance() {
		if (inst == null)
			inst = new TSP_DataLoader();

		return inst;
	}

	private TSP_DataLoader() {

	}

	public TSP_RawData constructFromFile(File f) throws TSPUnsupportedTypeException {
		TSP_RawData cont = null;

		try (FileInputStream fis = new FileInputStream(f); Scanner sc = new Scanner(fis);) {
			cont = constructFromScanner(sc);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return cont;
	}

	public TSP_RawData constructFromScanner(Scanner sc) throws TSPUnsupportedTypeException {
		TSP_RawData container = new TSP_RawData();
		container.startTimeMeasure();

		// 0 : Normal | 1 : NODE_COORD_SECTION | 2 : EDGE_WEIGHT_SECTION | 3 :
		// DISPLAY_DATA_SECTION
		short expectedArrayType = 0;

		while (sc.hasNext()) {
			String input = sc.nextLine().trim();

			while (input.contains("  "))
				input = input.replace("  ", " ");

			String[] pair = parseArgs(input);

			switch (pair[0]) {
			case "EOF":
				expectedArrayType = 0;
				container.stopTimeMeasure();
				return container;
			case "NAME":
				expectedArrayType = 0;
				container.setName(pair[1]);
				break;
			case "TYPE":
				expectedArrayType = 0;
				container.setType(TSP_Type.parse(pair[1]));
				break;
			case "COMMENT":
				expectedArrayType = 0;
				container.addComment(pair[1]);
				break;
			case "DIMENSION":
				expectedArrayType = 0;
				container.setDimension(Integer.parseInt(pair[1]));
				break;
			case "EDGE_WEIGHT_TYPE":
				expectedArrayType = 0;
				container.setEdgeWeightType(TSP_EdgeWeightType.parse(pair[1]));
				break;
			case "EDGE_WEIGHT_FORMAT":
				expectedArrayType = 0;
				container.setEdgeWeightFormat(TSP_EdgeWeightFormat.parse(pair[1]));
				break;
			case "DISPLAY_DATA_TYPE":
				expectedArrayType = 0;
				container.setDisplayDataType(TSP_DisplayDataType.parse(pair[1]));
				break;
			case "NODE_COORD_SECTION":
				expectedArrayType = 1;
				continue;
			case "EDGE_WEIGHT_SECTION":
				expectedArrayType = 2;
				continue;
			case "DISPLAY_DATA_SECTION":
				expectedArrayType = 3;
				continue;
			default:
			}

			if (expectedArrayType != 0) {
				switch (expectedArrayType) {
				case 1:
					container.addNodeCoordSection(input);
					break;
				case 2:
					container.addEdgeWeightSection(input);
					break;
				case 3:
					container.addDisplayDataSection(input);
					break;
				default:
				}
			}
		}

		container.stopTimeMeasure();
		return container;
	}

	private final String[] parseArgs(String s) {
		final String[] pair = s.split(":");

		for (int i = 0; i < pair.length; i++)
			pair[i] = pair[i].trim();

		return pair;
	}
}
