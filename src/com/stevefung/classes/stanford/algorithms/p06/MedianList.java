package com.stevefung.classes.stanford.algorithms.p06;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import org.apache.commons.collections.buffer.PriorityBuffer;

public class MedianList {

	PriorityBuffer high = new PriorityBuffer(true);
	PriorityBuffer low = new PriorityBuffer(false);
	
	public void add(int n) {
		if (low.size() > 0 && n > ((Integer) low.get()).intValue()) {
			high.add(Integer.valueOf(n));
		} else {
			low.add(Integer.valueOf(n));
		}
		
		while (high.size() > low.size()) {
			low.add(high.remove());
		}
		
		while (low.size() > high.size() + 1) {
			high.add(low.remove());
		}
	}
	
	public int median() {
		return ((Integer) low.get()).intValue();
	}
	
	public static boolean debug = false;
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
//		String testfile = "test/p06/Median.txt";
		String testfile = "test/p06/p06_2-test04.in";
		
		long startTime = System.currentTimeMillis();
		
		MedianList list = new MedianList();
		int sum = 0;
		
		// Read in file

		BufferedReader br = null;
		
		try {
			String line;
			
			br = new BufferedReader(new FileReader(testfile));
			
			while((line = br.readLine()) != null) {
				int n = Integer.parseInt(line);
				list.add(n);
				if (debug) System.err.println("Read in: " + n);
				if (debug) System.err.println("\tMedian: " + list.median());
				sum = (sum + list.median()) % 10000;
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
		
		long endTime = System.currentTimeMillis();
		System.out.println("Solution: " + sum);
		System.out.println("Test took " + (endTime-startTime) + " milliseconds.  i.e. " + (endTime-startTime)/(60*1000) + " minutes"
				+ " and " + ((endTime-startTime)%(60*1000)/1000) + " seconds.");
	}

}
