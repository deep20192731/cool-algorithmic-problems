import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class ActivitySelection {

	class Activity {
		int startTime;
		int endTime;
		
		public Activity(int s, int e) {
			this.startTime = s;
			this.endTime = e;
		}
	}

	public int greedyApproach(int[] startTimes, int[] endTimes) {
		List<Activity> allActivities = new ArrayList<Activity>();
		// Here we are just finding the max subset, we can modify 
		// this to get all activities
		int maxSubset = 0;
		
		if((startTimes.length == endTimes.length) && 
				startTimes.length != 0) {
			for(int i=0; i< startTimes.length; i++) {
				Activity a = new Activity(startTimes[i], endTimes[i]);
				allActivities.add(a);
			}
			
			// Sort on end time. Using a diff structure we can track start time too.
			allActivities.sort(new Comparator<Activity>() {
				@Override
				public int compare(Activity arg0, Activity arg1) {
					// Actually we should have sorted these in reverse order
					// so that it was easy to remove.
					return arg0.endTime - arg1.endTime;
				}
			});
			
			while(allActivities.size() > 0) {
				// Pick from the set with earliest finish time
				Activity firstAct = allActivities.remove(0);
				maxSubset++;
				
				// Now remove all conflicts
				List<Activity> toBeRemovedActivities = new 
						ArrayList<Activity>();
				// Because of this the algorithm is O(n^2) but with careful
				// tuning we can reduce this to nlogn(sorting) + n
				// By adversary argument too the process of selecting after
				// 
				for(Activity a : allActivities) {
					if(firstAct.endTime > a.startTime) {
						toBeRemovedActivities.add(a);
					}
				}
				allActivities.removeAll(toBeRemovedActivities);
			}
			return maxSubset;
		}
		return -1;
	}
	public static void main(String[] args) {
		int startTimes[] =  {1, 3, 0, 5, 8, 5, 10};
	    int endTimes[] =  {2, 4, 6, 7, 9, 9, 89};
	    
	    ActivitySelection sel = new ActivitySelection();
	    System.out.println(sel.greedyApproach(startTimes, endTimes));
	}
}
