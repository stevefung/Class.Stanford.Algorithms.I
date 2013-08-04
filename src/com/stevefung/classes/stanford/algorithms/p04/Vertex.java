package com.stevefung.classes.stanford.algorithms.p04;

import java.util.ArrayList;

public class Vertex implements Comparable {
	
	private String name = "";
	ArrayList<DirectedEdge> inEdges = null;
	ArrayList<DirectedEdge> outEdges = null;
	
	public Vertex(String name) {
		this.name = name;
		inEdges = new ArrayList<DirectedEdge>();
		outEdges = new ArrayList<DirectedEdge>();
	}

	public String getName() {
		return name;
	}

	public ArrayList<DirectedEdge> getInEdges() {
		return inEdges;
	}

	public ArrayList<DirectedEdge> getOutEdges() {
		return outEdges;
	}
	
	public void addInEdge(DirectedEdge e) {
		inEdges.add(e);
	}
	
	public void addOutEdge(DirectedEdge e) {
		outEdges.add(e);
	}

	@Override
	public int compareTo(Object o) {
		Vertex v = (Vertex) o;
		return name.compareTo(v.getName())*-1; // *-1 to put in decreasing order
	}

	@Override
	public String toString() {
		return name;
	}
}
