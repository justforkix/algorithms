package com.algo.fun.graph;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Graph Adjacency Matrix representation.
 */
public class AMGraph<T> implements Cloneable {

	private static final int MAX = 50;

	private static class Edge {
		private final int weight;
		private final boolean link;

		public Edge(final int weight, final boolean link) {
			this.weight = weight;
			this.link = link;
		}

		public int getWeight() {
			return weight;
		}

		public boolean isLink() {
			return link;
		}

		@Override
		public String toString() {
			return "[" + getWeight() + "," + isLink() + "]";
		}

	}

	private final Edge[][] adjacencyMatrix;
	private final Map<Vertex<T>, Integer> indexMap;
	private final Map<Integer, Vertex<T>> reverseMap;
	private final boolean isDirected;
	private final int size;

	public AMGraph(final int size, final boolean isDirected) {
		this.adjacencyMatrix = new Edge[size][size];
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				int weight = MAX;
				if (i == j) {
					weight = 0;
				}
				adjacencyMatrix[i][j] = new Edge(weight, false);
			}
		}
		this.indexMap = new HashMap<>();
		this.reverseMap = new HashMap<>();
		this.isDirected = isDirected;
		this.size = size;
	}

	public Map<Vertex<T>, Integer> getIndexMap() {
		return indexMap;
	}

	public int[][] weightMatrix() {
		int[][] result = new int[size][size];

		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				result[i][j] = adjacencyMatrix[i][j].weight;
			}
		}
		return result;
	}

	public Edge[][] adjacencyMatrix() {
		return adjacencyMatrix;
	}

	/**
	 * Add vertex to graph.
	 */
	public void addVertex(final Vertex<T> vertex, final int position) {
		indexMap.put(vertex, position);
		reverseMap.put(position, vertex);
	}

	/**
	 * Add edge to graph.
	 */
	public AMGraph<T> addEdge(final Vertex<T> vertex1, final Vertex<T> vertex2, final int weight) {
		update(vertex1, vertex2, weight);
		if (!isDirected) {
			update(vertex2, vertex1, weight);
		}
		return this;
	}

	/**
	 * The transpose graph.
	 */
	public AMGraph<T> transpose() {
		AMGraph<T> tgraph = new AMGraph<>(size, isDirected);
		Map<Vertex<T>, Vertex<T>> map = new HashMap<>();
		for (Map.Entry<Vertex<T>, Integer> entry : indexMap.entrySet()) {
			tgraph.addVertex(getVertex(map, entry.getKey()), entry.getValue());
		}
		for (int i = 0; i < adjacencyMatrix.length; i++) {
			for (int j = 0; j < adjacencyMatrix[i].length; j++) {
				if (adjacencyMatrix[i][j].isLink()) {
					Vertex<T> vertex1 = getVertex(map, reverseMap.get(j));
					Vertex<T> vertex2 = getVertex(map, reverseMap.get(i));
					tgraph.addEdge(vertex1, vertex2, adjacencyMatrix[i][j].getWeight());
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
		return adjacencyMatrix[index1][index2].isLink();
	}

	/**
	 * The weight of the edge between two vertices.
	 */
	public boolean hasEdge(final int index1, final int index2) {
		Vertex<T> vertex1 = reverseMap.get(index1);
		Vertex<T> vertex2 = reverseMap.get(index2);
		return hasEdge(vertex1, vertex2);
	}

	/**
	 * The weight of the edge between two vertices.
	 */
	public int getEdgeWeight(final Vertex<T> vertex1, final Vertex<T> vertex2) {
		int index1 = indexMap.get(vertex1);
		int index2 = indexMap.get(vertex2);
		return adjacencyMatrix[index1][index2].getWeight();
	}

	/**
	 * The weight of the edge between two vertices.
	 */
	public int getEdgeWeight(final int index1, final int index2) {
		Vertex<T> vertex1 = reverseMap.get(index1);
		Vertex<T> vertex2 = reverseMap.get(index2);
		return getEdgeWeight(vertex1, vertex2);
	}

	/**
	 * The vertex at position.
	 */
	public Vertex<T> getVertex(final int position) {
		return reverseMap.get(position);
	}

	private void update(final Vertex<T> vertex1, final Vertex<T> vertex2, final int weight) {
		int index1 = indexMap.get(vertex1);
		int index2 = indexMap.get(vertex2);
		adjacencyMatrix[index1][index2] = new Edge(weight, true);
	}

	/**
	 * The vertices in the graph.
	 */
	public Set<Vertex<T>> vertices() {
		return indexMap.keySet();
	}

	/**
	 * The matrix dimensions.
	 */
	public int getSize() {
		return size;
	}

	/**
	 * Print the graph.
	 */
	public void displayGraph() {
		display(adjacencyMatrix);
	}

	private void display(final Edge[][] matrix) {
		for (Map.Entry<Integer, Vertex<T>> entry : reverseMap.entrySet()) {
			Edge[] links = matrix[entry.getKey()];
			Set<Vertex<T>> vertices = new HashSet<Vertex<T>>();
			for (int j = 0; j < links.length; j++) {
				if (links[j].isLink()) {
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

		int weight = 0;

		AMGraph<Integer> ugraph = new AMGraph<>(5, false);
		ugraph.addVertex(vertex1, 0);
		ugraph.addVertex(vertex2, 1);
		ugraph.addVertex(vertex3, 2);
		ugraph.addVertex(vertex4, 3);
		ugraph.addVertex(vertex5, 4);

		ugraph.addEdge(vertex1, vertex5, weight).addEdge(vertex1, vertex2, weight).addEdge(vertex2, vertex5, weight)
		        .addEdge(vertex5, vertex4, weight).addEdge(vertex2, vertex4, weight).addEdge(vertex2, vertex3, weight)
		        .addEdge(vertex3, vertex4, weight);
		ugraph.displayGraph();
		System.out.println("");
		ugraph.transpose().displayGraph();

		System.out.println("-----***----");

		AMGraph<Integer> dgraph = new AMGraph<>(6, true);
		dgraph.addVertex(vertex1, 0);
		dgraph.addVertex(vertex2, 1);
		dgraph.addVertex(vertex3, 2);
		dgraph.addVertex(vertex4, 3);
		dgraph.addVertex(vertex5, 4);
		dgraph.addVertex(vertex6, 5);

		dgraph.addEdge(vertex1, vertex2, weight).addEdge(vertex1, vertex4, weight).addEdge(vertex4, vertex2, weight)
		        .addEdge(vertex2, vertex5, weight).addEdge(vertex5, vertex4, weight).addEdge(vertex3, vertex5, weight)
		        .addEdge(vertex3, vertex6, weight).addEdge(vertex6, vertex6, weight);
		dgraph.displayGraph();
		System.out.println("");
		dgraph.transpose().displayGraph();
	}

}
