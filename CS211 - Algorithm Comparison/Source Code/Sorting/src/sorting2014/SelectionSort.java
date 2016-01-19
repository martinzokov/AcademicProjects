package sorting2014;

public class SelectionSort implements Sorter {

	@Override
	public void sort(Comparable[] items, int cutoff) {
		
		int min;
		int i,j;
		for(i = 0; i < items.length; i++){
			min = i;
			
			for(j = i+1; j < items.length;j++){
				if(items[j].compareTo(items[min]) < 0){
					min = j;
				}
			}
			if(min != i){
				Comparable temp;
				temp = items[min];
				items[min] = items[i];
				items[i]=temp;
			}
		}

	}


}
