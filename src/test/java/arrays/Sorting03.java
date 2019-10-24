package arrays;

import java.util.Arrays;

import za.ac.sun.cs.coastal.Symbolic;

public class Sorting03 {

	private static final int N = 4;

	public static void sort(int[] arr) {
		int minValue = arr[0];
		for (int i = 1; i < arr.length; i++) {
			if (arr[i] < minValue) {
				minValue = arr[i];
			}
		}
		Arrays.sort(arr);
		if (arr[0] != minValue) {
			Symbolic.stop("MIN NOT AT ARR[0]");
		}
	}

	public static void main(String[] args) {
		int[] a = new int[N];
		/*
		int seed = 123;
		for (int i = 0; i < N; i++) {
			seed = (seed * 59 + 7) % 511;
			a[i] = seed % 101;
		}
		*/
		sort(a);
	}

}
