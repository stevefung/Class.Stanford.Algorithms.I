package com.stevefung.classes.stanford.algorithms.p01;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class Inversions {
	
	private static String testfile = "test/p01/IntegerArray.txt";
	//private static String testfile = "test/p01/test01.in";
	private static boolean debug = false;
	
	private ArrayList<Integer> array;
	private BigInteger inversions;

	public BigInteger getInversions() {
		return inversions;
	}

	public void setInversions(BigInteger inversions) {
		this.inversions = inversions;
	}

	public ArrayList<Integer> getArray() {
		return array;
	}

	public void setArray(ArrayList<Integer> array) {
		this.array = array;
	}
	
	public BigInteger sortAndCountInversions() {
		if (debug) System.err.println("Counting [" + array.toString() + "]");
		if (array.size() <= 1) {
			return BigInteger.ZERO;
		}
		
		if (array.size() == 2) {
			if (array.get(0) > array.get(1)) {
				if (debug) System.err.println("Adding 1 inversions from base case " + array);
				Integer t = array.get(0);
				array.set(0, array.get(1));
				array.set(1, t);
				return BigInteger.ONE;
			} else {
				return BigInteger.ZERO;
			}
		}
		
		BigInteger addedInversions;
		
		if (debug) System.err.println("Recursing left with " + array.subList(0, array.size()/2));
		Inversions left = new Inversions(array.subList(0, array.size()/2));
		addedInversions = left.sortAndCountInversions();
		if (debug) System.err.println("\tAdded " + addedInversions + " from " + array.subList(0, array.size()/2));
		inversions = inversions.add(addedInversions);
		
		if (debug) System.err.println("Recursing right with " + array.subList(array.size()/2, array.size()));
		Inversions right = new Inversions(array.subList(array.size()/2, array.size()));
		addedInversions = right.sortAndCountInversions();
		if (debug) System.err.println("\tAdded " + addedInversions + " from " + array.subList(array.size()/2, array.size()));
		inversions = inversions.add(addedInversions);
		
		ArrayList<Integer> leftArray = left.getArray();
		ArrayList<Integer> rightArray = right.getArray();
		
		int i = 0;
		int j = 0;
		
		for (int k = 0; k < array.size(); k++) {
			if (j >= rightArray.size() || (i < leftArray.size() && leftArray.get(i) <= rightArray.get(j))) {
				array.set(k, leftArray.get(i));
				if (debug) System.err.println("Adding " + j + " inversions for (" + i + "," + j + ") " + leftArray.get(i) + " from " + leftArray + " " + rightArray);
				i++;
				inversions = inversions.add(BigInteger.valueOf(j));
			} else {
				array.set(k, rightArray.get(j));
				j++;
			}
		}
		
		return inversions;
	}

	public Inversions(List<Integer> array) {
		super();
		this.array = new ArrayList<Integer>(array);
		inversions = BigInteger.valueOf(0);
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
		
		Inversions helper = new Inversions(array);
		
		BigInteger inversions = helper.sortAndCountInversions();
		
		System.out.println("Inversions: " + inversions);

	}

}
