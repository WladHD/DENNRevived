package de.denn.data.model;

import java.util.ArrayList;

import de.denn.data.enums.TSP_DisplayDataType;
import de.denn.data.enums.TSP_EdgeWeightFormat;
import de.denn.data.enums.TSP_EdgeWeightType;
import de.denn.data.enums.TSP_Type;
import de.denn.helper.TimeStopper;

public class TSP_RawData extends TimeStopper {
	
	private String name;
	private ArrayList<String> comment;
	private TSP_Type type;
	private int dimension;
	private TSP_EdgeWeightType edge_weight_type;
	private TSP_EdgeWeightFormat edge_weight_format;
	private TSP_DisplayDataType display_data_type;
	
	// Optional
	private ArrayList<String> edge_weight_section;
	private ArrayList<String> node_coord_section;
	private ArrayList<String> display_data_section;
	
	public TSP_RawData() {
		comment = new ArrayList<String>();
		edge_weight_section = new ArrayList<String>();
		node_coord_section = new ArrayList<String>();
		display_data_section = new ArrayList<String>();
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public ArrayList<String> getComments() {
		return comment;
	}
	
	public void addComment(String comment) {
		this.comment.add(comment);
	}
	
	public TSP_Type getType() {
		return type;
	}
	
	public void setType(TSP_Type type) {
		this.type = type;
	}
	
	public int getDimension() {
		return dimension;
	}
	
	public void setDimension(int dimension) {
		this.dimension = dimension;
	}
	
	public TSP_EdgeWeightType getEdgeWeightType() {
		return edge_weight_type;
	}
	
	public void setEdgeWeightType(TSP_EdgeWeightType edge_weight_type) {
		this.edge_weight_type = edge_weight_type;
	}
	
	public TSP_EdgeWeightFormat getEdgeWeightFormat() {
		return edge_weight_format;
	}
	
	public void setEdgeWeightFormat(TSP_EdgeWeightFormat edge_weight_format) {
		this.edge_weight_format = edge_weight_format;
	}
	
	public TSP_DisplayDataType getDisplayDataType() {
		return display_data_type;
	}
	
	public void setDisplayDataType(TSP_DisplayDataType display_data_type) {
		this.display_data_type = display_data_type;
	}
	
	public ArrayList<String> getEdgeWeightSection() {
		return edge_weight_section;
	}
	
	public void addEdgeWeightSection(String edge_weight_section) {
		this.edge_weight_section.add(edge_weight_section);
	}
	
	public ArrayList<String> getNodeCoordSection() {
		return node_coord_section;
	}
	
	public void addNodeCoordSection(String node_coord_section) {
		this.node_coord_section.add(node_coord_section);
	}
	
	public ArrayList<String> getDisplayDataSection() {
		return display_data_section;
	}
	
	public void addDisplayDataSection(String display_data_section) {
		this.display_data_section.add(display_data_section);
	}
	
}
