import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Permutations {

	// By Recursion
	Set<String> getAllPermuations(String str) {
		// Base case
		//System.out.println(str);
	Set<String> permutations = new HashSet<String>();
	if(str.length() <= 1) {
		permutations.add(str);
	}
	else {
		String leftPart = str.substring(0, (str.length()/2));
		String rightPart = str.substring(str.length()/2);
		//System.out.println(leftPart + "   " + rightPart);

	// Divide
	Set<String> leftPartPermu = getAllPermuations(leftPart);
	Set<String> rightPartPermu = getAllPermuations(rightPart);
	//System.out.println(leftPartPermu + "   " + rightPartPermu);

	// Conquer
	for(String l : leftPartPermu) {
		for(String r : rightPartPermu) {
			for(int i = 0; i < l.length(); i++) {
				//System.out.println(l);
				String p = l.substring(0, i) + r + l.substring(i);
				//System.out.println(l +"   " + p + "   " + r);
				permutations.add(p);
			}
			permutations.add(l+r);
			//System.out.println(permutations);
		}
	}
	
	for(String l : rightPartPermu) {
		for(String r : leftPartPermu) {
			for(int i = 0; i < l.length(); i++) {
				//System.out.println(l);
				String p = l.substring(0, i) + r + l.substring(i);
				//System.out.println(l +"   " + p + "   " + r);
				permutations.add(p);
			}
			permutations.add(l+r);
			//System.out.println(permutations);
		}
	}
	}
	return permutations;

	}
	int getCountForWaysToClimb(int numberOfStairs) {
		// Base-Cases
		int numberOfWays = 0;
		if(numberOfStairs == 0)
			return 0;
		else if(numberOfStairs == 1)
			return 1;
		else if(numberOfStairs == 2)
			return 2;
		else if(numberOfStairs == 3)
			return 4;
		else {
			for(int i = 1; i < numberOfStairs; i++) {
				//System.out.println(i);
				int l = getCountForWaysToClimb(i);
				int r = getCountForWaysToClimb(numberOfStairs-i);
				numberOfWays = numberOfWays + l + r;
				//System.out.println(i +  "  " +l + "  "+ r);
			}
			return numberOfWays;
		}
	}
	public int countWays(int n) {
		if (n < 0)
		return 0;
		else if (n == 0)
		return 1;
		else {
		return countWays(n - 1) + countWays(n - 2) +
		countWays(n - 3);
		}
		}
	public static void main(String[] args) {
		Permutations pp = new Permutations();
		/*Set<String> allP = pp.getAllPermuations("vfrasd");
		System.out.println(allP);
		
		for(String s : allP) {
			//System.out.println(allP);
			//System.out.println(s);
		}*/
		System.out.println(pp.getCountForWaysToClimb(3));
		System.out.println(pp.countWays(3));
	}
}
