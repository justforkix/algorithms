package com.algo.dp;

import java.util.Arrays;

/**
 * Compute the number of combinations of 'n' things taken 'r' at a time. 'n'
 * choose 'r'.
 */
public class BinomialCoefficient {

	private final int n;
	private final int r;
	private final long[][] optimizedValues;

	public BinomialCoefficient(final int n, final int r) {
		this.n = n;
		this.r = r;
		this.optimizedValues = new long[n + 1][r + 1];
	}

	/**
	 * The recursive solution.
	 */
	public long recursive() {
		return compute(n, r);
	}

	private long compute(final int n, final int r) {
		if (n == r) {
			return 1;
		}
		if (r == 1) {
			return n;
		}

		long result = compute(n - 1, r - 1) + compute(n - 1, r);
		return result;
	}

	private void initTopDown() {
		for (int i = 0; i < optimizedValues.length; i++) {
			for (int j = 0; j < optimizedValues[1].length; j++) {
				optimizedValues[i][j] = -1;
			}
		}
	}

	/**
	 * The top down approach using memoization.
	 */
	public long topDown() {
		initTopDown();
		return topDownMemoized(n, r);
	}

	private long topDownMemoized(final int n, final int r) {
		if (n == r) {
			return 1;
		}
		if (r == 1) {
			return n;
		}
		if (optimizedValues[n][r] != -1) {
			return optimizedValues[n][r];
		}

		long result = topDownMemoized(n - 1, r - 1) + compute(n - 1, r);
		optimizedValues[n][r] = result;
		return result;
	}

	private void initBottomsUp() {
		for (int i = 0; i < optimizedValues.length; i++) {
			for (int j = 0; j < optimizedValues[1].length; j++) {
				int result = 0;
				if (j == 0) {
					result = 1;
				}
				if (j == 1) {
					result = i;
				}
				if (i == j) {
					result = 1;
				}
				optimizedValues[i][j] = result;
			}
		}
	}

	/**
	 * The bottoms up approach.
	 */
	public long[][] bottomsUp() {
		initBottomsUp();

		for (int k = 2; k <= r; k++) {
			for (int j = k + 1; j <= n; j++) {
				optimizedValues[j][k] = optimizedValues[j - 1][k - 1] + optimizedValues[j - 1][k];
			}
		}
		return optimizedValues;
	}

	/**
	 * Return the optimal values.
	 */
	public void printOptimalValues() {
		for (int i = 0; i < optimizedValues.length; i++) {
			System.out.println(Arrays.toString(optimizedValues[i]));
		}
	}

	/**
	 * main.
	 */
	public static void main(final String[] args) {
		int n = 10;
		int r = 10;
		BinomialCoefficient bc = new BinomialCoefficient(n, r);
		long currentTime1 = System.nanoTime();
		System.out.println("Recursive solution value: " + bc.recursive());
		System.out.println("Recursive solution time: " + (System.nanoTime() - currentTime1));
		long currentTime2 = System.nanoTime();
		System.out.println("Top Down solution optimal value: " + bc.topDown());
		System.out.println("Top Down solution time: " + (System.nanoTime() - currentTime2));
		long currentTime3 = System.nanoTime();
		bc.bottomsUp();
		System.out.println("Bottoms Up solution time: " + (System.nanoTime() - currentTime3));
		System.out.println("Bottoms Up solution optimal values");
		bc.printOptimalValues();

	}

}
