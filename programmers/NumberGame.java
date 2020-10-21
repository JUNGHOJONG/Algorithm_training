package algorithm.programmers;

import java.util.Arrays;

public class NumberGame {
    public static void main(String[] args) {
        int[] A = {5, 1, 3, 7};
        int[] B = {2, 2, 6, 8};
        Solution_NumberGame processCommand = new Solution_NumberGame();
        System.out.println(processCommand.solution(A, B));
    }
}

class Solution_NumberGame{
    public int solution(int[] A, int[] B){
        Arrays.sort(A);
        Arrays.sort(B);
        int count = 0;
        int leftIndex = B.length - 1;
        for(int rightIndex = A.length - 1; rightIndex >= 0; rightIndex--){
            if(B[leftIndex] <= A[rightIndex]) continue;
            count++;
            leftIndex--;
        }
        return count;
    }
}