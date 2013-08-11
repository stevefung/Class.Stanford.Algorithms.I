package com.stevefung.classes.stanford.algorithms.p05;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.TreeMap;

import com.stevefung.classes.stanford.algorithms.p04.Vertex;

public class DirectedGraph {

	private static boolean debug = false;

	private ArrayList<WeightedEdge> edgeList = new ArrayList<WeightedEdge>();
	private HashMap<String, Vertex> vertexList = new HashMap<String, Vertex>();
	Vertex source = null;
	private TreeMap<String, Integer> weightMap = new TreeMap<String, Integer>();
	
	public DirectedGraph(String path) {
		// Read in file

		BufferedReader br = null;
		
		try {
			String line;
			
			br = new BufferedReader(new FileReader(path));
			
			while((line = br.readLine()) != null) {
				String[] la = line.split("\\s+");
				if (la.length >= 2) {
					Vertex s;
					Vertex v;
					if (debug) System.err.println("Source: " + la[0]);
					
					if (vertexList.containsKey(la[0])) {
						s = vertexList.get(la[0]);
					} else {
						s = new Vertex(la[0]);
						vertexList.put(la[0], s);
					}
					
					for (int i = 1; i < la.length; i++) {
						String[] va = la[i].split(",");
						if (va.length != 2) {
							if (debug) System.err.println("Error in input file: " + la[i]);
							continue;
						}
						if (debug) System.err.println("\tE: " + va[0] + " - " + va[1]);
						
						if (vertexList.containsKey(va[0])) {
							v = vertexList.get(va[0]);
						} else {
							v = new Vertex(va[0]);
							vertexList.put(va[0], v);
						}
						
						WeightedEdge we = new WeightedEdge(s, v, Integer.parseInt(va[1]));
						s.addOutEdge(we);
						v.addInEdge(we);
						edgeList.add(we);
					}
					
				} else if (la.length == 1) {
					System.err.println("Source: " + la[0]);
					if (!vertexList.containsKey(la[0])) {
						vertexList.put(la[0], new Vertex(la[0]));
					}
				} else {
					System.err.println("Improper input file!");
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (br != null) {
					br.close();
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	}
	
	public boolean setSource(String v) {
		if (source != null) {
			if (source.getName().equals(v)) {
				return true;
			}
		}
		if (vertexList.containsKey(v)) {
			source = vertexList.get(v);
			return calculateShortestPaths();
		}
		return false;
	}
	
	public Vertex getSource() {
		return source;
	}
	
	public boolean calculateShortestPaths() {
		if (source == null) {
			return false;
		}
		weightMap.clear();
		
		weightMap.put(source.getName(), Integer.valueOf(0));
		HashMap<String, Vertex> processed = new HashMap<String, Vertex>();
		processed.put(source.getName(), source);
		
		while (processed.size() < vertexList.size()) {
			Integer lowestValue = Integer.MAX_VALUE;
			WeightedEdge lowestEdge = null;
			for (int i = 0; i < edgeList.size(); i++) {
				WeightedEdge e = edgeList.get(i);
				if (processed.containsKey(e.getTail().getName()) && !processed.containsKey(e.getHead().getName())) {
					Integer weight = weightMap.get(e.getTail().getName()) + e.getWeight();
					if (weight < lowestValue) {
						lowestValue = weight;
						lowestEdge = e;
					}
				}
			}
			if (lowestEdge == null) {
				// Remaining vertices are unreachable from source
				break;
			}
			weightMap.put(lowestEdge.getHead().getName(), lowestValue);
			processed.put(lowestEdge.getHead().getName(), lowestEdge.getHead());
		}
		
		return true;
	}
	
	public void printWeightMap() {
		if (source != null) {
			System.out.println("Source: " + source.getName());
			
			Iterator<String> it = weightMap.keySet().iterator();
			while (it.hasNext()) {
				String s = it.next();
				System.out.println("\t" + s + ": " + weightMap.get(s));
			}
		}
	}
	
	public Integer getShortestPath(String target) {
		if (source != null) {
			if (weightMap.containsKey(target)) {
				return weightMap.get(target);
			} else {
				return 1000000; // Per problem statement, return 1000000 as distance if no path exists
			}
		} else {
			return null;
		}
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String testfile = "test/p05/dijkstraData.txt";
//		String testfile = "test/p05/test04.in";
		
		long startTime = System.currentTimeMillis();
		DirectedGraph dg = new DirectedGraph(testfile);
		dg.setSource("1");
		dg.calculateShortestPaths();
		if (debug) dg.printWeightMap();

		System.out.print("Solution: ");
		String[] targets = {"7","37","59","82","99","115","133","165","188","197"};
		for (int i = 0; i < targets.length; i++) {
			System.out.print(dg.getShortestPath(targets[i]));
			if (i < targets.length - 1) {
				System.out.print(",");
			} else {
				System.out.println("");
			}
		}
		
		long endTime = System.currentTimeMillis();
		System.out.println("Test took " + (endTime-startTime) + " milliseconds.  i.e. " + (endTime-startTime)/(60*1000) + " minutes"
				+ " and " + ((endTime-startTime)%(60*1000)/1000) + " seconds.");
	}

}
