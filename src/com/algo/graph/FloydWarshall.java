package com.algo.graph;

/**
 * All pairs shortest path.
 */
public class FloydWarshall<T> {

	private static final int MAX = 50;
	private final AMGraph<T> graph;
	private int[][] parent;

	public FloydWarshall(final AMGraph<T> graph) {
		this.graph = graph;
	}

	/**
	 * All pairs shortest path.
	 */
	public int[][] compute() {
		int[][] initial = initialize();
		int[][] iparent = initializeParent();
		int size = graph.getSize();
		for (int k = 0; k < size; k++) {
			int[][] result = new int[size][size];
			int[][] rparent = new int[size][size];
			for (int i = 0; i < size; i++) {
				for (int j = 0; j < size; j++) {
					int pindex, value;
					if (lessThanEqual(initial[i][j], add(initial[i][k], initial[k][j]))) {
						pindex = iparent[i][j];
						value = initial[i][j];
					} else {
						pindex = iparent[k][j];
						value = add(initial[i][k], initial[k][j]);
					}
					result[i][j] = value;
					rparent[i][j] = pindex;
				}
			}
			initial = result;
			iparent = rparent;
		}
		parent = iparent;
		return initial;
	}

	/**
	 * The predecessor matrix.
	 */
	public int[][] parentMatrix() {
		return parent;
	}

	/**
	 * Display shortest paths.
	 */
	public void display(final int[][] result) {
		System.out.print("    ");
		for (int k = 0; k < graph.getSize(); k++) {
			System.out.print(String.format("%-3s", graph.getVertex(k).toString()));
		}
		System.out.println("");
		for (int i = 0; i < graph.getSize(); i++) {
			System.out.print(graph.getVertex(i).toString() + "   ");
			for (int j = 0; j < graph.getSize(); j++) {
				System.out.print(String.format("%-3s", result[i][j]));
			}
			System.out.println("");
		}
		System.out.println("");
	}

	private boolean lessThanEqual(final int weight1, final int weight2) {
		return weight1 <= weight2 ? true : false;
	}

	private int add(final int weight1, final int weight2) {
		if (weight1 == MAX || weight2 == MAX) {
			return MAX;
		}
		return weight1 + weight2;
	}

	private int[][] initialize() {
		int[][] matrix = new int[graph.getSize()][graph.getSize()];
		for (int i = 0; i < matrix.length; i++) {
			for (int j = 0; j < matrix[i].length; j++) {
				if (i == j) {
					matrix[i][j] = 0;
				} else {
					matrix[i][j] = graph.getEdgeWeight(i, j);
				}
			}
		}
		return matrix;
	}

	private int[][] initializeParent() {
		int[][] matrix = new int[graph.getSize()][graph.getSize()];
		for (int i = 0; i < matrix.length; i++) {
			for (int j = 0; j < matrix[i].length; j++) {
				if (i == j || !(graph.hasEdge(i, j))) {
					matrix[i][j] = MAX;
				} else {
					matrix[i][j] = i;
				}
			}
		}
		return matrix;
	}

	/**
	 * main.
	 */
	public static void main(final String[] args) {
		Vertex<Integer> vertex0 = new Vertex<>(0);
		Vertex<Integer> vertex1 = new Vertex<>(1);
		Vertex<Integer> vertex2 = new Vertex<>(2);
		Vertex<Integer> vertex3 = new Vertex<>(3);
		Vertex<Integer> vertex4 = new Vertex<>(4);

		AMGraph<Integer> dgraph = new AMGraph<>(5, true);
		dgraph.addVertex(vertex0, 0);
		dgraph.addVertex(vertex1, 1);
		dgraph.addVertex(vertex2, 2);
		dgraph.addVertex(vertex3, 3);
		dgraph.addVertex(vertex4, 4);

		dgraph.addEdge(vertex0, vertex1, 3).addEdge(vertex0, vertex2, 8).addEdge(vertex0, vertex4, -4)
		        .addEdge(vertex1, vertex4, 7).addEdge(vertex1, vertex3, 1).addEdge(vertex2, vertex1, 4)
		        .addEdge(vertex3, vertex2, -5).addEdge(vertex3, vertex0, 2).addEdge(vertex4, vertex3, 6);

		dgraph.displayGraph();
		System.out.println("");

		FloydWarshall<Integer> fw = new FloydWarshall<>(dgraph);
		System.out.println("");
		fw.display(fw.compute());
		System.out.println("----");
		fw.display(fw.parentMatrix());
	}

}
