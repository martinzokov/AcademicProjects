/*
 * Created on Nov 28, 2003
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package sorting2014;

/**
 * @author rcs
 * Modified version of the BubbleSort algorithm. Modified by mvz.
 * This improved version uses a boolean variable so that after
 * the first iteration it checks if the array is already sorted
 * and stops the algorithm from trying to further sort.
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class BubbleSort implements Sorter {

	/* (non-Javadoc)
	 * @see sorting.Sorter#sort(java.lang.Comparable[])
	 */
	@SuppressWarnings("unchecked")
	public void sort(Comparable[] items, int cutoff) {
		Comparable temp;
		boolean isSorted = true;
		for (int i=0; i<items.length-1; i++)
		{	
			for (int j=0; j<items.length-1-i; j++)
			{
				if (items[j].compareTo(items[j+1])>0)
				{
					temp=items[j+1];
					items[j+1]=items[j];
					items[j]=temp;
					isSorted = false;
				}
			}
			if(isSorted){
				break;
			}
		}
	}
}
