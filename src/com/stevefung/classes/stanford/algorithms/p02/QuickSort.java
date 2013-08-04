package com.stevefung.classes.stanford.algorithms.p02;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class QuickSort {

	private static String testfile = "test/p02/QuickSort.txt";
	//private static String testfile = "test/p02/test02.in";
	//private static String testfile = "test/p02/1000.txt";
	private static boolean debug = false;
	
	public static int sort(ArrayList<Integer> array) {
		return sort(array, 0, array.size()-1);
	}
	
	private static int choosePivot(ArrayList<Integer> array, int leftIndex, int rightIndex, int method) {
		
		switch (method) {
		case 0:
			return leftIndex;
		case 1:
			return rightIndex;
		case 2:
			Integer l = array.get(leftIndex);
			Integer r = array.get(rightIndex);
			Integer m = array.get(((rightIndex-leftIndex+2)/2) - 1 + leftIndex);
			if (debug) System.err.println("\t\tChoosing median from " + l + ", " + m + ", " + r);
			if ((l > r && l < m) || (l > m && l < r)) {
				return leftIndex;
			} else if ((r > l && r < m) || (r > m && r < l)) {
				return rightIndex;
			} else {
				return ((rightIndex-leftIndex+2)/2) - 1 + leftIndex;
			}
		default:
			return leftIndex;
		}
	}
	
	private static int sort(ArrayList<Integer> array, int leftIndex, int rightIndex) {
		if (debug) System.err.println("Recursed with " + array.toString() + "\t" + leftIndex + "\t" + rightIndex);
		if (leftIndex < 0 || rightIndex >= array.size() || rightIndex <= leftIndex) {
			return 0;
		}

		Integer t;

		// Choose pivot
		int pivot = choosePivot(array, leftIndex, rightIndex, 2);
		t = array.get(pivot);
		array.set(pivot, array.get(leftIndex));
		array.set(leftIndex, t);
		if (debug) System.err.println("\tPivoting around " + t + ": " + array.toString());
		
		Integer p = array.get(leftIndex);
		int i = leftIndex+1;
		int ret = rightIndex - leftIndex;
		
		for (int j = leftIndex+1; j <= rightIndex; j++) {
			if (array.get(j) < p) {
				t = array.get(j);
				array.set(j, array.get(i));
				array.set(i, t);
				i++;
			}
		}
		
		t = array.get(leftIndex);
		array.set(leftIndex, array.get(i-1));
		array.set(i-1, t);
		
		if (debug) System.err.println("\tFinished partition " + array.toString());
		
		ret += sort(array, leftIndex, i-2);
		ret += sort(array, i, rightIndex);
		
		return ret;
	}
	

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// Read in file
		
		BufferedReader br = null;
		ArrayList<Integer> array = new ArrayList<Integer>();
		
		try {
			String line;
			
			br = new BufferedReader(new FileReader(testfile));
			
			while((line = br.readLine()) != null) {
				array.add(Integer.valueOf(line));
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (br != null) {
					br.close();
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		
		System.out.println("Read in " + array.size() + " numbers.");
		
		System.out.println("\nArray: " + array.toString());
		
		int comparisons = QuickSort.sort(array);
		
		System.out.println("\n\nSorted [" + comparisons + "]: " + array.toString());
		
		// Left: 162085
		// Right: 164123
		// Median:   --not 113631
	}

}
