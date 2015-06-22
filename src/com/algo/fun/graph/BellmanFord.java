package com.algo.fun.graph;

import java.util.HashSet;
import java.util.Set;

/**
 * Single source shortest path with negative weight cycles.
 */
public class BellmanFord<T> {

	private final Set<Edge<T>> edges;

	private final Set<Vertex<T>> vertices;

	public BellmanFord() {
		this.edges = new HashSet<>();
		this.vertices = new HashSet<>();
	}

	/**
	 * Add an edge.
	 */
	public BellmanFord<T> addEdge(final Vertex<T> vertex1, final Vertex<T> vertex2, final int weight) {
		vertices.add(vertex1);
		vertices.add(vertex2);
		Edge<T> edge = new Edge<>(vertex1, vertex2, true, weight);
		edges.add(edge);
		return this;
	}

	/**
	 * Single source shortest path.
	 */
	public boolean compute(final Vertex<T> source) {
		boolean result = true;
		source.setKey(0);
		source.setParent(null);
		for (int i = 1; i <= vertices.size() - 1; i++) {
			for (Edge<T> edge : edges) {
				relax(edge);
			}
		}

		// check for negative weight cycles.
		for (Edge<T> edge : edges) {
			if (hasNegativeCycle(edge)) {
				result = false;
				break;
			}
		}
		return result;
	}

	private boolean hasNegativeCycle(final Edge<T> edge) {
		boolean result = false;
		if (edge.getVertex2().getKey() > edge.getVertex1().getKey() + edge.getWeight()) {
			result = true;
		}
		return result;
	}

	private void relax(final Edge<T> edge) {
		if (edge.getVertex2().getKey() > edge.getVertex1().getKey() + edge.getWeight()) {
			edge.getVertex2().setKey(edge.getVertex1().getKey() + edge.getWeight());
			edge.getVertex2().setParent(edge.getVertex1());
		}
	}

	/**
	 * Print path from source.
	 */
	public String printPath(final Vertex<T> vertex) {
		String str = "";
		if (vertex != null) {
			str = "[" + vertex.getValue() + "]" + "-->" + printPath(vertex.getParent());
		}
		return str;
	}

	/**
	 * main.
	 */
	public static void main(final String[] args) {
		Vertex<String> s = new Vertex<>("s");
		Vertex<String> t = new Vertex<>("t");
		Vertex<String> x = new Vertex<>("x");
		Vertex<String> z = new Vertex<>("z");
		Vertex<String> y = new Vertex<>("y");

		Set<Vertex<String>> vertices = new HashSet<>();
		vertices.add(s);
		vertices.add(t);
		vertices.add(x);
		vertices.add(z);
		vertices.add(y);

		BellmanFord<String> bf = new BellmanFord<>();
		bf.addEdge(s, t, 6).addEdge(t, x, 5).addEdge(x, t, -2).addEdge(s, y, 7).addEdge(t, y, 8).addEdge(y, z, 9)
		        .addEdge(z, x, 7).addEdge(z, s, 2).addEdge(y, x, -3).addEdge(t, z, -4);

		boolean result = bf.compute(s);

		if (result) {
			for (Vertex<String> vertex : vertices) {
				System.out.println("---" + vertex.toString() + "---");
				System.out.println(bf.printPath(vertex));
			}
		} else {
			System.out.println("Has negative weight cycle");
		}
	}

}
