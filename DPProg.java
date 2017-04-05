// you can also use imports, for example:
import java.util.*;

// you can write to stdout for debugging purposes, e.g.
// System.out.println("this is a debug message");

class DPProg {
    private static int MAX_INT = 1000002;
    public int solution(int[] A, int X, int D) {
        // write your code in Java SE 8
        // Since we have to optimize the time to cross, lets try the dynamic solution
        // Base case
        if(X < D) {
            return 0;
        }
        
        // Here we create a map with the position as key and value as the
        // earliest second that position is available
        Map<Integer, Integer> earliestTimeForPos = new HashMap<Integer, Integer>();
        for(int i = 0; i < A.length; i++) {
            int position = A[i];
            if(earliestTimeForPos.containsKey(position)) {
                continue;
            } else {
                earliestTimeForPos.put(position, i);
            }
        }

        int time = crossBank(earliestTimeForPos, 0, D, X);
        if(time == MAX_INT) {
            return -1;
        }
        
        return time;
    }
    
    private int checkAndReturnPosition(int position, Map<Integer, Integer> 
        mapForPositions) {
        if(mapForPositions.containsKey(position)) {
            return mapForPositions.get(position);
        }
        return -1;
    }
    
    private int crossBank(Map<Integer, Integer> mapForPositions,
        int currentPosition, int maxHops, int totalDistance) {
            boolean atleastOneHopPossible = false;
            // Since as per spec the this is the upper limit of time
            int minimumTimeToCross = MAX_INT;

            if((totalDistance - currentPosition) <= maxHops) {
                    return mapForPositions.get(currentPosition);
            }
            
            for(int i = 1; i <= maxHops; i++) {
                // TODO: Check the range here
                if(mapForPositions.containsKey(currentPosition+i)) {
                    // Recurse here
                    atleastOneHopPossible = true;
                    int time = crossBank(mapForPositions, currentPosition+i, maxHops, totalDistance);
                    minimumTimeToCross = (minimumTimeToCross > time) ? time : minimumTimeToCross;
                }
            }
            
            if(!atleastOneHopPossible) {
                // Means no hop is possible from this position
                return MAX_INT;
            }
        return minimumTimeToCross;
    }
}