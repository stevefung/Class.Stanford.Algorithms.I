package com.stevefung.classes.stanford.algorithms.p03;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Random;

public class ContractionAlgorithm {
	
	public static int getCut(ArrayList<Edge> edgeList, HashMap<String, ArrayList<Edge>> vertexList) {
		if (vertexList.size() <= 2) {
			return edgeList.size();
		}
		
		if (debug) System.err.println("\n\n=====================\nRecursed");
		if (debug) System.err.println("\nEdges: " + edgeList.toString());
		if (debug) System.err.println("Vertexes:");
		if (debug) {
			Iterator<String> itp = vertexList.keySet().iterator();
			while (itp.hasNext()) {
				String key = itp.next();
				System.err.println("\tVertex " + key + ": " + vertexList.get(key));
			}
		}
		
		Random random = new Random(System.currentTimeMillis());
		Edge edge = edgeList.get(random.nextInt(edgeList.size()));
		String newVertex = edge.getV1() + "-" + edge.getV2();
		String v1 = edge.getV1();
		String v2 = edge.getV2();
		ArrayList<Edge> e1 = vertexList.remove(edge.getV1());
		ArrayList<Edge> e2 = vertexList.remove(edge.getV2());
		ArrayList<Edge> newEdges = new ArrayList<Edge>();
		newEdges.addAll(e1);
		newEdges.addAll(e2);
		vertexList.put(newVertex, newEdges);
		HashMap<Edge, Edge> newEdgeMap = new HashMap<Edge, Edge>();
		ArrayList<Edge> newEdgeList = new ArrayList<Edge>();
		
		if (debug) System.err.println("--");
		if (debug) System.err.println("\nMerged " + edge);
		
		for (int i = 0; i < edgeList.size(); i++) {
			Edge e = edgeList.get(i);
			if (debugIterationsInternals) System.err.println("\tInspecting: " + e + " with " + v1 + "," + v2 + "=" + newVertex);
			e.updateEdge(v1, newVertex);
			if (debugIterationsInternals) System.err.println("\t\tReplaced " + v1 + "... " + e);
			e.updateEdge(v2, newVertex);
			if (debugIterationsInternals) System.err.println("\t\tReplaced " + v2 + "... " + e);
			if (e.getV1() != e.getV2()) {
				if (debugIterationsInternals) System.err.println("\t\tAdding: " + e);
				newEdgeList.add(e);
			} else {
				if (debugIterationsInternals) System.err.println("\t\tSkipping: " + e);
			}
		}
		
		edgeList.clear();
		edgeList.addAll(newEdgeList);
		
		// Remove duplicates from vertex's edge list
		newEdges = vertexList.get(newVertex);
		for (int i = 0; i < newEdges.size(); i++) {
			Edge e = newEdges.get(i);
			if (e.getV1().equals(e.getV2())) {
				newEdges.remove(i);
				i--;
			}
		}
		
		if (debug) System.err.println("Edges: " + edgeList.toString());
		if (debug) System.err.println("Vertexes:");
		if (debug)  {
			Iterator<String> itp = vertexList.keySet().iterator();
			while (itp.hasNext()) {
				String key = itp.next();
				System.err.println("\tVertex " + key + ": " + vertexList.get(key));
			}
		}
		
		return getCut(edgeList, vertexList);
	}
	
	public static int getCutOptimized(ArrayList<Edge> edgeList, HashMap<String, ArrayList<Edge>> vertexList) {
		if (vertexList.size() <= 2) {
			return edgeList.size();
		}
		
		if (debug) System.err.println("\n\n=====================\nRecursed");
		if (debug) System.err.println("\nEdges: " + edgeList.toString());
		if (debug) System.err.println("Vertexes:");
		if (debug) {
			Iterator<String> itp = vertexList.keySet().iterator();
			while (itp.hasNext()) {
				String key = itp.next();
				System.err.println("\tVertex " + key + ": " + vertexList.get(key));
			}
		}
		
		Random random = new Random(System.currentTimeMillis());
		Edge edge = edgeList.get(random.nextInt(edgeList.size()));
		String newVertex = edge.getV1() + "-" + edge.getV2();
		String v1 = edge.getV1();
		String v2 = edge.getV2();
		ArrayList<Edge> e1 = vertexList.remove(edge.getV1());
		ArrayList<Edge> e2 = vertexList.remove(edge.getV2());
		ArrayList<Edge> newEdges = new ArrayList<Edge>();
		newEdges.addAll(e1);
		newEdges.addAll(e2);
		vertexList.put(newVertex, newEdges);
		HashMap<Edge, Edge> newEdgeMap = new HashMap<Edge, Edge>();
//		ArrayList<Edge> newEdgeList = new ArrayList<Edge>();
		
		if (debug) System.err.println("--");
		if (debug) System.err.println("\nMerged " + edge);
		
		for (int i = 0; i < vertexList.get(newVertex).size(); i++) {
			Edge e = vertexList.get(newVertex).get(i);
			if (debugIterationsInternals) System.err.println("\tInspecting: " + e + " with " + v1 + "," + v2 + "=" + newVertex);
			e.updateEdge(v1, newVertex);
			if (debugIterationsInternals) System.err.println("\t\tReplaced " + v1 + "... " + e);
			e.updateEdge(v2, newVertex);
			if (debugIterationsInternals) System.err.println("\t\tReplaced " + v2 + "... " + e);
//			if (e.getV1() != e.getV2()) {
//				if (debugIterationsInternals) System.err.println("\t\tAdding: " + e);
//				newEdgeList.add(e);
//			} else {
//				if (debugIterationsInternals) System.err.println("\t\tSkipping: " + e);
//			}
		}
		
//		edgeList.clear();
//		edgeList.addAll(newEdgeList);
		
		// Remove duplicates from vertex's edge list
		newEdges = vertexList.get(newVertex);
		for (int i = 0; i < newEdges.size(); i++) {
			Edge e = newEdges.get(i);
			if (e.getV1().equals(e.getV2())) {
				if (debug) System.err.println("Removing " + newEdges.get(i) + " as self loop");
				edgeList.remove(newEdges.remove(i));
				i--;
			}
		}
		
		if (debug) System.err.println("Edges: " + edgeList.toString());
		if (debug) System.err.println("Vertexes:");
		if (debug)  {
			Iterator<String> itp = vertexList.keySet().iterator();
			while (itp.hasNext()) {
				String key = itp.next();
				System.err.println("\tVertex " + key + ": " + vertexList.get(key));
			}
		}
		
		return getCut(edgeList, vertexList);
	}
	
	public static ArrayList<Edge> copyEdgeList(ArrayList<Edge> edgeList) {
		ArrayList<Edge> ret = new ArrayList<Edge>();
		
		for (int i = 0; i < edgeList.size(); i++) {
			Edge e = edgeList.get(i);
			ret.add(new Edge(e.getV1(), e.getV2()));
		}
		
		return ret;
	}
	
	public static HashMap<String, ArrayList<Edge>> copyVertexList(ArrayList<Edge> edgeList, HashMap<String, ArrayList<Edge>> vertexList) {
		HashMap<String, ArrayList<Edge>> ret = new HashMap<String, ArrayList<Edge>>();
		
		Iterator<String> it = vertexList.keySet().iterator();
		while(it.hasNext()) {
			String key = it.next();
			ArrayList<Edge> oldArray = vertexList.get(key);
			ArrayList<Edge> newArray = new ArrayList<Edge>();
			for (int i = 0; i < oldArray.size(); i++) {
				newArray.add(edgeList.get(edgeList.indexOf(oldArray.get(i))));
			}
			ret.put(key, newArray);
		}
		
		return ret;
	}
	
	private static String testfile = "test/p03/kargerMinCut.txt";
//	private static String testfile = "test/p03/test02.txt";
	private static boolean debug = false;
	private static boolean debugIterationsInternals = false;
	private static int maxTests = 100;
	private static boolean variableTestLength = false;
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		int min = -1;
		long startTime = System.currentTimeMillis();
		
		// Read in file
		BufferedReader br = null;
		HashMap<String, ArrayList<Edge>> vertexList = new HashMap<String, ArrayList<Edge>>();
		ArrayList<Edge> edgeList = new ArrayList<Edge>();
		int count = 0;
		
		try {
			String line;
			
			br = new BufferedReader(new FileReader(testfile));
			
			while((line = br.readLine()) != null) {
				String arr[] = line.split("\\s+");
				String v = arr[0];
				ArrayList<Edge> el = new ArrayList<Edge>();
				for (int i = 1; i < arr.length; i++) {
					Edge e = new Edge(v, arr[i]);
					count++;
					if (debug) System.err.print(e + " ");
					if (!edgeList.contains(e)) {
						edgeList.add(e);
					} else {
						if (debug) System.err.println("Edge " + e + " already exists");
						e = edgeList.get(edgeList.indexOf(e));
					}
					el.add(e);
				}
				vertexList.put(v, el);
				if (debug) System.err.println("");
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
		
		int numVertices = vertexList.size();
		if (debug) System.err.println("Read in " + count + " edges.");
		if (debug) System.err.println("Read in " + edgeList.size() + " unique edges.");
		
		if (debug) {
			System.err.println("\nEdges: " + edgeList.toString());
			System.err.println("Vertexes:");
			Iterator<String> it = vertexList.keySet().iterator();
			while (it.hasNext()) {
				String key = it.next();
				System.err.println("Vertex " + key + ": " + vertexList.get(key));
			}
		}
		
			
		for (int tests = 0; tests < maxTests; tests++) {
			if (debug) System.err.println("\n\nRunning Algorithm\n\n");

			ArrayList<Edge> cEdgeList = copyEdgeList(edgeList);
			HashMap<String, ArrayList<Edge>> cVertexList = copyVertexList(cEdgeList, vertexList);
//			int result = ContractionAlgorithm.getCut(cEdgeList, cVertexList);
			int result = ContractionAlgorithm.getCutOptimized(cEdgeList, cVertexList);
			
			if (debug) System.err.println("\t\tResult of run: " + result);
			if (min < 0 || result < min) {
				min = result;
			}
			if (variableTestLength && maxTests < numVertices*numVertices) {
				maxTests = numVertices*numVertices;
			}
		}
		
		long endTime = System.currentTimeMillis();
		System.out.println("Test took " + (endTime-startTime) + " milliseconds.  i.e. " + (endTime-startTime)/(60*1000) + " minutes"
				+ " and " + ((endTime-startTime)%(60*1000)/1000) + " seconds.");
		System.out.println("\n\nResult after " + maxTests + " iterations: " + min);

	}

}
