package com.algo.fun;

import java.util.HashSet;
import java.util.Set;

/**
 * Depth First Search.
 */
public class DFS<T> {

	private enum COLOR {
		WHITE, GRAY, BLACK
	};

	/**
	 * Vertex.
	 */
	public static class Vertex<T> {
		private final T value;
		private COLOR color;
		private int first;
		private int last;
		private Vertex<T> parent;

		public Vertex(final T value) {
			this.value = value;
			this.color = COLOR.WHITE;
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

	}

	private final ALGraph<Vertex<T>> graph;
	private int time;

	public DFS(final ALGraph<Vertex<T>> graph) {
		this.graph = graph;
		this.time = 1;
	}

	/**
	 * Compute the Depth First Forest.
	 */
	public void compute(final Set<Vertex<T>> vertices) {
		for (Vertex<T> vertex : vertices) {
			if (vertex.getColor().equals(COLOR.WHITE)) {
				computeInternal(vertex);
			}
		}
	}

	private void computeInternal(final Vertex<T> vertex) {
		vertex.setColor(COLOR.GRAY);
		vertex.setFirst(time++);
		Set<Vertex<T>> vertices = graph.getList(vertex);
		for (Vertex<T> node : vertices) {
			if (node.getColor().equals(COLOR.WHITE)) {
				node.setParent(vertex);
				computeInternal(node);
			}
		}
		vertex.setColor(COLOR.BLACK);
		vertex.setLast(time++);
	}

	/**
	 * Print path from source to vertex.
	 */
	public void printPath(final Vertex<T> vertex) {
		printPathInternal(vertex, " ");
	}

	private void printPathInternal(final Vertex<T> vertex, String s) {
		if (vertex != null) {
			s = "(" + vertex.getValue().toString() + "[" + vertex.getFirst() + "]" + s + vertex.getValue().toString()
			        + "[" + vertex.getLast() + "]" + ")";
			printPathInternal(vertex.getParent(), s);
		} else {
			System.out.println(s);
		}
	}

	/**
	 * main.
	 */
	public static void main(final String[] args) {
		Set<Vertex<String>> vertices = new HashSet<Vertex<String>>();

		Vertex<String> u = new Vertex<>("u");
		Vertex<String> v = new Vertex<>("v");
		Vertex<String> w = new Vertex<>("w");
		Vertex<String> x = new Vertex<>("x");
		Vertex<String> y = new Vertex<>("y");
		Vertex<String> z = new Vertex<>("z");

		vertices.add(u);
		vertices.add(v);
		vertices.add(w);
		vertices.add(x);
		vertices.add(y);
		vertices.add(z);

		ALGraph<Vertex<String>> graph = new ALGraph<>(true);
		graph.addEdge(u, v).addEdge(u, x).addEdge(x, v).addEdge(v, y).addEdge(y, x).addEdge(w, y).addEdge(w, z)
		        .addEdge(z, z);

		DFS<String> dfs = new DFS<>(graph);
		dfs.compute(vertices);

		for (Vertex<String> vertex : vertices) {
			dfs.printPath(vertex);
		}

	}

}
