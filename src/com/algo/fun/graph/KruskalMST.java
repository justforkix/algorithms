package com.algo.fun.graph;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Kruskal's Minimum Spanning Tree.
 */
public class KruskalMST<T> {

	private final Map<Vertex<T>, Set<Vertex<T>>> map;
	private final Set<Edge<T>> edges;
	private final Set<Edge<T>> mst;

	public KruskalMST() {
		this.map = new HashMap<>();
		this.edges = new HashSet<>();
		this.mst = new HashSet<>();
	}

	public KruskalMST<T> addEdge(final Vertex<T> vertex1, final Vertex<T> vertex2, final int weight) {
		addVertex(vertex1);
		addVertex(vertex2);
		Edge<T> edge = new Edge<>(vertex1, vertex2, false, weight);
		edges.add(edge);
		return this;
	}

	private void addVertex(final Vertex<T> vertex) {
		if (!map.containsKey(vertex)) {
			Set<Vertex<T>> set = new HashSet<>();
			set.add(vertex);
			map.put(vertex, set);
		}
	}

	/**
	 * Minimum Spanning Tree.
	 */
	public Set<Edge<T>> compute() {
		List<Edge<T>> list = new LinkedList<>(edges);
		Collections.sort(list);
		for (Edge<T> edge : list) {
			if (!(map.get(edge.getVertex1()).equals(map.get(edge.getVertex2())))) {
				mst.add(edge);
				Set<Vertex<T>> set = new HashSet<>();
				set.addAll(map.get(edge.getVertex1()));
				set.addAll(map.get(edge.getVertex2()));
				for (Vertex<T> vertex : set) {
					map.put(vertex, set);
				}
			}
		}
		return mst;
	}

	/**
	 * main.
	 */
	public static void main(final String[] args) {
		Vertex<String> a = new Vertex<>("a");
		Vertex<String> b = new Vertex<>("b");
		Vertex<String> c = new Vertex<>("c");
		Vertex<String> d = new Vertex<>("d");
		Vertex<String> e = new Vertex<>("e");
		Vertex<String> f = new Vertex<>("f");
		Vertex<String> g = new Vertex<>("g");
		Vertex<String> h = new Vertex<>("h");
		Vertex<String> i = new Vertex<>("i");

		KruskalMST<String> kruskal = new KruskalMST<>();
		kruskal.addEdge(a, b, 4).addEdge(b, c, 8).addEdge(c, d, 7).addEdge(d, e, 9).addEdge(e, f, 10).addEdge(f, d, 14)
		        .addEdge(f, c, 4).addEdge(f, g, 2).addEdge(g, i, 6).addEdge(i, c, 2).addEdge(g, h, 1).addEdge(h, i, 7)
		        .addEdge(h, b, 11).addEdge(a, h, 8);

		Set<Edge<String>> mst = kruskal.compute();
		System.out.println(mst.toString());
	}

}
