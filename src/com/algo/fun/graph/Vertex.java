package com.algo.fun.graph;

/**
 * Graph Vertex.
 */
public class Vertex<T> {

	private static final int MAX = 50;

	public static enum COLOR {
		WHITE, GRAY, BLACK
	};

	private final T value;
	private COLOR color;
	private int distance;
	private int first;
	private int last;
	private Vertex<T> parent;
	private int key;

	public Vertex(final T value) {
		this.value = value;
		this.color = COLOR.WHITE;
		this.key = MAX;
	}

	public Vertex(final T value, final int distance) {
		this.value = value;
		this.color = COLOR.WHITE;
		this.key = MAX;
		this.distance = distance;
	}

	public T getValue() {
		return value;
	}

	public COLOR getColor() {
		return color;
	}

	public void setColor(final COLOR color) {
		this.color = color;
	}

	public int getDistance() {
		return distance;
	}

	public void setDistance(final int distance) {
		this.distance = distance;
	}

	public int getFirst() {
		return first;
	}

	public void setFirst(final int first) {
		this.first = first;
	}

	public int getLast() {
		return last;
	}

	public void setLast(final int last) {
		this.last = last;
	}

	public Vertex<T> getParent() {
		return parent;
	}

	public void setParent(final Vertex<T> parent) {
		this.parent = parent;
	}

	public int getKey() {
		return key;
	}

	public void setKey(final int key) {
		this.key = key;
	}

	public String display() {
		return "<" + getValue().toString() + ":" + "(" + getColor() + "," + getDistance() + "," + getParent() + ")"
		        + "(" + getFirst() + "," + getLast() + ")>";
	}

	@Override
	public String toString() {
		return getValue().toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((value == null) ? 0 : value.hashCode());
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
		Vertex other = (Vertex) obj;
		if (value == null) {
			if (other.value != null) {
				return false;
			}
		} else if (!value.equals(other.value)) {
			return false;
		}
		return true;
	}
}
