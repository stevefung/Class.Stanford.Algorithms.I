package com.stevefung.classes.stanford.algorithms.p06;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class TwoSumList {

	private ArrayList<BigInteger> list = new ArrayList<BigInteger>();
	
	public TwoSumList(String testfile) {
		// Read in file

		HashMap<String, String> map = new HashMap<String, String>();
		BufferedReader br = null;
		
		try {
			String line;
			
			br = new BufferedReader(new FileReader(testfile));
			
			while((line = br.readLine()) != null) {
				if (debug) System.err.println("Read in: " + line);
				if (!map.containsKey(line)) {
					list.add(new BigInteger(line));
					map.put(line, line);
				}
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
		
		Collections.sort(list);
	}
	
	public int findSums(int min, int max) {
		BigInteger bMin = new BigInteger(""+min);
		BigInteger bMax = new BigInteger(""+max);
		HashMap<BigInteger, BigInteger> solutions = new HashMap<BigInteger, BigInteger>();
		if (debug) System.err.println("Checking array: " + list.toString());
		for (int i = 0; i < list.size(); i++) {
			int j = list.size()-1;
			while(j > i && list.get(i).add(list.get(j)).compareTo(bMax) > 0) {
				j--;
			}
			if (debug) System.err.println("\tChecking in range: " + i + " : " + j);
			while(j > i && list.get(i).add(list.get(j)).compareTo(bMin) >= 0) {
				j--;
				solutions.put(list.get(i).add(list.get(j)), null);
				if (debug) System.err.println("\t\tFound solution: " + list.get(i) + " : " + list.get(j));
			}
		}
		return solutions.size();
	}
	
	public static boolean debug = false;
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String testfile = "test/p06/2sum.txt";
//		String testfile = "test/p06/p06_1-test02.in";
		
		long startTime = System.currentTimeMillis();
		
		TwoSumList ts = new TwoSumList(testfile);
		int sums = ts.findSums(-100, 100);
		
		long endTime = System.currentTimeMillis();
		System.out.println("Solution: " + sums);
		System.out.println("Test took " + (endTime-startTime) + " milliseconds.  i.e. " + (endTime-startTime)/(60*1000) + " minutes"
				+ " and " + ((endTime-startTime)%(60*1000)/1000) + " seconds.");
	}

}
