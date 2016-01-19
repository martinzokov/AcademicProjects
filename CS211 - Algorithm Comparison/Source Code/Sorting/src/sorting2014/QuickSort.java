package sorting2014;

/**
 * @author mvz
 * The implementation of QuickSort.
 */
public class QuickSort implements Sorter{
	
	/* (non-Javadoc)
	 * @see sorting2014.Sorter#sort(java.lang.Comparable[], int)
	 */
	@Override
	public void sort(Comparable[] items, int cutoff) {
		quickSort(items,0,items.length-1);
	}
	
	/**
	 * Quick sort algorithm. Recursive method to sort the array.
	 *
	 * @param items the array
	 * @param left the first element of the array/sub-array
	 * @param right the last element of the array/sub-array
	 */
	public void quickSort(Comparable[] items, int left, int right){
		if(items.length<=1){
			return;
		}
		
		int pivot = findMedian(items, left, right);
		
		pivot = partition(items, left,right,pivot);
		
		if(left < pivot){
			quickSort(items,left,pivot-1);
		}
		if(right > pivot){
			quickSort(items, pivot+1,right);
		}
		
	}
	
	/**
	 * Partition method to sort a sub-array and put the pivot in its correct position
	 *
	 * @param items the array/sub-array
	 * @param left the first element of the array
	 * @param right the last element of the array
	 * @param pivot the pivot
	 * @return the new position of the pivot
	 */
	public int partition(Comparable[] items, int left, int right, int pivot){
		
		swap(items, pivot, left);
		int cursor = left + 1;
		
		for(int i = cursor; i <= right; i++){
			
			if(items[left].compareTo(items[i])>0){
				swap(items, cursor,i);
				cursor++;
			}
		}
		
		swap(items, left, cursor-1);
		return cursor-1;
		
	}
	
	/**
	 * Finds the median value for the passed array.
	 *
	 * @param items the array
	 * @param left the first element of the array
	 * @param right the last element of the array
	 * @return the pivot
	 */
	@SuppressWarnings("unchecked")
	public int findMedian(Comparable[] items, int left, int right){
		int pivot;
		int middle = left + (right-left)/2;
		
		if(items[left].compareTo(items[middle]) > 0){
			if(items[left].compareTo(items[right]) < 0){
				pivot = left;
			}else if(items[middle].compareTo(items[right]) < 0){
				pivot = middle;
			}else{
				pivot = right;
			}
		}else {
			if(items[middle].compareTo(items[right]) < 0){
				pivot = middle;
			}else if(items[left].compareTo(items[right]) > 0){
				pivot = left;
			}else{
				pivot = right;
			}
		}
		
		return pivot;
	}
	
	/**
	 * Swaps two elements in the array.
	 *
	 * @param items the array
	 * @param first the first element
	 * @param second the second element
	 */
	public void swap(Comparable[] items, int first, int second){
		Comparable temp;
		temp = items[first];
		items[first] = items[second];
		items[second] = temp;
	}
	
}
