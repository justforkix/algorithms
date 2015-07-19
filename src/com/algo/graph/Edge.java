package com.algo.graph;

/**
 * Graph edge.
 */
public class Edge<T> implements Comparable<Edge<T>> {
	private final Vertex<T> vertex1;
	private final Vertex<T> vertex2;
	private final boolean isDirected;
	private final int weight;

	public Edge(final Vertex<T> vertex1, final Vertex<T> vertex2, final boolean isDirected, final int weight) {
		super();
		this.vertex1 = vertex1;
		this.vertex2 = vertex2;
		this.isDirected = isDirected;
		this.weight = weight;
	}

	public Vertex<T> getVertex1() {
		return vertex1;
	}

	public Vertex<T> getVertex2() {
		return vertex2;
	}

	public boolean isDirected() {
		return isDirected;
	}

	public int getWeight() {
		return weight;
	}

	@Override
	public int compareTo(final Edge<T> other) {
		return this.weight - other.weight;
	}

	@Override
	public String toString() {
		return "(" + vertex1.getValue() + "->" + vertex2.getValue() + "," + getWeight() + ")";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (isDirected ? 1231 : 1237);
		result = prime * result + ((vertex1 == null) ? 0 : vertex1.hashCode());
		result = prime * result + ((vertex2 == null) ? 0 : vertex2.hashCode());
		result = prime * result + weight;
		return result;
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		Edge other = (Edge) obj;
		if (isDirected != other.isDirected) {
			return false;
		}
		if (vertex1 == null) {
			if (other.vertex1 != null) {
				return false;
			}
		} else if (!vertex1.equals(other.vertex1)) {
			return false;
		}
		if (vertex2 == null) {
			if (other.vertex2 != null) {
				return false;
			}
		} else if (!vertex2.equals(other.vertex2)) {
			return false;
		}
		if (weight != other.weight) {
			return false;
		}
		return true;
	}

}
