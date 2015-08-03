package com.algo.dp;

import java.util.Arrays;

/**
 * Approximate string matching. 1. Longest Common Subsequence (no
 * substitutions). 2. Maximum Monotone Subsequence (LCS with sorted sequence).
 */
public class EditDistance {

	private final char[] first;
	private final char[] second;
	private final long[][] optimalCost;
	private final char[][] optimalPath;

	public EditDistance(final char[] first, final char[] second) {
		this.first = first;
		this.second = second;
		this.optimalCost = new long[first.length + 1][second.length + 1];
		this.optimalPath = new char[first.length + 1][second.length + 1];
	}

	/**
	 * The recursive solution.
	 */
	public long recursive(final int firstEndIndex, final int secondEndIndex) {
		return compute(0, 0, firstEndIndex, secondEndIndex);
	}

	private long compute(final int firstIndex, final int secondIndex, final int firstEndIndex, final int secondEndIndex) {
		if (firstIndex == firstEndIndex + 1) {
			return secondEndIndex + 1 - secondIndex;
		} else if (secondIndex == secondEndIndex + 1) {
			return firstEndIndex + 1 - firstIndex;
		}

		long result = 0;
		if (first[firstIndex] == second[secondIndex]) {
			return compute(firstIndex + 1, secondIndex + 1, firstEndIndex, secondEndIndex);
		} else {
			long value1 = 1 + compute(firstIndex + 1, secondIndex + 1, firstEndIndex, secondEndIndex);
			long value2 = 1 + compute(firstIndex + 1, secondIndex, firstEndIndex, secondEndIndex);
			long value3 = 1 + compute(firstIndex, secondIndex + 1, firstEndIndex, secondEndIndex);
			result = value1 < value2 ? value1 : value2;
			result = result < value3 ? result : value3;
		}

		return result;
	}

	private void initTopDown() {
		for (int i = 0; i < optimalCost.length; i++) {
			for (int j = 0; j < optimalCost[0].length; j++) {
				optimalCost[i][j] = -1;
			}
		}
	}

	/**
	 * The top down approach using memoization.
	 */
	public long topDown(final int firstEndIndex, final int secondEndIndex) {
		initTopDown();
		return topDownMemoized(0, 0, firstEndIndex, secondEndIndex);
	}

	private long topDownMemoized(final int firstIndex, final int secondIndex, final int firstEndIndex,
	        final int secondEndIndex) {
		if (firstIndex == firstEndIndex + 1) {
			return secondEndIndex + 1 - secondIndex;
		} else if (secondIndex == secondEndIndex + 1) {
			return firstEndIndex + 1 - firstIndex;
		}

		if (optimalCost[firstIndex][secondIndex] != -1) {
			return optimalCost[firstIndex][secondIndex];
		}

		long result = 0;
		if (first[firstIndex] == second[secondIndex]) {
			return topDownMemoized(firstIndex + 1, secondIndex + 1, firstEndIndex, secondEndIndex);
		} else {
			long value1 = 1 + topDownMemoized(firstIndex + 1, secondIndex + 1, firstEndIndex, secondEndIndex);
			long value2 = 1 + topDownMemoized(firstIndex + 1, secondIndex, firstEndIndex, secondEndIndex);
			long value3 = 1 + topDownMemoized(firstIndex, secondIndex + 1, firstEndIndex, secondEndIndex);
			result = value1 < value2 ? value1 : value2;
			result = result < value3 ? result : value3;
		}
		optimalCost[firstIndex][secondIndex] = result;
		return result;
	}

	private void initBottomsUp() {
		for (int i = 0; i < optimalCost.length; i++) {
			optimalCost[i][0] = i;
		}
		for (int i = 0; i < optimalCost[0].length; i++) {
			optimalCost[0][i] = i;
		}
	}

	/**
	 * The bottoms up approach.
	 */
	public long[][] bottomsUp() {
		initBottomsUp();

		for (int i = 1; i < optimalCost.length; i++) {
			for (int j = 1; j < optimalCost[0].length; j++) {
				long result = 0;
				char path = '\0';
				if (first[i - 1] == second[j - 1]) {
					result = optimalCost[i - 1][j - 1];
					path = 'm';
				} else {
					long value1 = 1 + optimalCost[i - 1][j - 1];
					long value2 = 1 + optimalCost[i - 1][j];
					long value3 = 1 + optimalCost[i][j - 1];
					result = value1 < value2 ? value1 : value2;
					path = value1 < value2 ? 's' : 'i';
					result = result < value3 ? result : value3;
					path = result < value3 ? path : 'd';

				}
				optimalCost[i][j] = result;
				optimalPath[i][j] = path;
			}
		}
		return optimalCost;
	}

	/**
	 * Print the optimal cost for the two sequences.
	 */
	public void printOptimalCost() {
		for (int i = 0; i < optimalCost.length; i++) {
			System.out.println(Arrays.toString(optimalCost[i]));
		}
	}

	/**
	 * Print the optimal path for the two sequences.
	 */
	public void printOptimalPath() {
		for (int i = 0; i < optimalPath.length; i++) {
			System.out.println(Arrays.toString(optimalPath[i]));
		}
	}

	/**
	 * main.
	 */
	public static void main(final String[] args) {
		char[] first = { 't', 'h', 'o', 'u', ' ', 's', 'h', 'a', 'l', 't', ' ', 'n', 'o', 't' };
		char[] second = { 'y', 'o', 'u', ' ', 's', 'h', 'o', 'u', 'l', 'd', ' ', 'n', 'o', 't' };
		EditDistance ed = new EditDistance(first, second);
		long currentTime1 = System.nanoTime();
		System.out.println("Recursive solution cost: " + ed.recursive(12, 8));
		System.out.println("Recursive solution time: " + (System.nanoTime() - currentTime1));
		long currentTime2 = System.nanoTime();
		System.out.println("Top Down solution optimal cost: " + ed.topDown(12, 8));
		System.out.println("Top Down solution time: " + (System.nanoTime() - currentTime2));
		long currentTime3 = System.nanoTime();
		ed.bottomsUp();
		System.out.println("Bottoms Up solution time: " + (System.nanoTime() - currentTime3));
		System.out.println("Bottoms Up solution optimal cost");
		ed.printOptimalCost();
		System.out.println("Bottoms Up solution optimal path");
		ed.printOptimalPath();

	}

}
