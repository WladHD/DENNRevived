package de.denn.data.parser.interfaces;

import java.util.ArrayList;

import de.denn.graph.nodes.Node2D;
import de.denn.graph.nodes.Node2D_GEOM;

public interface TSP_ParserHelper {
	
	default Node2D parseNode2D(String s) {
		String[] args = s.split(" ");
		return new Node2D(Integer.parseInt(args[0]), Double.parseDouble(args[1]), Double.parseDouble(args[2]));
	}
	
	default Node2D_GEOM parseNode2D_GEOM(String s) {
		String[] args = s.split(" ");
		return new Node2D_GEOM(Integer.parseInt(args[0]), Double.parseDouble(args[1]), Double.parseDouble(args[2]));
	}
	
	default String convertToString(ArrayList<String> strings) {
		StringBuilder sb = new StringBuilder();
		
		for(int i = 0; i < strings.size(); i++)
			sb.append(strings.get(i)).append(" ");
		
		return sb.toString().trim();
	}
	
}
