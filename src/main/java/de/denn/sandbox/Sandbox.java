package de.denn.sandbox;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.concurrent.TimeUnit;

import de.denn.data.TSP_DataLoader;
import de.denn.data.TourFileWriter;
import de.denn.data.model.TSP_RawData;
import de.denn.errors.ClientMovedOnException;
import de.denn.errors.TSPUnsupportedTypeException;
import de.denn.graph.DENN;
import de.denn.graph.DENNAlgorithm;
import de.denn.graph.interfaces.DENNTourInterface;
import de.denn.graph.interfaces.NodeInterface;
import de.denn.graph.nodes.Node2D;
import de.denn.graph.startnode.DENNStartNodeSelectType;
import de.denn.graph.tours.DENNTour;
import de.denn.helper.TimeStopper;

public class Sandbox {

	public static void run() throws Exception {

		final String problem = "bm33708";

		File load = new File("D:\\Arbeit\\Sonstiges\\Touren\\" + problem + ".tsp");

		DENNAlgorithm da = null;

		TSP_RawData cont = null;
		try {
			cont = TSP_DataLoader.getInstance().constructFromFile(load);
			da = DENN.getInstance().setupAlgorithm(cont, null);
		} catch (TSPUnsupportedTypeException e) {
			e.printStackTrace();
		}

		File save = new File("D:\\Arbeit\\Sonstiges\\Touren\\" + problem + ".tsp.tour");

		if(da != null)
			new TourFileWriter(da.getTourByPreference(DENNStartNodeSelectType.GEOGRAPHICAL_CENTER)).save(save);
		
		printLaufzeit(cont, da);
	}
	
	public static void printLaufzeit(TSP_RawData cont, DENNAlgorithm da) throws ClientMovedOnException {
		TimeStopper ts = new TimeStopper();
		ts.startTimeMeasure();
		
		DENNTourInterface dti = da.getTourByPreference(DENNStartNodeSelectType.FURTHEST_FROM_ALL);
		//System.out.println(new Gson().toJson(dti.getTour()).replace("[", "").replace("]", ""));
		ts.stopTimeMeasure();
		
		System.out.println(dti.getDistance() + " " + dti.getStoppedTime(TimeUnit.MILLISECONDS) + "ms " + dti.getTour()[0] + " " + dti.getTour()[1]);
		System.out.println("All: " + ts.getStoppedTime(TimeUnit.MILLISECONDS) + "ms");
	}
	
	public static void printHypothese3Implementation(TSP_RawData cont, DENNAlgorithm da) {
		int[] a = new int[cont.getDimension()];

		for (int i = 0; i < a.length; i++) {
			for(int j = i + 1; j < a.length; j++) {
				final int dist = da.getNodeDistanceInterface().getWeight(da.getNodeInterfaces()[i], da.getNodeInterfaces()[j]);
				a[i] += dist;
				a[j] += dist;
			}

			//System.out.println(a[i]);
		}
		
		int maxIndex = 0;
		
		for(int i = 1; i < a.length; i++)
			if(a[maxIndex] < a[i])
				maxIndex = i;
		
		System.out.println("MaxIndex: " + maxIndex);
		}
	
	public static void printHypothese3(TSP_RawData cont, DENNAlgorithm da) throws ClientMovedOnException {
		ArrayList<DENNTour> touren = new ArrayList<>();
		
		for (int i = 0; i < cont.getDimension(); i++) {
			DENNTour a = (DENNTour) da.getTourById(i + 1);
			touren.add(a);
			System.out.println(i + " / " + cont.getDimension());
		}

		Collections.sort(touren, new Comparator<DENNTour>() {

			@Override
			public int compare(DENNTour o1, DENNTour o2) {
				return ((int) (o1.getDistance() - o2.getDistance()));
			}
		});

		int[] a = new int[touren.size()];
		int[] b = new int[touren.size()];

		for (int i = 0; i < touren.size(); i++) {
			a[i] = (int) touren.get(i).getDistance();
			
			for(int j = 0; j < cont.getDimension(); j++) {
				if(touren.get(i).getTour()[0] - 1 == j)
					continue;
				
				b[i] += da.getNodeDistanceInterface().getWeight(da.getNodeInterfaces()[touren.get(i).getTour()[0] - 1], da.getNodeInterfaces()[j]);
			}

			System.out.println(a[i] + " " + b[i]);
		}

		// for(DENNTour t : touren)
		// System.out.printf("%d \t %d \t Länge: %d \n", t.getTour()[0], t.getTour()[1],
		// t.getDistance());

		System.out.println(Arrays.toString(a).replace("[", "").replace("]", ""));
		System.out.println(Arrays.toString(b).replace("[", "").replace("]", ""));

		// System.out.println(new LinearRegression(a, b).toString());

		System.out.println("Anzahl Touren: " + touren.size());
	}
	
	public static void printHypothese12(TSP_RawData cont, DENNAlgorithm da) throws ClientMovedOnException {
		ArrayList<DENNTour> touren = new ArrayList<>();

		Node2D avg = getAverageNode2D(da.getNodeInterfaces());

		for (int i = 0; i < cont.getDimension(); i++) {
			DENNTour a = (DENNTour) da.getTourById(i + 1);
			touren.add(a);
			System.out.println(i + " / " + cont.getDimension());
		}

		Collections.sort(touren, new Comparator<DENNTour>() {

			@Override
			public int compare(DENNTour o1, DENNTour o2) {
				return ((int) (o1.getDistance() - o2.getDistance()));
			}
		});

		int[] a = new int[touren.size()];
		int[] b = new int[touren.size()];

		for (int i = 0; i < touren.size(); i++) {
			a[i] = (int) touren.get(i).getDistance();
			b[i] = da.getNodeDistanceInterface().getWeight(da.getNodeInterfaces()[touren.get(i).getTour()[0] - 1], avg);

			System.out.println(a[i] + " " + b[i]);
		}

		// for(DENNTour t : touren)
		// System.out.printf("%d \t %d \t Länge: %d \n", t.getTour()[0], t.getTour()[1],
		// t.getDistance());

		System.out.println(Arrays.toString(a).replace("[", "").replace("]", ""));
		System.out.println(Arrays.toString(b).replace("[", "").replace("]", ""));

		// System.out.println(new LinearRegression(a, b).toString());

		System.out.println("Anzahl Touren: " + touren.size());
	}

	public static Node2D getAverageNode2D(NodeInterface[] nodes) {
		double avgX = 0;
		double avgY = 0;

		for (NodeInterface ni : nodes) {
			avgX += ((Node2D) ni).getX();
			avgY += ((Node2D) ni).getX();
		}

		return new Node2D(-1, avgX / nodes.length, avgY / nodes.length);
	}

	public static void printKommutativgesetz2(TSP_RawData cont, DENNAlgorithm da) {
		ArrayList<NodeSet> ns = new ArrayList<>();

		for (NodeInterface ni : da.getNodeInterfaces()) {
			NodeSet n = new NodeSet();
			n.a = ni;

			for (NodeInterface ni2 : da.getNodeInterfaces()) {
				if (ni != ni2)
					n.b.add(ni2);
			}
			ns.add(n);
		}

		for (NodeSet n : ns) {
			ArrayList<NodeInterface> forRemoval = new ArrayList<>();

			for (NodeInterface ni : n.b) {
				boolean remove = true;
				int dist = da.getNodeDistanceInterface().getWeight(ni, n.a);

				for (NodeInterface ni2 : n.b) {
					if (ni2 == ni)
						continue;

					if (da.getNodeDistanceInterface().getWeight(ni2, n.a) == dist) {
						remove = false;
						break;
					}
				}

				if (remove)
					forRemoval.add(ni);
			}

			for (NodeInterface ni : forRemoval)
				n.b.remove(ni);
		}

		for (NodeSet n : ns) {
			if (n.b.size() == 0)
				continue;

			for (NodeInterface ni : n.b)
				System.out.println(
						n.a.getID() + " <- " + ni.getID() + " = " + da.getNodeDistanceInterface().getWeight(ni, n.a));
		}
	}

	public static void printKommutativgesetz(TSP_RawData cont, DENNAlgorithm da) throws Exception {
		ArrayList<DENNTour> touren = new ArrayList<>();
		// touren.add((DENNTour)
		// da.getTourByPreference(DENNStartNodeSelectType.CLOSEST_TO_OTHERS));
		// touren.add((DENNTour)
		// da.getTourByPreference(DENNStartNodeSelectType.GEOGRAPHICAL_CENTER));

		for (int i = 0; i < cont.getDimension(); i++) {
			for (int j = i + 1; j < cont.getDimension(); j++) {
				if (i == j)
					continue;

				DENNTour a = (DENNTour) da.getTourByIndex(i, j);
				DENNTour b = (DENNTour) da.getTourByIndex(j, i);

				if (a.getDistance() == b.getDistance())
					continue;

				touren.add(a);
				touren.add(b);
			}
		}

		/*
		 * Collections.sort(touren, new Comparator<DENNTour>() {
		 * 
		 * @Override public int compare(DENNTour o1, DENNTour o2) { return ((int)
		 * (o1.getDistance() - o2.getDistance())); } });
		 */

		for (DENNTour dt : touren) {

			long dist = da.getNodeDistanceInterface().getWeight(da.getNodeInterfaces()[dt.getTour()[0] - 1],
					da.getNodeInterfaces()[dt.getTour()[dt.getTour().length - 1] - 1]);

			for (int i = 0; i < dt.getTour().length - 1; i++) {
				dist += da.getNodeDistanceInterface().getWeight(da.getNodeInterfaces()[dt.getTour()[i] - 1],
						da.getNodeInterfaces()[dt.getTour()[i + 1] - 1]);
			}

			if (dist == dt.getDistance()) {
				System.out.println(dt.getDistance() + " - " + dt.getTour()[0] + "|" + dt.getTour()[1]);
				System.out.println(dist);
			} else {
				throw new Exception("Distance calculation is off!");
			}

		}
	}

}

class NodeSet {
	public NodeInterface a;
	public ArrayList<NodeInterface> b = new ArrayList<>();
}
