import java.util.ArrayList;
import java.util.List;

public class Sort {
	
	public List<Integer> mergeSort(List<Integer> listToSort) {
		if(listToSort.size() > 1) {
			int midElementPos = listToSort.size()/2;
			List<Integer> firstList = mergeSort(listToSort.subList(0, midElementPos));
			List<Integer> secondList = mergeSort(listToSort.subList(midElementPos, listToSort.size()));
			return merge(firstList, secondList);
	} else {
		return listToSort;
	}
	}

	private List<Integer> merge(List<Integer> firstList, List<Integer> secondList) {
		List<Integer> mergedList = new ArrayList<Integer>();
		int firstListSize = firstList.size();
		int secondListSize = secondList.size();
	if(firstListSize >0 && secondListSize>0) {
			int firstListIter = 0;
			int secondListIter = 0;
	while(firstListIter < firstListSize && secondListIter < secondListSize) {
		if(firstList.get(firstListIter) > secondList.get(secondListIter)) {
			mergedList.add(secondList.get(secondListIter));
			secondListIter++;
	} else if(firstList.get(firstListIter) < secondList.get(secondListIter)) {
			mergedList.add(firstList.get(firstListIter));
			firstListIter++;
	} else {
		mergedList.add(firstList.get(firstListIter));
		mergedList.add(secondList.get(secondListIter));
		firstListIter++;
		secondListIter++;
	}
	}
	if(firstListIter < firstListSize) {
		mergedList.addAll(firstList.subList(firstListIter, firstListSize));
	} else if(secondListIter < secondListSize) {
		mergedList.addAll(secondList.subList(secondListIter, secondListSize));
	}
	}
	return mergedList;
	}

	
	public static void main(String[] args) {
		System.out.println("Deep");
		List<Integer> listToSort = new ArrayList<Integer>();
		listToSort.add(10);
		listToSort.add(65);
		listToSort.add(34);
		listToSort.add(100);
		listToSort.add(12);
		listToSort.add(11);
		
		Sort mergeSort = new Sort();
		System.out.println("Deep");
		List<Integer> sortedList = mergeSort.mergeSort(listToSort);
		System.out.println("Deep");
		for(Integer i : sortedList) {
			System.out.println(i);
		}
	}
}
