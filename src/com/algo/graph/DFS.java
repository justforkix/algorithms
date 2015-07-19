package com.algo.graph;

import java.util.Collection;
import java.util.Deque;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

import com.algo.graph.Vertex.COLOR;

/**
 * Depth First Search.
 */
public class DFS<T> {

	private final ALGraph<T> graph;
	private final Deque<Vertex<T>> topologicalSort;
	private int time;

	public DFS(final ALGraph<T> graph) {
		this.topologicalSort = new LinkedList<Vertex<T>>();
		this.graph = graph;
		this.time = 1;
	}

	/**
	 * Compute the Depth First Forest.
	 */
	public void compute(final Collection<Vertex<T>> vertices) {
		for (Vertex<T> vertex : vertices) {
			if (vertex.getColor().equals(COLOR.WHITE)) {
				computeInternal(vertex);
			}
		}
	}

	public void printTopologicalSort() {
		System.out.println(topologicalSort.toString());
	}

	public Collection<Vertex<T>> topologicalSort() {
		return topologicalSort;
	}

	private void computeInternal(final Vertex<T> vertex) {
		vertex.setColor(COLOR.GRAY);
		vertex.setFirst(time++);
		for (Vertex<T> node : graph.getList(vertex)) {
			if (node.getColor().equals(COLOR.WHITE)) {
				node.setParent(vertex);
				computeInternal(node);
			}
		}
		vertex.setColor(COLOR.BLACK);
		vertex.setLast(time++);
		topologicalSort.addFirst(vertex);
	}

	/**
	 * Print path from source to vertex.
	 */
	public void printPath(final Vertex<T> vertex) {
		printPathInternal(vertex, " ");
	}

	private void printPathInternal(final Vertex<T> vertex, String s) {
		if (vertex != null) {
			s = "(" + vertex.getValue().toString() + "[" + vertex.getFirst() + "]" + s + vertex.getValue().toString()
			        + "[" + vertex.getLast() + "]" + ")";
			printPathInternal(vertex.getParent(), s);
		} else {
			System.out.println(s);
		}
	}

	/**
	 * main.
	 */
	public static void main(final String[] args) {
		/*
		 * Set<Vertex<String>> vertices = new HashSet<Vertex<String>>();
		 * 
		 * Vertex<String> u = new Vertex<>("u"); Vertex<String> v = new
		 * Vertex<>("v"); Vertex<String> w = new Vertex<>("w"); Vertex<String> x
		 * = new Vertex<>("x"); Vertex<String> y = new Vertex<>("y");
		 * Vertex<String> z = new Vertex<>("z");
		 * 
		 * vertices.add(u); vertices.add(v); vertices.add(w); vertices.add(x);
		 * vertices.add(y); vertices.add(z);
		 * 
		 * ALGraph<String> graph = new ALGraph<>(true); graph.addEdge(u,
		 * v).addEdge(u, x).addEdge(x, v).addEdge(v, y).addEdge(y, x).addEdge(w,
		 * y).addEdge(w, z) .addEdge(z, z);
		 */

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

		ALGraph<String> graph = new ALGraph<>(true);
		graph.addEdge(a, b, 1).addEdge(b, e, 1).addEdge(e, a, 1).addEdge(b, f, 1).addEdge(e, f, 1).addEdge(b, c, 1)
		        .addEdge(b, f, 1).addEdge(c, g, 1).addEdge(f, g, 1).addEdge(g, f, 1).addEdge(c, d, 1).addEdge(d, c, 1)
		        .addEdge(g, h, 1).addEdge(d, h, 1).addEdge(h, h, 1);

		graph.displayGraph();
		System.out.println("");

		DFS<String> dfs = new DFS<>(graph);
		dfs.compute(vertices);

		for (Vertex<String> vertex : vertices) {
			System.out.println("---" + vertex.getValue() + "----");
			dfs.printPath(vertex);
		}
		System.out.println("");
		dfs.printTopologicalSort();

	}

}
