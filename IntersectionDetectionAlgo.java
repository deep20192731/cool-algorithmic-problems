import java.util.ArrayList;
import java.util.List;


public class IntersectionDetectionAlgo {

	int findIntersectingElement(List<Integer> firstList, List<Integer> secondList) {
		List<Integer> sortedLis = mergeSort(firstList);
		for(Integer i : secondList) {
		int searchedEle = binarySearch(i, 0, sortedLis);
		if(searchedEle != -1) {
			return sortedLis.get(searchedEle);
		}
		}
		return -1;
	}

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

	public int binarySearch(int searchElement, int offset, List<Integer> sortedList) {
		// Terminating cases
		if(sortedList.size() == 0 || (sortedList.size() == 1 && searchElement != sortedList.get(0))){
			return -1;
	}

		int midElementPosition =sortedList.size()/2;
		
	// Base case
		if(sortedList.get(midElementPosition) == searchElement) {
			return midElementPosition + offset;
		} else if(sortedList.get(midElementPosition) > searchElement) {
			return binarySearch(searchElement, 0, sortedList.subList(0, midElementPosition));	
	} else {
		return binarySearch(searchElement, midElementPosition, sortedList.subList(midElementPosition,
				sortedList.size()));
	}
	}

	public static void main(String[] args) {
		List<Integer> listToSort = new ArrayList<Integer>();
		listToSort.add(1000);
		listToSort.add(65);
		listToSort.add(34);
		listToSort.add(100);
		listToSort.add(12);
		listToSort.add(11);
		
		List<Integer> sortedList = new ArrayList<Integer>();
		sortedList.add(1);
		sortedList.add(3);
		sortedList.add(70);
		sortedList.add(9);
		sortedList.add(10000);
		sortedList.add(15);
		IntersectionDetectionAlgo algo = new IntersectionDetectionAlgo();
		int s = algo.findIntersectingElement(listToSort, sortedList);
		System.out.println(s);
	}
}
