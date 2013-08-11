package com.stevefung.classes.stanford.algorithms.p05;

import com.stevefung.classes.stanford.algorithms.p04.DirectedEdge;
import com.stevefung.classes.stanford.algorithms.p04.Vertex;

public class WeightedEdge extends DirectedEdge {
	
	private int weight = 1;

	public WeightedEdge(Vertex tail, Vertex head, int weight) {
		super(tail, head);
		this.weight = weight;
	}

	public int getWeight() {
		return weight;
	}
}
