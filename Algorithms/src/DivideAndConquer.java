import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import java.util.Arrays;

import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;

@Path("/divideAndConquer")
public class DivideAndConquer {
	
	@GET
	@Path("/getRank")
	@Produces("application/json")
	public int getRank(int[] elements, int rank) {
		
		// BaseCase
		if (elements.length < 10) {
			return getRankForSmallInput(elements, rank);
		}
		
		int[] medians = divideAndGetMedians(elements, 5);
		int nearestMedian = getRank(medians, (elements.length/10));
		
		elements = partition(elements, nearestMedian);
		int nearestMedianPosition = Arrays.binarySearch(elements, nearestMedian);
		
		if(nearestMedianPosition > rank) {
			return getRank(Arrays.copyOfRange(elements, 0, nearestMedianPosition-1), rank);
		} else if(nearestMedianPosition < rank) {
			return getRank(Arrays.copyOfRange(elements, nearestMedianPosition, elements.length), (rank-nearestMedianPosition));
		} else {
			return nearestMedianPosition;
		}
	}

	private int getRankForSmallInput(int[] elements, int rank) {
		Arrays.sort(elements);
		System.out.println(elements[rank-1] + "\n");
		return elements[rank-1];
	}
	private int[] divideAndGetMedians(int[] elements, int parts) {
		int numberOfParts = elements.length/parts;
		int i = 0;
		int[] medians = {};
		while(numberOfParts != 0) {
			int startOfPart = i*parts;
			int endOfPart = (startOfPart + parts)-1;
			if(elements.length < endOfPart) {
				endOfPart = elements.length-1;
			}
			System.out.println(startOfPart + " " + endOfPart + "\n");
			System.out.println("Median finding\n");
			medians[i] = getRankForSmallInput(Arrays.copyOfRange(elements, startOfPart, endOfPart), (parts/2));
			System.out.println("Median#" + i + " is " + medians[i] + "\n");
			i++;
			numberOfParts--;
		}
		return medians;
	}
	
	private int[] partition(int[] elements, int pivot) {
		int pivotPositon = Arrays.binarySearch(elements, pivot);
		int i = 0;
		int j = elements.length-1;
		
		while(i < pivotPositon || j > pivotPositon) {
			while(elements[i] < pivot) {
				i++;
			} 
			while(elements[j] > pivot) {
				j--;
			}
			int temp = elements[i];
			elements[i] = elements[j];
			elements[j] = temp;
		}
		return elements;
	}
	
	public static void main(String[] args) {
		DivideAndConquer algo = new DivideAndConquer();
		int[] elements = {1,2, 5, 4,3, 10, 9, 6, 7, 8, 11, 60, 31, 45, 19, 18, 65, 123, 90, 1098};
		int rank = algo.getRank(elements, 3);
		System.out.println(rank);
	}
}
