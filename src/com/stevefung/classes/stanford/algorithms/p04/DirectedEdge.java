package com.stevefung.classes.stanford.algorithms.p04;

public class DirectedEdge {

	private Vertex tail = null;
	private Vertex head = null;
	
	public DirectedEdge(Vertex tail, Vertex head) {
		this.tail = tail;
		this.head = head;
	}

	public Vertex getTail() {
		return tail;
	}

	public Vertex getHead() {
		return head;
	}
	
	
}
