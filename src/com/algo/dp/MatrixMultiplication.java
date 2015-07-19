package com.algo.dp;

import java.util.Arrays;

/**
 * Given a chain on 'n' matrices A1...An where Ai has dimensions Pi-1 x Pi,
 * parenthesize the product A1.A2...An in a way that minimizes the scalar
 * multiplications.
 */
public class MatrixMultiplication {

	private final int[] dimensions;
	private final int numMatrices;
	private final long[][] optimalCost;
	private final int[][] optimalPosition;

	public MatrixMultiplication(final int[] dimensions) {
		this.dimensions = dimensions;
		this.numMatrices = dimensions.length - 1;
		this.optimalCost = new long[dimensions.length][dimensions.length];
		this.optimalPosition = new int[dimensions.length][dimensions.length];
		for (int i = 0; i < dimensions.length; i++) {
			for (int j = 0; j < dimensions.length; j++) {
				optimalCost[i][j] = Long.MAX_VALUE;
				optimalPosition[i][j] = 0;
			}
		}
	}

	/**
	 * The recursive solution.
	 */
	public long recursive() {
		return compute(1, numMatrices);
	}

	private long compute(final int initialIndex, final int finalIndex) {
		if (initialIndex == finalIndex) {
			return 0;
		}
		long result = Long.MAX_VALUE;
		for (int i = initialIndex; i < finalIndex; i++) {
			long value = compute(initialIndex, i) + dimensions[initialIndex - 1] * dimensions[i]
			        * dimensions[finalIndex] + compute(i + 1, finalIndex);
			if (value < result) {
				result = value;
			}
		}
		optimalCost[initialIndex][finalIndex] = result;
		return result;
	}

	private long topDownMemoized(final int initialIndex, final int finalIndex) {
		if (initialIndex == finalIndex) {
			return 0;
		}
		if (optimalCost[initialIndex][finalIndex] < Long.MAX_VALUE) {
			return optimalCost[initialIndex][finalIndex];
		} else {
			return compute(initialIndex, finalIndex);
		}
	}

	/**
	 * The top down approach using memoization.
	 */
	public long topDown() {
		return topDownMemoized(1, numMatrices);
	}

	/**
	 * The bottoms up approach.
	 */
	public long[][] bottomsUp() {
		for (int i = 1; i <= numMatrices; i++) {
			optimalCost[i][i] = 0;
		}
		for (int l = 1; l < numMatrices; l++) {
			for (int i = 1; i <= numMatrices - l; i++) {
				long result = Long.MAX_VALUE;
				int position = -1;
				for (int j = i; j < i + l; j++) {
					long value = optimalCost[i][j] + dimensions[i - 1] * dimensions[j] * dimensions[i + l]
					        + optimalCost[j + 1][i + l];
					if (value < result) {
						result = value;
						position = j;
					}
				}
				optimalCost[i][i + l] = result;
				optimalPosition[i][i + l] = position;
			}
		}
		return optimalCost;
	}

	/**
	 * Return the optimal position for the matrix range.
	 */
	public int[][] getOptimalPosition() {
		return optimalPosition;
	}

	public static void main(final String[] args) {
		int[] dimensions = { 30, 35, 15, 5, 10, 20, 25 };
		MatrixMultiplication mm = new MatrixMultiplication(dimensions);
		long currentTime1 = System.nanoTime();
		System.out.println("Recursive solution min cost: " + mm.recursive());
		System.out.println("Recursive solution time: " + (System.nanoTime() - currentTime1));
		long currentTime2 = System.nanoTime();
		System.out.println("Top Down solution optimal cost: " + mm.topDown());
		System.out.println("Top Down solution time: " + (System.nanoTime() - currentTime2));
		long currentTime3 = System.nanoTime();
		long[][] cost = mm.bottomsUp();
		System.out.println("Bottoms Up solution time: " + (System.nanoTime() - currentTime3));
		System.out.println("Bottoms Up solution optimal cost");
		for (int i = 0; i < cost.length; i++) {
			System.out.println(Arrays.toString(cost[i]));
		}
		System.out.println("Bottoms Up solution optimal position");
		int[][] position = mm.getOptimalPosition();
		for (int i = 0; i < position.length; i++) {
			System.out.println(Arrays.toString(position[i]));
		}
	}

}
