package com.algo.fun;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Graph Adjacency List representation.
 */
public class ALGraph<T> {

	private final Map<T, Set<T>> adjacencyList;
	private final boolean isDirected;

	public ALGraph(final boolean isDirected) {
		this.adjacencyList = new HashMap<>();
		this.isDirected = isDirected;
	}

	/**
	 * Add edge to graph.
	 */
	public ALGraph<T> addEdge(final T vertex1, final T vertex2) {
		update(vertex1, vertex2);
		if (!isDirected) {
			update(vertex2, vertex1);
		}
		return this;
	}

	/**
	 * The vertex links.
	 */
	public Set<T> getList(T vertex) {
		return adjacencyList.get(vertex);
	}

	private void update(final T vertex1, final T vertex2) {
		Set<T> vertices = null;
		if (adjacencyList.containsKey(vertex1)) {
			vertices = adjacencyList.get(vertex1);
		} else {
			vertices = new HashSet<>();
			adjacencyList.put(vertex1, vertices);
		}
		vertices.add(vertex2);
	}

	/**
	 * Print the graph.
	 */
	public void display() {
		for (Map.Entry<T, Set<T>> entry : adjacencyList.entrySet()) {
			System.out.println(entry.getKey().toString() + "->" + entry.getValue().toString());
		}
	}

	/**
	 * main.
	 */
	public static void main(final String[] args) {
		ALGraph<Integer> ugraph = new ALGraph<>(false);
		ugraph.addEdge(1, 5).addEdge(1, 2).addEdge(2, 5).addEdge(5, 4).addEdge(2, 4).addEdge(2, 3).addEdge(3, 4);
		ugraph.display();

		System.out.println("");

		ALGraph<Integer> dgraph = new ALGraph<>(true);
		dgraph.addEdge(1, 2).addEdge(1, 4).addEdge(4, 2).addEdge(2, 5).addEdge(5, 4).addEdge(3, 5).addEdge(3, 6)
		        .addEdge(6, 6);
		dgraph.display();
	}

}
