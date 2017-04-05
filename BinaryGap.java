import java.util.*;

// Codility
class BainryGap {
    public int solution(int N) {
        List<Integer> binary = binaryRep(N);
        int binaryGap = 0;
        int tempBinaryGap = 0;
        boolean startCounting = false;
        for(int i = 0; i < binary.size(); i++) {
            if(startCounting) {
                if(binary.get(i).equals(0)) {
                    tempBinaryGap++;
                    continue;
                } else {
                    startCounting = false;
                    binaryGap = ((binaryGap > tempBinaryGap) ? binaryGap : tempBinaryGap);
                }
            }
            if (i == (binary.size()-1)) {
                continue;
            }
            if(binary.get(i).equals(1) && binary.get(i+1).equals(0)) {
                startCounting = true;
                tempBinaryGap = 0;
            }
        }
        return binaryGap;
        
    }
    
    private List<Integer> binaryRep(int N) {
        List<Integer> binary = new ArrayList<Integer>();
        int quotient = N;
        while(quotient != 0) {
            binary.add(quotient%2);
            quotient = quotient/2;
        }
        return binary;
    }
}