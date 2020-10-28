package algorithm.backjoon;

import java.io.*;

public class Backjoon9251 {

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
        System.out.println(output);
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
}
