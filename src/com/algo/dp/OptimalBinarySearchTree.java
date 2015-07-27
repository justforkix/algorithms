package com.algo.dp;

import java.util.Arrays;

/**
 * Given a sequence K1...Kn of n distinct keys in sorted order with Pi as the
 * probability that the search will be for Ki and Do...Dn as values representing
 * keys not in K with Qi as the probability that the search will be for Di, we
 * wish to build a binary search tree from these keys whose expected search cost
 * is the smallest.
 */
public class OptimalBinarySearchTree {

	private final int numKeys;
	private final double[] pk;
	private final double[] pd;
	private final double[][] optimizedProbability;
	private final double[][] optimizedCost;
	private final int[][] optimizedRoot;

	public OptimalBinarySearchTree(final int numKeys, final double[] pk, final double[] pd) {
		this.numKeys = numKeys;
		this.pk = pk;
		this.pd = pd;
		this.optimizedCost = new double[numKeys + 2][numKeys + 2];
		this.optimizedProbability = new double[numKeys + 2][numKeys + 2];
		this.optimizedRoot = new int[numKeys + 2][numKeys + 2];
	}

	private void initTopDown() {
		for (int i = 0; i < optimizedCost.length; i++) {
			for (int j = 0; j < optimizedCost.length; j++) {
				optimizedCost[i][j] = Double.MIN_VALUE;
			}
		}
	}

	private void initBottomsUp() {
		for (int i = 0; i < optimizedCost.length; i++) {
			for (int j = 0; j < optimizedCost.length; j++) {
				double value = 0.0;
				if (j == i - 1 && i > 0) {
					value = pd[j];
				}
				optimizedCost[i][j] = value;
				optimizedProbability[i][j] = value;
				optimizedRoot[i][j] = 0;
			}
		}
	}

	/**
	 * The recursive solution.
	 */
	public double recursive() {
		return compute(1, numKeys);
	}

	/**
	 * The top down approach using memoization.
	 */
	public double topDown() {
		initTopDown();
		return topDownMemoized(1, numKeys);
	}

	/**
	 * The bottoms up approach.
	 */
	public double[][] bottomsUp() {
		initBottomsUp();
		for (int l = 0; l < numKeys; l++) {
			for (int i = 1; i <= numKeys - l; i++) {
				double result = Double.MAX_VALUE;
				double presult = 0.0;
				int root = -1;
				for (int j = i; j <= i + l; j++) {
					double pvalue = optimizedProbability[i][(j) - 1] + optimizedProbability[(j) + 1][i + l] + pk[j];
					double value = optimizedCost[i][(j) - 1] + optimizedCost[(j) + 1][i + l] + pvalue;
					if (value < result) {
						result = value;
						presult = pvalue;
						root = j;
					}
				}
				optimizedCost[i][i + l] = result;
				optimizedProbability[i][i + l] = presult;
				optimizedRoot[i][i + l] = root;
			}
		}
		return optimizedCost;
	}

	/**
	 * Return the optimal values.
	 */
	public void printOptimizedValues() {
		System.out.println("----cost------");
		for (int i = 0; i < optimizedCost.length; i++) {
			System.out.println(Arrays.toString(optimizedCost[i]));
		}
		System.out.println("-----probabilty-----");
		for (int i = 0; i < optimizedProbability.length; i++) {
			System.out.println(Arrays.toString(optimizedProbability[i]));
		}
		System.out.println("----root------");
		for (int i = 0; i < optimizedRoot.length; i++) {
			System.out.println(Arrays.toString(optimizedRoot[i]));
		}
	}

	private double topDownMemoized(final int begin, final int end) {
		if (begin > end) {
			return pd[end];
		}
		if (optimizedCost[begin][end] != Double.MIN_VALUE) {
			return optimizedCost[begin][end];
		} else {
			double result = Double.MAX_VALUE;
			for (int i = begin; i <= end; i++) {
				double cost = topDownMemoized(begin, i - 1) + topDownMemoized(i + 1, end) + add(begin, end);
				if (cost < result) {
					result = cost;
				}
			}
			optimizedCost[begin][end] = result;
			return result;
		}
	}

	private double compute(final int begin, final int end) {
		if (begin > end) {
			return pd[end];
		}
		double result = Double.MAX_VALUE;
		for (int i = begin; i <= end; i++) {
			double cost = compute(begin, i - 1) + compute(i + 1, end) + add(begin, end);
			if (cost < result) {
				result = cost;
			}
		}
		return result;
	}

	private double add(final int first, final int last) {
		double sum = pd[first - 1];
		for (int i = first; i <= last; i++) {
			sum = sum + pk[i] + pd[i];
		}
		return sum;
	}

	/**
	 * main.
	 */
	public static void main(final String[] args) {
		double[] pd = { 0.05, 0.10, 0.05, 0.05, 0.05, 0.10, 0.0, 0.0, 0.0 };
		double[] pk = { 0.0, 0.15, 0.10, 0.05, 0.10, 0.20, 0.0, 0.0, 0.0 };

		OptimalBinarySearchTree obst = new OptimalBinarySearchTree(5, pk, pd);

		long currentTime1 = System.nanoTime();
		System.out.println("Recursive solution min cost: " + obst.recursive());
		System.out.println("Recursive solution time: " + (System.nanoTime() - currentTime1));
		long currentTime2 = System.nanoTime();
		System.out.println("Top Down solution optimal cost: " + obst.topDown());
		System.out.println("Top Down solution time: " + (System.nanoTime() - currentTime2));
		long currentTime3 = System.nanoTime();
		obst.bottomsUp();
		System.out.println("Bottoms Up solution time: " + (System.nanoTime() - currentTime3));
		System.out.println("Bottoms Up solution optimal cost");
		obst.printOptimizedValues();
	}

}
