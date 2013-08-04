package com.stevefung.classes.stanford.algorithms.p03;

public class Edge {
	private String v1;
	private String v2;
	
	public String getV1() {
		return v1;
	}
	
	public String getV2() {
		return v2;
	}
	
	public boolean updateEdge(String oldVertex, String newVertex) {
		boolean ret = false;
		
		if (v1.equals(oldVertex)) {
			v1 = newVertex;
			ret = true;
		}
		
		if (v2.equals(oldVertex)) {
			v2 = newVertex;
			ret = true;
		}

		return ret;
	}
	
	public Edge(String v1, String v2) {
		super();
		
		if (v1.compareTo(v2) < 0) {
			this.v1 = v1;
			this.v2 = v2;
		} else {
			this.v1 = v2;
			this.v2 = v1;	
		}
	}

	@Override
	public boolean equals(Object obj) {
		Edge e = (Edge) obj;
		
		return (e.getV1().equals(v1) && e.getV2().equals(v2));
	}

	@Override
	public String toString() {
		return "[" + v1 + ", " + v2 + "]";
	}

	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		return v1.hashCode() + v2.hashCode();
	}
}
