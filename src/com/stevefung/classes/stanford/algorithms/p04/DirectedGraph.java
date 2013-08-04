package com.stevefung.classes.stanford.algorithms.p04;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Stack;

public class DirectedGraph {
	
	private static boolean debug = false;

	private ArrayList<DirectedEdge> edgeList = new ArrayList<DirectedEdge>();
	private HashMap<String, Vertex> vertexList = new HashMap<String, Vertex>();
	
	public DirectedGraph(String path) {
		// Read in file

		BufferedReader br = null;
		
		try {
			String line;
			
			br = new BufferedReader(new FileReader(path));
			
			while((line = br.readLine()) != null) {
				String[] s = line.split("\\s+");
				if (s.length >= 2) {
					Vertex v0;
					Vertex v1;
					
					if (debug) System.err.println("Read in: " + s[0] + "->" + s[1]);
					if (vertexList.containsKey(s[0])) {
						v0 = vertexList.get(s[0]);
					} else {
						v0 = new Vertex(s[0]);
						vertexList.put(s[0], v0);
					}
				
					if (vertexList.containsKey(s[1])) {
						v1 = vertexList.get(s[1]);
					} else {
						v1 = new Vertex(s[1]);
						vertexList.put(s[1], v1);
					}
					
					DirectedEdge de = new DirectedEdge(v0, v1);
					edgeList.add(de);
					
					v0.addOutEdge(de);
					v1.addInEdge(de);
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
	
	private HashMap<String, Vertex> finishingTimes;
	private HashMap<String, ArrayList<Vertex>> leaders;
	HashMap<String, String> visited;
	
	public void calculateStronglyConnectedComponents() {
		finishingTimes = new HashMap<String, Vertex>();
		visited = new HashMap<String, String>();
		
		for (int i = vertexList.size(); i > 0; i--) {
			if (!visited.containsKey(""+i)) {
				Vertex v = vertexList.get(""+i);	// Hack for our input space
				if (debug) System.err.println("Starting reverse pass at vertex " + v.getName());
				reverseDFS(v);
			}
		}
		if (debug) System.err.println("Finishing times: " + finishingTimes.toString());
		
		visited.clear();
		leaders = new HashMap<String, ArrayList<Vertex>>();
		for (int i = finishingTimes.size(); i > 0; i--) {
			Vertex v = finishingTimes.get(""+i);
			if (!visited.containsKey(v.getName())) {
				if (debug) System.err.println("Starting forward pass at finishing time " + i + " = vertex " + v.getName());
				forwardDFS(v, v.getName());
			}
		}
		
		ArrayList<Integer> sccSizes = new ArrayList<Integer>();
		Iterator<Entry<String, ArrayList<Vertex>>> it = leaders.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry<String, ArrayList<Vertex>> pairs = (Entry<String, ArrayList<Vertex>>)it.next();
			sccSizes.add(pairs.getValue().size());
		}
		Collections.sort(sccSizes);
		System.err.println("Sizes: " + sccSizes.toString());
		System.out.print("Top 5: [");
		for (int i = 1; i <= 5; i++) {
			if (sccSizes.size() >= i) {
				System.out.print(sccSizes.get(sccSizes.size()-i));
			} else {
				System.out.print("0");
			}
			if (i < 5) {
				System.out.print(",");
			}
		}
		System.out.println("]");
	}
		
	private void reverseDFS(Vertex v) {
		visited.put(v.getName(), null);
		for (int i = 0; i < v.getInEdges().size(); i++) {
			if (!visited.containsKey(v.getInEdges().get(i).getTail().getName())) {
				reverseDFS(v.getInEdges().get(i).getTail());
			}
		}
		finishingTimes.put(""+(finishingTimes.size()+1), v);
	}
	
	private void forwardDFS(Vertex v, String leaderName) {
		visited.put(v.getName(), v.getName());
		if (debug) System.err.println("\tVisiting " + v.getName());
		ArrayList<Vertex> list;
		if (leaders.containsKey(leaderName)) {
			list = leaders.get(leaderName);
		} else {
			list = new ArrayList<Vertex>();
		}
		list.add(v);
		leaders.put(leaderName, list);
		for (int i = 0; i < v.getOutEdges().size(); i++) {
			Vertex t = v.getOutEdges().get(i).getHead();
			if (!visited.containsKey(t.getName())) {
				forwardDFS(t, leaderName);
			}
		}
	}
	
	public void calculateStronglyConnectedComponentsIterative() {
		finishingTimes = new HashMap<String, Vertex>();
		visited = new HashMap<String, String>();
		Stack<Vertex> stack = new Stack<Vertex>();
		
		if (debug) System.err.println("Starting iterative algorithm");
		
		// Reverse DFS pass
		for (int i = vertexList.size(); i > 0; i--) {
			if (!visited.containsKey(""+i)) {
				if (debug) System.err.println("Starting reverse pass at vertex " + vertexList.get(""+i) + " (" + i + ")");
				stack.push(vertexList.get(""+i));
				while (!stack.isEmpty()) {
					boolean recurse = false;
					Vertex v = stack.peek();
					if (debug) System.err.println("\tChecking vertex " + v.getName());
					visited.put(v.getName(), null);
					for (int j = v.getInEdges().size()-1; j >= 0; j--) {
						if (!visited.containsKey(v.getInEdges().get(j).getTail().getName())) {
							if (debug) System.err.println("\t\tAdding vertex " + v.getInEdges().get(j).getTail().getName() + " to stack");
							stack.push(v.getInEdges().get(j).getTail());
							visited.put(v.getInEdges().get(j).getTail().getName(), null);
							recurse = true;
						}
					}
					if (!recurse) {
						if (debug) System.err.println("\t\tFinish time for vertex " + v.getName() + ": " + (finishingTimes.size()+1));
						finishingTimes.put(""+(finishingTimes.size()+1), v);
						stack.pop();
					}
				}
			}
		}
		
		if (debug) System.err.println("Finishing times: " + finishingTimes.toString());
		
		// Forward DFS pass
		
		visited.clear();
		stack.clear();
		leaders = new HashMap<String, ArrayList<Vertex>>();
		for (int i = finishingTimes.size(); i > 0; i--) {
			Vertex s = finishingTimes.get(""+i);
			if (!visited.containsKey(s.getName())) {
				if (debug) System.err.println("Starting forward pass at finishing time " + i + " = vertex " + s.getName());
				stack.push(s);
				while(!stack.isEmpty()) {
					Vertex v = stack.pop();
					if (debug) System.err.println("\tChecking vertex " + v.getName());
					visited.put(v.getName(), null);
					ArrayList<Vertex> list;
					if (leaders.containsKey(s.getName())) {
						list = leaders.get(s.getName());
					} else {
						list = new ArrayList<Vertex>();
					}
					list.add(v);
					leaders.put(s.getName(), list);
					for (int j = v.getOutEdges().size()-1; j >= 0; j--) {
						if (!visited.containsKey(v.getOutEdges().get(j).getHead().getName())) {
							stack.push(v.getOutEdges().get(j).getHead());
							visited.put(v.getOutEdges().get(j).getHead().getName(), null);
						}
					}
				}
			}
		}
		
		// Checking sizes
		
		ArrayList<Integer> sccSizes = new ArrayList<Integer>();
		Iterator<Entry<String, ArrayList<Vertex>>> it = leaders.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry<String, ArrayList<Vertex>> pairs = (Entry<String, ArrayList<Vertex>>)it.next();
			sccSizes.add(pairs.getValue().size());
		}
		Collections.sort(sccSizes);
		System.err.println("Sizes: " + sccSizes.toString());
		System.out.print("Top 5: [");
		for (int i = 1; i <= 5; i++) {
			if (sccSizes.size() >= i) {
				System.out.print(sccSizes.get(sccSizes.size()-i));
			} else {
				System.out.print("0");
			}
			if (i < 5) {
				System.out.print(",");
			}
		}
		System.out.println("]");
	}

	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
//		String testfile = "test/p04/test01.in";
		String testfile = "test/p04/SCC.txt";
		// NOTE: with graded break-test, due to stack overflows with eclipse, run with options: -Xss4m -XX:+UseSerialGC
		//			java -Xss8m -XX:+UseSerialGC com.stevefung.classes.stanford.algorithms.p04.DirectedGraph 2>err.log 1>out.log
		
		long startTime = System.currentTimeMillis();
		DirectedGraph dg = new DirectedGraph(testfile);
		dg.calculateStronglyConnectedComponents();
//		dg.calculateStronglyConnectedComponentsIterative();
		long endTime = System.currentTimeMillis();
		System.out.println("Test took " + (endTime-startTime) + " milliseconds.  i.e. " + (endTime-startTime)/(60*1000) + " minutes"
				+ " and " + ((endTime-startTime)%(60*1000)/1000) + " seconds.");
	}

}
