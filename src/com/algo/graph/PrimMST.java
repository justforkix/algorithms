package com.algo.graph;

import java.util.Collection;
import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * Prim's Minimum Spanning Tree.
 */
public class PrimMST<T> {

	private final ALGraph<T> graph;
	private static final int SIZE = 10;

	public PrimMST(final ALGraph<T> graph) {
		this.graph = graph;
	}

	/**
	 * Minimum Spanning Tree.
	 */
	public void compute(final Vertex<T> vertex) {
		vertex.setKey(0);
		vertex.setParent(null);
		PriorityQueue<Vertex<T>> queue = priorityQueue(graph.vertices());
		while (!queue.isEmpty()) {
			System.out.println(queue.toString());
			Vertex<T> node = queue.poll();
			for (Vertex<T> item : graph.getList(node)) {
				if ((graph.edgeWeight(node, item) < item.getKey()) && queue.contains(item)) {
					item.setKey(graph.edgeWeight(node, item));
					item.setParent(node);
				}
			}
			queue = priorityQueue(queue);
		}
	}

	public String printPath(final Vertex<T> vertex) {
		String str = "";
		if (vertex != null) {
			str = vertex.toString() + "->" + printPath(vertex.getParent());
		}
		return str;
	}

	private PriorityQueue<Vertex<T>> priorityQueue(final Collection<Vertex<T>> col) {
		PriorityQueue<Vertex<T>> queue = new PriorityQueue<>(SIZE, new Comparator<Vertex<T>>() {
			@Override
			public int compare(final Vertex<T> vertex1, final Vertex<T> vertex2) {
				return vertex1.getKey() - vertex2.getKey();
			}
		});
		for (Vertex<T> vertex : col) {
			queue.add(vertex);
		}
		return queue;
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

		ALGraph<String> graph = new ALGraph<>(false);
		graph.addEdge(a, b, 4).addEdge(b, c, 8).addEdge(c, d, 7).addEdge(d, e, 9).addEdge(e, f, 10).addEdge(f, d, 14)
		        .addEdge(f, c, 4).addEdge(f, g, 2).addEdge(g, i, 6).addEdge(i, c, 2).addEdge(g, h, 1).addEdge(h, i, 7)
		        .addEdge(h, b, 11).addEdge(a, h, 8);

		PrimMST<String> prim = new PrimMST<>(graph);
		prim.compute(g);

		for (Vertex<String> vertex : graph.vertices()) {
			System.out.println("---" + vertex.toString() + "---");
			System.out.println(prim.printPath(vertex));
		}
	}

}
