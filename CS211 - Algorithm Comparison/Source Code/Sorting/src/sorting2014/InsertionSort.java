package sorting2014;

/**
 * 
 * @author mvz
 * 
 * Implementation of the InsertionSort algorithm.
 *
 */

public class InsertionSort implements Sorter{

	/* (non-Javadoc)
	 * @see sorting2014.Sorter#sort(java.lang.Comparable[], int)
	 */
	@Override
	public void sort(Comparable[] items, int cutoff) {
		Comparable key;
		for(int i = 1; i < items.length; i++){
			key = items[i];
			
			int j;
			for(j = i - 1; j >= 0; j--){
				if(items[j].compareTo(key) < 0){
					break;
				}
				items[j+1] = items[j];
			}
			items[j+1] = key;
		}
	}
	
}
