package com.algo.fun;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

/**
 * Breadth First Search.
 */
public class BFS<T> {

	private enum COLOR {
		WHITE, GRAY, BLACK
	};

	/**
	 * Vertex.
	 */
	public static class Vertex<T> {
		private final T value;
		private COLOR color;
		private int distance;
		private Vertex<T> parent;

		public Vertex(final T value) {
			this.value = value;
			this.color = COLOR.WHITE;
			this.distance = Integer.MAX_VALUE;
		}

		public T getValue() {
			return value;
		}

		public COLOR getColor() {
			return color;
		}

		public void setColor(final COLOR color) {
			this.color = color;
		}

		public int getDistance() {
			return distance;
		}

		public void setDistance(final int distance) {
			this.distance = distance;
		}

		public Vertex<T> getParent() {
			return parent;
		}

		public void setParent(final Vertex<T> parent) {
			this.parent = parent;
		}

	}

	private final ALGraph<Vertex<T>> graph;

	private final Queue<Vertex<T>> queue;

	public BFS(final ALGraph<Vertex<T>> graph) {
		this.graph = graph;
		this.queue = new LinkedList<>();
	}

	/**
	 * Compute the Breadth First Tree.
	 */
	public void compute(final Vertex<T> source) {
		queue.add(source);
		source.setColor(COLOR.GRAY);
		source.setParent(null);
		source.setDistance(0);
		while (!queue.isEmpty()) {
			Vertex<T> vertex = queue.remove();
			Set<Vertex<T>> vertices = graph.getList(vertex);
			for (Vertex<T> node : vertices) {
				if (node.getColor().equals(COLOR.WHITE)) {
					node.setColor(COLOR.GRAY);
					node.setDistance(vertex.getDistance() + 1);
					node.setParent(vertex);
					queue.add(node);
				}
			}
			vertex.setColor(COLOR.BLACK);
		}
	}

	/**
	 * Print path from source to vertex.
	 */
	public void printPath(final Vertex<T> vertex) {
		if (vertex.getParent() == null) {
			System.out.print(vertex.getValue().toString() + "[" + vertex.getDistance() + "]");
		} else {
			printPath(vertex.getParent());
			System.out.print("-> " + vertex.getValue().toString() + "[" + vertex.getDistance() + "]");
		}
	}

	/**
	 * main.
	 */
	public static void main(final String[] args) {
		Set<Vertex<String>> vertices = new HashSet<Vertex<String>>();

		Vertex<String> r = new Vertex<>("r");
		Vertex<String> s = new Vertex<>("s");
		Vertex<String> t = new Vertex<>("t");
		Vertex<String> u = new Vertex<>("u");
		Vertex<String> v = new Vertex<>("v");
		Vertex<String> w = new Vertex<>("w");
		Vertex<String> x = new Vertex<>("x");
		Vertex<String> y = new Vertex<>("y");

		vertices.add(r);
		vertices.add(s);
		vertices.add(t);
		vertices.add(u);
		vertices.add(v);
		vertices.add(w);
		vertices.add(x);
		vertices.add(y);

		ALGraph<Vertex<String>> graph = new ALGraph<>(false);
		graph.addEdge(r, v).addEdge(r, s).addEdge(s, w).addEdge(t, w).addEdge(w, x).addEdge(t, x).addEdge(t, u)
		.addEdge(x, y).addEdge(x, u).addEdge(u, y);

		BFS<String> bfs = new BFS<>(graph);
		bfs.compute(s);

		for (Vertex<String> vertex : vertices) {
			System.out.println("---" + vertex.getValue() + "---");
			bfs.printPath(vertex);
			System.out.println("");
		}
	}

}
