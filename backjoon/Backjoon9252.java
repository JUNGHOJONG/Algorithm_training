package algorithm.backjoon;

import java.io.*;

public class Backjoon9252 {

    private static BufferedReader br = new BufferedReader( new InputStreamReader( System.in ));
    private static BufferedWriter bw = new BufferedWriter( new OutputStreamWriter( System.out ));
    private static String m;
    private static String n;
    private static int[][] sum;

    public static void main(String[] args) throws IOException {
        m = br.readLine();
        n = br.readLine();
        sum = new int[m.length()+1][n.length()+1];

        int output = dynamicProgramming();
        String subsequence = getLongestCommonSubsequence( output );
        if( output > 0 ){
            bw.write( output + "\n" );
            bw.write( subsequence + "\n" );
        }else{
            bw.write( output + "\n" );
        }
        bw.flush();
        bw.close();
    }

    public static int dynamicProgramming(){
        int mLength = m.length();
        int nLength = n.length();
        for( int i=1; i<=mLength; i++ ){
            for( int j=1; j<=nLength; j++ ){
                if( m.charAt(i-1) == n.charAt(j-1) ){
                    sum[i][j] = sum[i-1][j-1] + 1;
                }else{
                    sum[i][j] = Math.max( sum[i-1][j], sum[i][j-1] );
                }
            }
        }
        return sum[mLength][nLength];
    }

    public static String getLongestCommonSubsequence( int length ){
        int tempLength = length;
        int mLength = m.length();
        int nLength = n.length();
        StringBuilder sb = new StringBuilder();
        for( int i=mLength; i>=1; i-- ){
            for( int j=nLength; j>=1; j-- ){
                if( sum[i][j] == tempLength && m.charAt(i-1) == n.charAt(j-1) ){
                    sb.append( m.charAt(i-1) );
                    tempLength--;
                    break;
                }
            }
        }
        sb.reverse();
        return sb.toString();
    }
}
