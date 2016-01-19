package sorting2014;

public class ShellSort implements Sorter {

	@Override
	public void sort(Comparable[] items, int cutoff) {

		for (int gap = items.length / 2; gap > 0; gap /= 2.2) {
			Comparable key;
			for (int i = gap; i < items.length; i++) {
				key = items[i];
				int j;
				for (j = i ; j >= gap; j-=gap) {
					if (items[j-gap].compareTo(key) < 0) {
						break;
					}
					items[j] = items[j-gap];
				}
				items[j] = key;
			}
		}
	}

}
