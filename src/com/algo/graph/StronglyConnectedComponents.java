package com.algo.graph;

import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;

/**
 * Strongly Connected Components of a graph.
 */
public class StronglyConnectedComponents<T> {

	private final ALGraph<T> graph;

	public StronglyConnectedComponents(final ALGraph<T> graph) {
		this.graph = graph;
	}

	/**
	 * Strongly Connected Components.
	 */
	public void compute() {
		DFS<T> dfs = new DFS<>(graph);
		dfs.compute(graph.vertices());
		Deque<Vertex<T>> ts = (Deque<Vertex<T>>) dfs.topologicalSort();

		ALGraph<T> tgraph = graph.transpose();
		Set<Vertex<T>> tvertices = tgraph.vertices();
		Map<Vertex<T>, Vertex<T>> map = new HashMap<>();
		for (Vertex<T> vertex : tvertices) {
			map.put(vertex, vertex);
		}

		Deque<Vertex<T>> deque = new LinkedList<>();
		for (Vertex<T> vertex : ts) {
			deque.addLast(map.get(vertex));
		}

		DFS<T> tdfs = new DFS<>(tgraph);
		tdfs.compute(deque);

		for (Vertex<T> vertex : tvertices) {
			System.out.println("---" + vertex.getValue() + "----");
			dfs.printPath(vertex);
		}
	}

	/**
	 * main.
	 */
	public static void main(final String[] args) {
		Set<Vertex<String>> vertices = new HashSet<Vertex<String>>();

		Vertex<String> a = new Vertex<>("a");
		Vertex<String> b = new Vertex<>("b");
		Vertex<String> c = new Vertex<>("c");
		Vertex<String> d = new Vertex<>("d");
		Vertex<String> e = new Vertex<>("e");
		Vertex<String> f = new Vertex<>("f");
		Vertex<String> g = new Vertex<>("g");
		Vertex<String> h = new Vertex<>("h");

		vertices.add(a);
		vertices.add(b);
		vertices.add(c);
		vertices.add(d);
		vertices.add(e);
		vertices.add(f);
		vertices.add(g);
		vertices.add(h);

		ALGraph<String> dgraph = new ALGraph<>(true);
		dgraph.addEdge(a, b, 1).addEdge(b, e, 1).addEdge(e, a, 1).addEdge(b, f, 1).addEdge(e, f, 1).addEdge(b, c, 1)
		        .addEdge(b, f, 1).addEdge(c, g, 1).addEdge(f, g, 1).addEdge(g, f, 1).addEdge(c, d, 1).addEdge(d, c, 1)
		        .addEdge(g, h, 1).addEdge(d, h, 1).addEdge(h, h, 1);

		StronglyConnectedComponents<String> scc = new StronglyConnectedComponents<>(dgraph);
		scc.compute();
	}

}
