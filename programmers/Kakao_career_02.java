package algorithm.programmers;

import java.util.Arrays;

public class Kakao_career_02 {
    public static void main(String[] args) {
//        int n = 7;
//        int[] timeStamp = {2, 2, 4, 8, 11, 28, 30};
//        int[] top = {0, 5, 5, 15}; // output: 5
//        int n = 17;
//        int[] timeStamp = {0, 0, 1, 1, 1, 1, 1 ,1, 1, 1, 1, 1, 1, 1, 1, 1, 1};
//        int[] top = {6, 6, 6, 6}; // output: 17
        int n = 10;
        int[] timeStamp = {1, 2, 2, 3, 4, 5, 6, 6, 13, 16};
        int[] top = {10, 15}; // output: 9
        Solution_Kakao_Career_02 processCommand = new Solution_Kakao_Career_02();
        System.out.println(processCommand.solution(n, timeStamp, top));
    }
}

class Solution_Kakao_Career_02{
    public int solution(int n, int[] timeStamp, int[] top){
        Arrays.sort(timeStamp);
        Arrays.sort(top);
        int totalCount = 0;
        for(Integer topValue : top){ // 30
            for(int i=timeStamp.length-1; i>=0; i--){ // 100000
                int count = 0;
                if(topValue >= timeStamp[i] && timeStamp[i] != -1){
                    timeStamp[i] = -1;
                    totalCount++;
                    count++;
                }
                if(count >= 5) break;
            }
        }
        return totalCount;
    }
}
