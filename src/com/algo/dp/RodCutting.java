package com.algo.dp;

import java.util.Arrays;

/**
 * Find the maximum revenue obtained by cutting the rod of length 'n', given the
 * price for each length 1..n.
 */
public class RodCutting {

	private final long[] price;
	private final long[] optimalPrice;
	private final int[] optimalLength;

	public RodCutting(final long[] price) {
		this.price = price;
		this.optimalPrice = new long[price.length + 1];
		this.optimalLength = new int[price.length + 1];
		optimalPrice[0] = 0;
		optimalLength[0] = 0;
		for (int i = 0; i < price.length; i++) {
			optimalPrice[i + 1] = Long.MIN_VALUE;
		}
	}

	private long compute(final int size) {
		if (size == 0) {
			return 0;
		}
		long result = Long.MIN_VALUE;
		for (int i = 1; i <= size; i++) {
			long value = price[i - 1] + compute(size - i);
			if (value > result) {
				result = value;
			}
		}
		return result;
	}

	private long topDownMemoized(final int size) {
		if (size == 0) {
			return 0;
		}
		if (optimalPrice[size] > Long.MIN_VALUE) {
			return optimalPrice[size];
		} else {
			long result = Long.MIN_VALUE;
			for (int i = 1; i <= size; i++) {
				long value = price[i - 1] + topDownMemoized(size - i);
				if (value > result) {
					result = value;
				}
			}
			optimalPrice[size] = result;
			return result;
		}
	}

	/**
	 * The recursive solution.
	 */
	public long recursive() {
		return compute(price.length);
	}

	/**
	 * The top down approach using memoization.
	 */
	public long topDown() {
		return topDownMemoized(price.length);
	}

	/**
	 * The bottoms up approach.
	 */
	public long[] bottomsUp() {
		for (int l = 1; l <= price.length; l++) {
			long result = Long.MIN_VALUE;
			int length = 0;
			for (int i = 1; i <= l; i++) {
				long value = price[i - 1] + optimalPrice[l - i];
				if (value > result) {
					result = value;
					length = i;
				}
			}
			optimalPrice[l] = result;
			optimalLength[l] = length;
		}
		return optimalPrice;
	}

	/**
	 * Return the optimal length of the initial cut for each size.
	 */
	public int[] getOptimalLength() {
		return optimalLength;
	}

	/**
	 * main.
	 */
	public static void main(final String[] args) {
		long[] price = { 1, 5, 8, 9, 10, 17, 17, 20, 24, 30 };
		RodCutting rc = new RodCutting(price);
		long currentTime1 = System.nanoTime();
		System.out.println("Recursive solution optimal price: " + rc.recursive());
		System.out.println("Recursive solution time: " + (System.nanoTime() - currentTime1));
		long currentTime2 = System.nanoTime();
		System.out.println("Top Down solution optimal price: " + rc.topDown());
		System.out.println("Top Down solution time: " + (System.nanoTime() - currentTime2));
		long currentTime3 = System.nanoTime();
		System.out.println("Bottoms Up solution optimal prices: " + Arrays.toString(rc.bottomsUp()));
		System.out.println("Bottoms Up solution time: " + (System.nanoTime() - currentTime3));
		System.out.println("Optimal length cut by size: " + Arrays.toString(rc.getOptimalLength()));

	}

}
