import java.util.ArrayList;
import java.util.List;

public class Search {
	int binarySearch(int searchElement, int offset, List<Integer> sortedList) {
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
		Search binarySearchAlgo = new Search();
		
		List<Integer> sortedList = new ArrayList<Integer>();
		sortedList.add(1);
		sortedList.add(3);
		sortedList.add(6);
		sortedList.add(9);
		sortedList.add(10);
		sortedList.add(15);
		int pos = binarySearchAlgo.binarySearch(-1, 0, sortedList);
		System.out.println(pos);
	}
}
