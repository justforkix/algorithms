package com.algo.fun.graph;

import java.util.Collection;
import java.util.Comparator;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Set;

/**
 * Single source shortest path with positive weight edges.
 */
public class Dijkstra<T> {

	private static final int SIZE = 10;

	private final ALGraph<T> graph;

	public Dijkstra(final ALGraph<T> graph) {
		this.graph = graph;
	}

	/**
	 * Single source shortest path.
	 */
	public void compute(final Vertex<T> source) {
		source.setKey(0);
		source.setParent(null);
		PriorityQueue<Vertex<T>> queue = priorityQueue(graph.vertices());

		while (!queue.isEmpty()) {
			Vertex<T> vertex = queue.poll();
			for (Vertex<T> node : graph.getList(vertex)) {
				relax(vertex, node, graph.edgeWeight(vertex, node));
			}
			queue = priorityQueue(queue);
		}
	}

	private void relax(final Vertex<T> vertex1, final Vertex<T> vertex2, final int weight) {
		if (vertex2.getKey() > vertex1.getKey() + weight) {
			vertex2.setKey(vertex1.getKey() + weight);
			vertex2.setParent(vertex1);
		}
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
		Vertex<String> s = new Vertex<>("s");
		Vertex<String> t = new Vertex<>("t");
		Vertex<String> x = new Vertex<>("x");
		Vertex<String> z = new Vertex<>("z");
		Vertex<String> y = new Vertex<>("y");

		Set<Vertex<String>> vertices = new HashSet<>();
		vertices.add(s);
		vertices.add(t);
		vertices.add(x);
		vertices.add(z);
		vertices.add(y);

		ALGraph<String> graph = new ALGraph<>(true);
		graph.addEdge(s, t, 10).addEdge(s, y, 5).addEdge(t, y, 2).addEdge(t, x, 1).addEdge(y, z, 2).addEdge(y, x, 9)
		        .addEdge(y, t, 3).addEdge(x, z, 4).addEdge(z, s, 7).addEdge(z, x, 6);

		Dijkstra<String> dj = new Dijkstra<>(graph);
		dj.compute(s);

		for (Vertex<String> vertex : vertices) {
			System.out.println("---" + vertex.toString() + "---");
			System.out.println(dj.printPath(vertex));
		}

	}

}
