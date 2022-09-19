package de.denn.graph;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import de.denn.data.model.TSP_RawData;
import de.denn.errors.ClientMovedOnException;
import de.denn.graph.distance.NodeDistanceSet;
import de.denn.graph.interfaces.DENNAlgorithmInterface;
import de.denn.graph.interfaces.DENNEdgeAddedEventInterface;
import de.denn.graph.interfaces.DENNTourInterface;
import de.denn.graph.interfaces.NodeDistanceInterface;
import de.denn.graph.interfaces.NodeDistanceSetInterface;
import de.denn.graph.interfaces.NodeInterface;
import de.denn.graph.startnode.DENNStartNodeSelectType;
import de.denn.graph.startnode.DENNStartNode_FurthestFromAll;
import de.denn.graph.startnode.DENNStartNode_GeoCenter;
import de.denn.graph.tours.DENNTour;

public class DENNAlgorithm implements DENNAlgorithmInterface {

	private TSP_RawData rawContainer;
	private NodeInterface[] nodeInterfaces;
	private NodeDistanceInterface nodeDistanceInterface;
	private DENNEdgeAddedEventInterface edgeAddedEvent;
	private boolean disableEvent = false;

	public DENNAlgorithm(NodeInterface[] nodeInterfaces, NodeDistanceInterface nodeDistanceInterface,
			TSP_RawData rawContainer, DENNEdgeAddedEventInterface edgeAddedEvent) {
		this.nodeInterfaces = nodeInterfaces;
		this.nodeDistanceInterface = nodeDistanceInterface;
		this.rawContainer = rawContainer;
		this.edgeAddedEvent = edgeAddedEvent;

		assertCorrectNodeOrder();
	}

	public NodeInterface[] getNodeInterfaces() {
		return nodeInterfaces;
	}

	public NodeDistanceInterface getNodeDistanceInterface() {
		return nodeDistanceInterface;
	}

	@Override
	public DENNTourInterface getTourByPreference(DENNStartNodeSelectType type) throws ClientMovedOnException {
		int id = -1;

		switch (type) {
		case FURTHEST_FROM_ALL:
			id = new DENNStartNode_FurthestFromAll(nodeDistanceInterface).getStartNodeId(nodeInterfaces);
			break;
		case GEOGRAPHICAL_CENTER:
			id = new DENNStartNode_GeoCenter(nodeDistanceInterface).getStartNodeId(nodeInterfaces);
			break;
		case CALC_ALL:
			disableEvent = true;
			ArrayList<DENNTourInterface> touren = new ArrayList<>();
			for (int i = 0; i < rawContainer.getDimension(); i++) {
				for (int j = i + 1; j < rawContainer.getDimension(); j++) {
					final DENNTourInterface a = getTourByIndex(i, j);
					final DENNTourInterface b = getTourByIndex(j, i);
					touren.add(a);

					if (a.getDistance() != b.getDistance())
						touren.add(b);
				}
			}

			Collections.sort(touren, new Comparator<DENNTourInterface>() {
				@Override
				public int compare(DENNTourInterface o1, DENNTourInterface o2) {
					return ((int) (o1.getDistance() - o2.getDistance()));
				}
			});
			disableEvent = false;

			final DENNTourInterface shortest = touren.get(0);

			if (edgeAddedEvent != null) {
				for (int i = 0; i < rawContainer.getDimension() - 1; i++) {
					edgeAddedEvent.added(shortest.getTour()[i], shortest.getTour()[i + 1],
							nodeDistanceInterface.getWeight(getNodeInterfaceById(shortest.getTour()[i]),
									getNodeInterfaceById(shortest.getTour()[i + 1])),
							this);
				}

				edgeAddedEvent.added(shortest.getTour()[0], shortest.getTour()[rawContainer.getDimension() - 1],
						nodeDistanceInterface.getWeight(getNodeInterfaceById(shortest.getTour()[0]),
								getNodeInterfaceById(rawContainer.getDimension() - 1)),
						this);
			}

			return shortest;

		default:
		}

		if (id == -1)
			return null;

		NodeInterface start = getNodeInterfaceById(id);

		return getTour(start, findNearestUnvisitedNode(start).getNodeInterface());
	}

	@Override
	public DENNTourInterface getTourById(int aId, int bId) throws ClientMovedOnException {
		return getTour(getNodeInterfaceById(aId), getNodeInterfaceById(bId));
	}

	@Override
	public DENNTourInterface getTourById(int aId) throws ClientMovedOnException {
		NodeInterface start = getNodeInterfaceById(aId);
		return getTour(start, findNearestUnvisitedNode(start).getNodeInterface());
	}

	@Override
	public DENNTourInterface getTourByIndex(int aIndex, int bIndex) throws ClientMovedOnException {
		return getTour(nodeInterfaces[aIndex], nodeInterfaces[bIndex]);
	}

	@Override
	public DENNTourInterface getTour(NodeInterface a, NodeInterface b) throws ClientMovedOnException {
		DENNTour dt = new DENNTour(rawContainer.getName());
		dt.startTimeMeasure();

		int[] tour = new int[nodeInterfaces.length];
		boolean[] nodeVisited = new boolean[nodeInterfaces.length];
		int[] nextTourIndices = new int[2];
		NodeInterface[] currentNodes = new NodeInterface[2];

		tour[0] = a.getID();
		tour[1] = b.getID();

		nextTourIndices[0] = tour.length - 1;
		nextTourIndices[1] = 2;

		nodeVisited[a.getID() - 1] = true;
		nodeVisited[b.getID() - 1] = true;

		currentNodes[0] = a;
		currentNodes[1] = b;

		dt.addDistance(nodeDistanceInterface.getWeight(a, b));

		if (!disableEvent && edgeAddedEvent != null)
			edgeAddedEvent.added(currentNodes[0].getID(), currentNodes[1].getID(), (int) dt.getDistance(), this);

		while (nextTourIndices[0] >= nextTourIndices[1]) {
			NodeDistanceSetInterface[] near = { findNearestUnvisitedNode(currentNodes[0], nodeVisited),
					findNearestUnvisitedNode(currentNodes[1], nodeVisited) };

			if (near[0].getDistance() < near[1].getDistance()) {
				if (!disableEvent && edgeAddedEvent != null)
					edgeAddedEvent.added(currentNodes[0].getID(), near[0].getNodeInterface().getID(),
							near[0].getDistance(), this);

				nodeVisited[near[0].getNodeInterface().getID() - 1] = true;
				currentNodes[0] = near[0].getNodeInterface();
				tour[nextTourIndices[0]] = currentNodes[0].getID();
				nextTourIndices[0]--;
				dt.addDistance(near[0].getDistance());
			} else {
				if (!disableEvent && edgeAddedEvent != null)
					edgeAddedEvent.added(currentNodes[1].getID(), near[1].getNodeInterface().getID(),
							near[1].getDistance(), this);

				nodeVisited[near[1].getNodeInterface().getID() - 1] = true;
				currentNodes[1] = near[1].getNodeInterface();
				tour[nextTourIndices[1]] = currentNodes[1].getID();
				nextTourIndices[1]++;
				dt.addDistance(near[1].getDistance());
			}
		}

		final int lastDist = nodeDistanceInterface.getWeight(currentNodes[0], currentNodes[1]);

		dt.addDistance(lastDist);

		if (!disableEvent && edgeAddedEvent != null)
			edgeAddedEvent.added(currentNodes[0].getID(), currentNodes[1].getID(), lastDist, this);

		dt.setTour(tour);
		dt.stopTimeMeasure();

		return dt;
	}

	private final NodeDistanceSetInterface findNearestUnvisitedNode(NodeInterface a) {
		return findNearestUnvisitedNode(a, null);
	}

	private final NodeDistanceSetInterface findNearestUnvisitedNode(NodeInterface a, boolean[] visited) {
		int minDistance = -1;
		NodeInterface b = null;

		for (int i = 0; i < nodeInterfaces.length; i++) {
			if ((visited != null && visited[i]) || nodeInterfaces[i] == a)
				continue;

			final int cDist = nodeDistanceInterface.getWeight(a, nodeInterfaces[i]);

			if (minDistance == -1 || cDist < minDistance) {
				b = nodeInterfaces[i];
				minDistance = cDist;
			}
		}

		return new NodeDistanceSet(b, minDistance);
	}

	private final NodeInterface getNodeInterfaceById(int id) {
		if (nodeInterfaces[id - 1].getID() == id)
			return nodeInterfaces[id - 1];

		for (int i = 0; i < nodeInterfaces.length; i++)
			if (nodeInterfaces[i].getID() == id)
				return nodeInterfaces[i];

		return null;
	}

	private boolean assertCorrectNodeOrder() {
		boolean changed = false;

		for (int i = 0; i < nodeInterfaces.length; i++) {
			if (nodeInterfaces[i].getID() != i + 1) {
				changed = true;
				NodeInterface tmp = nodeInterfaces[i];
				nodeInterfaces[i] = nodeInterfaces[tmp.getID() - 1];
				nodeInterfaces[tmp.getID() - 1] = tmp;
			}
		}

		if (changed)
			return assertCorrectNodeOrder();

		return changed;
	}

	@Override
	public TSP_RawData getRawData() {
		return rawContainer;
	}

}
