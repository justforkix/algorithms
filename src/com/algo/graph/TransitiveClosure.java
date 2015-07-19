package com.algo.graph;

/**
 * Transitive closure of a directed graph.
 */
public class TransitiveClosure<T> {

	private final AMGraph<T> graph;
	private int[][] tclosure;
	private final int size;

	public TransitiveClosure(final AMGraph<T> graph) {
		this.graph = graph;
		this.size = graph.getSize();
	}

	/**
	 * The transitive closure matrix.
	 */
	public int[][] compute() {
		int[][] initial = initialize();
		for (int k = 0; k < size; k++) {
			int[][] result = new int[size][size];
			for (int i = 0; i < size; i++) {
				for (int j = 0; j < size; j++) {
					result[i][j] = initial[i][j] | (initial[i][k] & initial[k][j]);
				}
			}
			initial = result;
		}
		return initial;
	}

	private int[][] initialize() {
		int result[][] = new int[size][size];
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				if (i == j || graph.hasEdge(i, j)) {
					result[i][j] = 1;
				} else {
					result[i][j] = 0;
				}
			}
		}
		return result;
	}

	/**
	 * Display the transitive closure.
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

	public static void main(final String[] args) {
		Vertex<Integer> vertex0 = new Vertex<>(0);
		Vertex<Integer> vertex1 = new Vertex<>(1);
		Vertex<Integer> vertex2 = new Vertex<>(2);
		Vertex<Integer> vertex3 = new Vertex<>(3);

		AMGraph<Integer> dgraph = new AMGraph<>(4, true);
		dgraph.addVertex(vertex0, 0);
		dgraph.addVertex(vertex1, 1);
		dgraph.addVertex(vertex2, 2);
		dgraph.addVertex(vertex3, 3);

		dgraph.addEdge(vertex1, vertex3, 1).addEdge(vertex1, vertex2, 1).addEdge(vertex2, vertex1, 1)
		        .addEdge(vertex3, vertex0, 1).addEdge(vertex3, vertex2, 1);

		dgraph.displayGraph();
		System.out.println("");

		TransitiveClosure<Integer> tc = new TransitiveClosure<>(dgraph);
		System.out.println("");
		tc.display(tc.compute());

	}

}
