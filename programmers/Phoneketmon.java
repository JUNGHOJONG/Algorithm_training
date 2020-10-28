package algorithm.programmers;

import java.util.HashSet;

public class Phoneketmon {
    public static void main(String[] args) {
        int[] nums = {3, 1, 2, 3}; // output: 2
//        int[] nums = {3, 3, 3, 2, 2, 4}; // output: 3
//        int[] nums = {3, 3, 3, 2, 2, 2}; // output: 2
        Solution_Phoneketmon processCommand = new Solution_Phoneketmon();
        System.out.println(processCommand.solution(nums));
    }
}

class Solution_Phoneketmon{
    public int solution(int[] nums) {
        HashSet<Integer> hashSet = new HashSet<>();
        for(int n : nums){
            hashSet.add(n);
        }
        return Math.min(hashSet.size(), nums.length / 2);
    }
}