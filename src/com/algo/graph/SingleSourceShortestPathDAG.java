package com.algo.graph;

import java.util.Deque;
import java.util.HashSet;
import java.util.Set;

/**
 * Single source shortest path with negative weight DAG.
 */
public class SingleSourceShortestPathDAG<T> {

	private final ALGraph<T> graph;

	public SingleSourceShortestPathDAG(final ALGraph<T> graph) {
		this.graph = graph;
	}

	/**
	 * Single source shortest path.
	 */
	public void compute(final Vertex<T> source) {
		source.setKey(0);
		source.setParent(null);

		DFS<T> dfs = new DFS<>(graph);
		dfs.compute(graph.vertices());
		Deque<Vertex<T>> topologicalSort = (Deque<Vertex<T>>) dfs.topologicalSort();

		// reset parent that is udpated in dfs
		for (Vertex<T> vertex : graph.vertices()) {
			vertex.setParent(null);
		}

		for (Vertex<T> vertex : topologicalSort) {
			for (Vertex<T> node : graph.getList(vertex)) {
				relax(vertex, node, graph.edgeWeight(vertex, node));
			}
		}
	}

	private void relax(final Vertex<T> vertex1, final Vertex<T> vertex2, final int weight) {
		if (vertex2.getKey() > vertex1.getKey() + weight) {
			vertex2.setKey(vertex1.getKey() + weight);
			vertex2.setParent(vertex1);
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
		Vertex<String> r = new Vertex<>("r");
		Vertex<String> s = new Vertex<>("s");
		Vertex<String> t = new Vertex<>("t");
		Vertex<String> x = new Vertex<>("x");
		Vertex<String> z = new Vertex<>("z");
		Vertex<String> y = new Vertex<>("y");

		Set<Vertex<String>> vertices = new HashSet<>();
		vertices.add(r);
		vertices.add(s);
		vertices.add(t);
		vertices.add(x);
		vertices.add(z);
		vertices.add(y);

		ALGraph<String> graph = new ALGraph<>(true);
		graph.addEdge(r, s, 5).addEdge(r, t, 3).addEdge(s, t, 2).addEdge(s, x, 6).addEdge(t, x, 7).addEdge(t, y, 4)
		        .addEdge(t, z, 2).addEdge(x, y, -1).addEdge(x, z, 1).addEdge(y, z, -2);

		SingleSourceShortestPathDAG<String> ssp = new SingleSourceShortestPathDAG<>(graph);
		ssp.compute(s);

		for (Vertex<String> vertex : vertices) {
			System.out.println("---" + vertex.toString() + "---");
			System.out.println(ssp.printPath(vertex));
		}
	}

}
