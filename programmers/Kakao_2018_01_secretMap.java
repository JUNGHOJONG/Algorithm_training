package algorithm.programmers;

import java.util.Arrays;

public class Kakao_2018_01_secretMap {
    public static void main(String[] args) {
        int n = 6;
        int[] arr1 = { 46, 33, 33 ,22, 31, 50 };
        int[] arr2 = { 27 ,56, 19, 14, 14, 10 };
        Solution_secretMap processSolution = new Solution_secretMap();
        System.out.println(Arrays.toString(processSolution.solution(n, arr1, arr2)));
    }
}

class Solution_secretMap{
    public String[] solution(int n, int[] arr1, int[] arr2) {
        String[] output = new String[n];
        for( int i=0; i<n; i++ ){
            output[i] = Integer.toBinaryString( arr1[i] | arr2[i] );
            output[i] = output[i].replaceAll( "0", " " );
            output[i] = output[i].replaceAll( "1", "#" );
            while( output[i].length() != n ){
                output[i] = " " + output[i];
            }
        }
        return output;
    }
}
