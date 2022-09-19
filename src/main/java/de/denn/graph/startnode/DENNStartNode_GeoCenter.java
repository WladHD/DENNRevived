package de.denn.graph.startnode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import de.denn.graph.interfaces.DENNStartNodeSelectInterface;
import de.denn.graph.interfaces.NodeDistanceInterface;
import de.denn.graph.interfaces.NodeInterface;
import de.denn.graph.nodes.Node2D;

public class DENNStartNode_GeoCenter implements DENNStartNodeSelectInterface {

	private NodeDistanceInterface nid;

	public DENNStartNode_GeoCenter(NodeDistanceInterface nid) {
		this.nid = nid;
	}

	@Override
	public int getStartNodeId(NodeInterface[] nodes) {
		ArrayList<Node2D> normed = new ArrayList<>();

		Node2D norm = getNorm(nodes);

		for (NodeInterface place : nodes) {
			Node2D p = (Node2D) place;
			normed.add(new Node2D(p.getID(), p.getX() - norm.getX(), p.getY() - norm.getY()));
		}
			

		Collections.sort(normed, new Comparator<Node2D>() {

			@Override
			public int compare(Node2D o1, Node2D o2) {
				return nid.getWeight(o1, new Node2D(-1, 0, 0)) - nid.getWeight(o2, new Node2D(-1, 0, 0));
			}
		});

		return normed.get(0).getID();
	}

	private Node2D getNorm(NodeInterface[] p) {
		double x = 0, y = 0;

		for (int i = 0; i < p.length; i++) {
			x += ((Node2D) p[i]).getX();
			y += ((Node2D) p[i]).getY();
		}

		x /= p.length;
		y /= p.length;

		return new Node2D(-1, x, y);
	}

}
