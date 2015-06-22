package com.algo.fun.graph;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Graph Adjacency List representation.
 */
public class ALGraph<T> implements Cloneable {

	private final Map<Vertex<T>, Map<Vertex<T>, Integer>> adjacencyList;
	private final boolean isDirected;
	private final Set<Vertex<T>> vertices;

	public ALGraph(final boolean isDirected) {
		this.adjacencyList = new HashMap<>();
		this.isDirected = isDirected;
		this.vertices = new HashSet<>();
	}

	/**
	 * Add edge to graph.
	 */
	public ALGraph<T> addEdge(final Vertex<T> vertex1, final Vertex<T> vertex2, final int weight) {
		vertices.add(vertex1);
		vertices.add(vertex2);
		update(adjacencyList, vertex1, vertex2, weight);
		if (!isDirected) {
			update(adjacencyList, vertex2, vertex1, weight);
		}
		return this;
	}

	/**
	 * The vertex links in graph.
	 */
	public Set<Vertex<T>> getList(final Vertex<T> vertex) {
		Set<Vertex<T>> set = null;
		Map<Vertex<T>, Integer> map = adjacencyList.get(vertex);
		if (map != null) {
			return map.keySet();
		} else {
			set = new HashSet<>();
		}
		return set;
	}

	public int edgeWeight(final Vertex<T> vertex1, final Vertex<T> vertex2) {
		return adjacencyList.get(vertex1).get(vertex2);
	}

	/**
	 * The vertices in the graph.
	 */
	public Set<Vertex<T>> vertices() {
		return vertices;
	}

	/**
	 * The transpose graph.
	 */
	public ALGraph<T> transpose() {
		ALGraph<T> tgraph = new ALGraph<>(isDirected);
		Map<Vertex<T>, Vertex<T>> map = new HashMap<>();
		for (Map.Entry<Vertex<T>, Map<Vertex<T>, Integer>> entry : adjacencyList.entrySet()) {
			Vertex<T> vertex2 = getVertex(map, entry.getKey());
			Map<Vertex<T>, Integer> nodes = entry.getValue();
			for (Vertex<T> node : nodes.keySet()) {
				Vertex<T> vertex1 = getVertex(map, node);
				tgraph.addEdge(vertex1, vertex2, nodes.get(node));
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

	private void update(final Map<Vertex<T>, Map<Vertex<T>, Integer>> list, final Vertex<T> vertex1,
	        final Vertex<T> vertex2, final int weight) {
		Map<Vertex<T>, Integer> map = null;
		if (list.containsKey(vertex1)) {
			map = list.get(vertex1);
		} else {
			map = new HashMap<>();
			list.put(vertex1, map);
		}
		map.put(vertex2, weight);
	}

	/**
	 * Print the graph.
	 */
	public void displayGraph() {
		for (Map.Entry<Vertex<T>, Map<Vertex<T>, Integer>> entry : adjacencyList.entrySet()) {
			System.out.println(entry.getKey().toString() + "->" + getList(entry.getKey()));
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

		Set<Vertex<Integer>> uvertices = new HashSet<>();
		uvertices.add(vertex1);
		uvertices.add(vertex2);
		uvertices.add(vertex3);
		uvertices.add(vertex4);
		uvertices.add(vertex5);

		ALGraph<Integer> ugraph = new ALGraph<>(false);
		ugraph.addEdge(vertex1, vertex5, 1).addEdge(vertex1, vertex2, 1).addEdge(vertex2, vertex5, 1)
		        .addEdge(vertex5, vertex4, 1).addEdge(vertex2, vertex4, 1).addEdge(vertex2, vertex3, 1)
		        .addEdge(vertex3, vertex4, 1);
		ugraph.displayGraph();
		System.out.println("");
		ugraph.transpose().displayGraph();

		System.out.println("---------------");

		Set<Vertex<Integer>> dvertices = new HashSet<>();
		dvertices.add(vertex1);
		dvertices.add(vertex2);
		dvertices.add(vertex3);
		dvertices.add(vertex4);
		dvertices.add(vertex5);
		dvertices.add(vertex6);

		ALGraph<Integer> dgraph = new ALGraph<>(true);
		dgraph.addEdge(vertex1, vertex2, 1).addEdge(vertex1, vertex4, 1).addEdge(vertex4, vertex2, 1)
		        .addEdge(vertex2, vertex5, 1).addEdge(vertex5, vertex4, 1).addEdge(vertex3, vertex5, 1)
		        .addEdge(vertex3, vertex6, 1).addEdge(vertex6, vertex6, 1);
		dgraph.displayGraph();
		System.out.println("");
		dgraph.transpose().displayGraph();
	}

}
