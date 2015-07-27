package com.algo.dp;

import java.util.Arrays;

/**
 * Given two sequences X = <X1...Xn> and Y = <Y1...Yn> find a maximum length
 * common subsequence of X and Y. Common characters must appear in the same
 * order but not necessarily consecutively.
 */
public class LongestCommonSubsequence {

	private final char[] first;
	private final char[] second;
	private final int[][] optimizedLength;
	private final char[][] optimizedSequence;

	public LongestCommonSubsequence(final char[] first, final char[] second) {
		this.first = first;
		this.second = second;
		this.optimizedLength = new int[first.length + 1][second.length + 1];
		this.optimizedSequence = new char[first.length + 1][second.length + 1];
	}

	private void init(final int value) {
		for (int i = 0; i < first.length + 1; i++) {
			for (int j = 0; j < second.length + 1; j++) {
				optimizedLength[i][j] = value;
				optimizedSequence[i][j] = 'x';
			}
		}
	}

	/**
	 * The recursive solution.
	 */
	public int recursive() {
		return compute(first.length - 1, second.length - 1);
	}

	/**
	 * The top down approach using memoization.
	 */
	public int topDown() {
		init(-1);
		return topDownMemoized(first.length - 1, second.length - 1);
	}

	/**
	 * The bottoms up approach.
	 */
	public int[][] bottomsUp() {
		init(0);
		for (int i = 0; i < first.length; i++) {
			for (int j = 0; j < second.length; j++) {
				int result = 0;
				char position = ' ';
				if (first[i] == second[j]) {
					result = optimizedLength[(i - 1) + 1][(j - 1) + 1] + 1;
					position = '\\';
				} else {
					int value1 = optimizedLength[(i) + 1][(j - 1) + 1];
					int value2 = optimizedLength[(i - 1) + 1][(j) + 1];
					if (value1 > value2) {
						result = value1;
						position = '<';
					} else {
						result = value2;
						position = '^';
					}
				}
				optimizedLength[(i) + 1][(j) + 1] = result;
				optimizedSequence[(i) + 1][(j) + 1] = position;
			}
		}
		return optimizedLength;
	}

	/**
	 * Print the optimal length for the two sequences.
	 */
	public void printOptimalLength() {
		for (int i = 0; i < optimizedLength.length; i++) {
			System.out.println(Arrays.toString(optimizedLength[i]));
		}
	}

	/**
	 * Return the optimal sequence for the two sequences.
	 */
	public void printOptimalSequence() {
		for (int i = 0; i < optimizedSequence.length; i++) {
			System.out.println(Arrays.toString(optimizedSequence[i]));
		}
	}

	private int topDownMemoized(final int flastIndex, final int slastIndex) {
		if (flastIndex == -1 || slastIndex == -1) {
			return 0;
		} else if (optimizedLength[flastIndex + 1][slastIndex + 1] != -1) {
			return optimizedLength[flastIndex + 1][slastIndex + 1];
		} else {
			int value = 0;
			if (first[flastIndex] == second[slastIndex]) {
				value = topDownMemoized(flastIndex - 1, slastIndex - 1) + 1;
			} else {
				int value1 = topDownMemoized(flastIndex, slastIndex - 1);
				int value2 = topDownMemoized(flastIndex - 1, slastIndex);
				if (value1 > value2) {
					value = value1;
				} else {
					value = value2;
				}
			}
			optimizedLength[flastIndex + 1][slastIndex + 1] = value;
			return value;
		}
	}

	private int compute(final int flastIndex, final int slastIndex) {
		if (flastIndex == -1 || slastIndex == -1) {
			return 0;
		}

		int value = 0;
		if (first[flastIndex] == second[slastIndex]) {
			value = compute(flastIndex - 1, slastIndex - 1) + 1;
		} else {
			int value1 = compute(flastIndex, slastIndex - 1);
			int value2 = compute(flastIndex - 1, slastIndex);
			if (value1 > value2) {
				value = value1;
			} else {
				value = value2;
			}
		}
		return value;
	}

	/**
	 * main.
	 */
	public static void main(final String[] args) {
		char[] first = { 'A', 'B', 'C', 'B', 'D', 'A', 'B' };
		char[] second = { 'B', 'D', 'C', 'A', 'B', 'A' };

		LongestCommonSubsequence lcs = new LongestCommonSubsequence(first, second);
		long currentTime1 = System.nanoTime();
		System.out.println("Recursive solution optimal length: " + lcs.recursive());
		System.out.println("Recursive solution time: " + (System.nanoTime() - currentTime1));
		long currentTime2 = System.nanoTime();
		System.out.println("Top Down solution optimal length: " + lcs.topDown());
		System.out.println("Top Down solution time: " + (System.nanoTime() - currentTime2));
		long currentTime3 = System.nanoTime();
		lcs.bottomsUp();
		System.out.println("Bottoms Up solution time: " + (System.nanoTime() - currentTime3));
		System.out.println("Bottoms Up solution optimal length");
		lcs.printOptimalLength();
		System.out.println("Bottoms Up solution optimal sequence");
		lcs.printOptimalSequence();
	}

}
