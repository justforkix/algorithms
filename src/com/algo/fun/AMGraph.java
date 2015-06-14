package com.algo.fun;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Graph Adjacency Matrix representation.
 */
public class AMGraph<T> {

	private final boolean[][] adjacencyMatrix;
	private final Map<T, Integer> indexMap;
	private final Map<Integer, T> reverseMap;
	private final boolean isDirected;
	private int index;
	private final int size;

	public AMGraph(final int size, final boolean isDirected) {
		this.adjacencyMatrix = new boolean[size][size];
		this.indexMap = new HashMap<>();
		this.reverseMap = new HashMap<>();
		this.isDirected = isDirected;
		this.size = size;
	}

	/**
	 * Add vertex to graph.
	 */
	public AMGraph<T> addVertex(final T vertex) {
		if (!indexMap.containsKey(vertex)) {
			if (index < size) {
				indexMap.put(vertex, index);
				reverseMap.put(index, vertex);
				index++;
			} else {
				throw new RuntimeException("index out of range");
			}
		}
		return this;
	}

	/**
	 * Add edge to graph.
	 */
	public AMGraph<T> addEdge(final T vertex1, final T vertex2) {
		update(vertex1, vertex2);
		if (!isDirected) {
			update(vertex2, vertex1);
		}
		return this;
	}

	/**
	 * Check if there is an edge between vertices.
	 */
	public boolean hasEdge(final T vertex1, final T vertex2) {
		int index1 = indexMap.get(vertex1);
		int index2 = indexMap.get(vertex2);
		return adjacencyMatrix[index1][index2];
	}

	private void update(final T vertex1, final T vertex2) {
		int index1 = indexMap.get(vertex1);
		int index2 = indexMap.get(vertex2);
		adjacencyMatrix[index1][index2] = true;
	}

	/**
	 * Print the graph.
	 */
	public void display() {
		for (Map.Entry<Integer, T> entry : reverseMap.entrySet()) {
			boolean[] links = adjacencyMatrix[entry.getKey()];
			Set<T> vertices = new HashSet<T>();
			for (int j = 0; j < links.length; j++) {
				if (links[j]) {
					vertices.add(reverseMap.get(j));
				}
			}
			System.out.println(entry.getValue().toString() + "->" + vertices.toString());
		}
	}

	/**
	 * main.
	 */
	public static void main(final String[] args) {
		AMGraph<Integer> ugraph = new AMGraph<>(5, false);
		ugraph.addVertex(1).addVertex(2).addVertex(3).addVertex(4).addVertex(5);
		ugraph.addEdge(1, 5).addEdge(1, 2).addEdge(2, 5).addEdge(5, 4).addEdge(2, 4).addEdge(2, 3).addEdge(3, 4);
		ugraph.display();

		System.out.println("");

		AMGraph<Integer> dgraph = new AMGraph<>(6, true);
		dgraph.addVertex(1).addVertex(2).addVertex(3).addVertex(4).addVertex(5).addVertex(6);
		dgraph.addEdge(1, 2).addEdge(1, 4).addEdge(4, 2).addEdge(2, 5).addEdge(5, 4).addEdge(3, 5).addEdge(3, 6)
		.addEdge(6, 6);
		dgraph.display();

	}

}
