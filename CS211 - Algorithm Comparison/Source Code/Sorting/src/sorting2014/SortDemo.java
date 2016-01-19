/*
 * Created on Nov 28, 2003
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package sorting2014;

import java.io.*;
import java.util.*;

/**
 * The Class SortDemo. Used for testing algorithms. I have added some methods to the
 * original version of the class in order to make testing easier.
 *
 * @author rcs
 * Modified by: mvz
 * 
 *         To change the template for this generated type comment go to
 *         Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
@SuppressWarnings("unchecked")
public class SortDemo {


	public Comparable[] readData(String fileName) {
		Comparable[] items;
		File file = new File(fileName);
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(file));
		} catch (FileNotFoundException e) {
			System.out.println("File not found " + fileName);
			System.exit(0);
		}
		boolean eof = false;
		String inLine = null;
		int numLines = 0;
		while (!eof) {
			try {
				inLine = reader.readLine();
				if (inLine == null) {
					eof = true;
				} else {
					numLines++;
				}
			} catch (IOException e) {
			}
		}
		try {
			reader.close();
		} catch (IOException e) {
		}

		items = new Comparable[numLines];

		try {
			reader = new BufferedReader(new FileReader(file));
		} catch (FileNotFoundException e) {
			System.out.println("File not found " + fileName);
			System.exit(0);
		}
		eof = false;
		inLine = null;
		numLines = 0;
		while (!eof) {
			try {
				inLine = reader.readLine();
				if (inLine == null) {
					eof = true;
				} else {
					items[numLines] = inLine;
					numLines++;
				}
			} catch (IOException e) {
			}
		}
		try {
			reader.close();
		} catch (IOException e) {
		}

		return items;
	}

	public long testOne(String type, Comparable[] items) {
		long start;
		long finish;
		long timeTaken = 0;
		Sorter s = SortFactory.getSorter(type);
		if (s != null) {
			start = Calendar.getInstance().getTimeInMillis();
			s.sort(items, 0);
			finish = Calendar.getInstance().getTimeInMillis();
			timeTaken = finish - start;
		} else {
			System.out
					.println("Failed loading the sorter, no sorting will happen.");
		}
		return timeTaken;
	}

	public void printSortedArray(Comparable[] items) {
		for (int i = 0; i < items.length; i++) {
			System.out.println(items[i]);
		}
	}


	/**
	 * Tests one algorithm. The name of the sort is passed as a string
	 * and the method tests it agains all data set variations
	 *
	 * @param alg the algorithm
	 * @return String with the results for time taken.
	 */
	public String testAlg(String alg) {
		String filenames[] = { "test3.dat", "test3a.dat", "test3b.dat",
				"test4.dat", "test4a.dat", "test4b.dat", "test5.dat",
				"test5a.dat", "test5b.dat", "test6.dat", "test6a.dat",
				"test6b.dat" };
		String filenamesSorted[] = { "test3-sorted.dat", "test3a-sorted.dat",
				"test3b-sorted.dat", "test4-sorted.dat", "test4a-sorted.dat",
				"test4b-sorted.dat", "test5-sorted.dat", "test5a-sorted.dat",
				"test5b-sorted.dat", "test6-sorted.dat", "test6a-sorted.dat",
				"test6b-sorted.dat" };
		String filenamesReversed[] = { "test3-reversed.dat",
				"test3a-reversed.dat", "test3b-reversed.dat",
				"test4-reversed.dat", "test4a-reversed.dat",
				"test4b-reversed.dat", "test5-reversed.dat",
				"test5a-reversed.dat", "test5b-reversed.dat",
				"test6-reversed.dat", "test6a-reversed.dat",
				"test6b-reversed.dat" };
		long timeTaken = 0;
		StringBuffer retLine = new StringBuffer();
		retLine.append(alg + "\n");
		retLine.append("Unsorted\n");
		for (int i = 0; i < filenames.length; i++) {
			Comparable[] items = this.readData(filenames[i]);
			timeTaken = this.testOne("sorting2014." + alg, items);
			retLine.append(filenames[i] + "," + items.length + "," + timeTaken);
			retLine.append("\n");
		}
		retLine.append("Sorted\n");
		timeTaken = 0;
		for (int j = 0; j < filenamesSorted.length; j++) {
			Comparable[] items = this.readData(filenamesSorted[j]);
			timeTaken = this.testOne("sorting2014." + alg, items);
			retLine.append(filenamesSorted[j] + "," + items.length + ","
					+ timeTaken);
			retLine.append("\n");
		}
		retLine.append("Reversed\n");
		timeTaken = 0;
		for (int j = 0; j < filenamesReversed.length; j++) {
			Comparable[] items = this.readData(filenamesReversed[j]);
			timeTaken = this.testOne("sorting2014." + alg, items);
			retLine.append(filenamesReversed[j] + "," + items.length + ","
					+ timeTaken);
			retLine.append("\n");
		}
		return retLine.toString();
	}

	public static void main(String[] args) {
		SortDemo sd = new SortDemo();

		System.out.println(sd.testAlg("QuickSort"));
	}

	/**
	 * Saves a sorted array with all the elements.
	 *
	 * @param name the name of the file.
	 * @param items the array.
	 */
	public void saveSorted(String name, Comparable[] items) {
		BufferedWriter writer = null;
		try {
			writer = new BufferedWriter(new FileWriter(name));
		} catch (IOException ex) {
		}
		Comparable number = 0;

		for (int i = 0; i < items.length; i++) {
			number = items[i];
			try {
				writer.write(number + "\n");
			} catch (IOException e) {
			}
		}

		try {
			writer.close();
		} catch (IOException ex) {
		}
	}
}
