package com.stevefung.classes.stanford.algorithms.p06;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.Iterator;

public class TwoSum {

	private HashMap<BigInteger, Integer> map = new HashMap<BigInteger, Integer>();
	
	public TwoSum(String testfile) {
		// Read in file

		BufferedReader br = null;
		
		try {
			String line;
			
			br = new BufferedReader(new FileReader(testfile));
			
			while((line = br.readLine()) != null) {
				if (debug) System.err.println("Read in: " + line);
				map.put(new BigInteger(line), null);
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
	}
	
	public int findSums(int min, int max) {
		int count = 0;
		for (int i = min; i <= max; i++) {
			Iterator<BigInteger> it = map.keySet().iterator();
			BigInteger ib = new BigInteger(""+i);
			while (it.hasNext()) {
				BigInteger n = it.next();
				if (!n.equals(ib.subtract(n)) && map.containsKey(ib.subtract(n))) {
					if (debug) System.err.println("\tFound solution: [" + i + "]\t" + n + " : " + ib.subtract(n));
					count++;
					break;
				}
			}
		}
		return count;
	}
	
	public static boolean debug = false;
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String testfile = "test/p06/2sum.txt";
//		String testfile = "test/p06/p06_1-test01.in";
		
		long startTime = System.currentTimeMillis();
		
		TwoSum ts = new TwoSum(testfile);
		int sums = ts.findSums(-10000, 10000);
		
		long endTime = System.currentTimeMillis();
		System.out.println("Solution: " + sums);
		System.out.println("Test took " + (endTime-startTime) + " milliseconds.  i.e. " + (endTime-startTime)/(60*1000) + " minutes"
				+ " and " + ((endTime-startTime)%(60*1000)/1000) + " seconds.");
	}

}
