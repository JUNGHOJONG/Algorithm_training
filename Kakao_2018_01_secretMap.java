package algorithm;

public class Kakao_2018_01_secretMap {
    public static void main(String[] args) {
        int n = 6;
        int[] arr1 = { 46, 33, 33 ,22, 31, 50 };
        int[] arr2 = { 27 ,56, 19, 14, 14, 10 };
        String[] output = new String[n];
        for( int i=0; i<n; i++ ){
            output[i] = Integer.toBinaryString( arr1[i] | arr2[i] );
            output[i] = output[i].replaceAll( "0", " " );
            output[i] = output[i].replaceAll( "1", "#" );
            while( output[i].length() != n ){
                output[i] = " " + output[i];
            }
        }
        System.out.println( output );
    }
}
