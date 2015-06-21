package com.algo.fun.graph;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Graph Adjacency Matrix representation.
 */
public class AMGraph<T> implements Cloneable {

	private final boolean[][] adjacencyMatrix;
	private final Map<Vertex<T>, Integer> indexMap;
	private final Map<Integer, Vertex<T>> reverseMap;
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
	private void addVertex(final Vertex<T> vertex) {
		if (!indexMap.containsKey(vertex)) {
			if (index < size) {
				indexMap.put(vertex, index);
				reverseMap.put(index, vertex);
				index++;
			} else {
				throw new RuntimeException("index out of range");
			}
		}
	}

	/**
	 * Add edge to graph.
	 */
	public AMGraph<T> addEdge(final Vertex<T> vertex1, final Vertex<T> vertex2) {
		addVertex(vertex1);
		addVertex(vertex2);

		update(vertex1, vertex2);
		if (!isDirected) {
			update(vertex2, vertex1);
		}
		return this;
	}

	/**
	 * The transpose graph.
	 */
	public AMGraph<T> transpose() {
		AMGraph<T> tgraph = new AMGraph<>(size, isDirected);
		Map<Vertex<T>, Vertex<T>> map = new HashMap<>();
		for (int i = 0; i < adjacencyMatrix.length; i++) {
			for (int j = 0; j < adjacencyMatrix[i].length; j++) {
				if (adjacencyMatrix[i][j]) {
					Vertex<T> vertex1 = getVertex(map, reverseMap.get(j));
					Vertex<T> vertex2 = getVertex(map, reverseMap.get(i));
					tgraph.addEdge(vertex1, vertex2);
				}
			}
		}
		return tgraph;
	}

	private Vertex<T> getVertex(final Map<Vertex<T>, Vertex<T>> map, final Vertex<T> vertex) {
		Vertex<T> result = map.get(vertex);
		if (result == null) {
			result = new Vertex<>(vertex.getValue());
			map.put(result, result);
		}
		return result;
	}

	/**
	 * Check if there is an edge between vertices.
	 */
	public boolean hasEdge(final Vertex<T> vertex1, final Vertex<T> vertex2) {
		int index1 = indexMap.get(vertex1);
		int index2 = indexMap.get(vertex2);
		return adjacencyMatrix[index1][index2];
	}

	private void update(final Vertex<T> vertex1, final Vertex<T> vertex2) {
		int index1 = indexMap.get(vertex1);
		int index2 = indexMap.get(vertex2);
		adjacencyMatrix[index1][index2] = true;
	}

	/**
	 * The vertices in the graph.
	 */
	public Set<Vertex<T>> vertices() {
		return indexMap.keySet();
	}

	/**
	 * Print the graph.
	 */
	public void displayGraph() {
		display(adjacencyMatrix);
	}

	private void display(final boolean[][] matrix) {
		for (Map.Entry<Integer, Vertex<T>> entry : reverseMap.entrySet()) {
			boolean[] links = matrix[entry.getKey()];
			Set<Vertex<T>> vertices = new HashSet<Vertex<T>>();
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
	public static void main(final String[] args) throws Exception {
		Vertex<Integer> vertex1 = new Vertex<>(1);
		Vertex<Integer> vertex2 = new Vertex<>(2);
		Vertex<Integer> vertex3 = new Vertex<>(3);
		Vertex<Integer> vertex4 = new Vertex<>(4);
		Vertex<Integer> vertex5 = new Vertex<>(5);
		Vertex<Integer> vertex6 = new Vertex<>(6);

		AMGraph<Integer> ugraph = new AMGraph<>(5, false);
		ugraph.addEdge(vertex1, vertex5).addEdge(vertex1, vertex2).addEdge(vertex2, vertex5).addEdge(vertex5, vertex4)
		        .addEdge(vertex2, vertex4).addEdge(vertex2, vertex3).addEdge(vertex3, vertex4);
		ugraph.displayGraph();
		System.out.println("");
		ugraph.transpose().displayGraph();

		System.out.println("----------");

		AMGraph<Integer> dgraph = new AMGraph<>(6, true);
		dgraph.addEdge(vertex1, vertex2).addEdge(vertex1, vertex4).addEdge(vertex4, vertex2).addEdge(vertex2, vertex5)
		        .addEdge(vertex5, vertex4).addEdge(vertex3, vertex5).addEdge(vertex3, vertex6)
		        .addEdge(vertex6, vertex6);
		dgraph.displayGraph();
		System.out.println("");
		dgraph.transpose().displayGraph();
	}

}
