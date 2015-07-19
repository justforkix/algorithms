package com.algo.graph;

/**
 * All pairs shortest path.
 */
public class MatrixMultiplication<T> {

	private static final int MAX = 50;
	private final AMGraph<T> graph;

	public MatrixMultiplication(final AMGraph<T> graph) {
		this.graph = graph;
	}

	/**
	 * All pairs shortest path.
	 */
	public int[][] compute() {
		int[][] initial = initialize();
		for (int k = 0; k < initial.length - 2; k++) {
			int[][] result = computeInternal(initial, graph.weightMatrix());
			initial = result;
		}
		return initial;
	}

	/**
	 * All pairs shortest path using associative property of matrices.
	 */
	public int[][] fastCompute() {
		int[][] initial = initialize();
		int[][] weight = graph.weightMatrix();
		int size = initial.length;
		int k = 1;
		while (true) {
			weight = computeInternal(weight, graph.weightMatrix());
			if (k > size - 2) {
				break;
			}
			k = k * 2;
		}
		return computeInternal(initial, weight);
	}

	private int[][] computeInternal(final int[][] initial, final int[][] weight) {
		int[][] result = new int[initial.length][initial.length];
		for (int i = 0; i < initial.length; i++) {
			for (int j = 0; j < initial.length; j++) {
				int value = Integer.MAX_VALUE;
				for (int l = 0; l < initial.length; l++) {
					if (l == i || l == j) {
						continue;
					}
					int sum = add(initial[i][l], weight[l][j]);
					if (sum < value) {
						value = sum;
					}
				}
				result[i][j] = min(initial[i][j], value);
			}
		}
		return result;
	}

	private int min(final int weight1, final int weight2) {
		return weight1 < weight2 ? weight1 : weight2;
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

		MatrixMultiplication<Integer> mm = new MatrixMultiplication<>(dgraph);
		mm.display(mm.compute());
		System.out.println("---");
		mm.display(mm.fastCompute());

	}

}
