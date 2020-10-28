package algorithm.backjoon;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigInteger;

public class Backjoon1309 {
    private static BufferedReader br = new BufferedReader( new InputStreamReader( System.in ) );
    private static int zooRowSize;
    public static void main(String[] args) throws IOException {
        zooRowSize = Integer.parseInt( br.readLine() );
        BigInteger output = dynamicProgramming();
        System.out.println( output );
    }

    public static BigInteger dynamicProgramming(){
        BigInteger[][] zoo = new BigInteger[zooRowSize+1][3];
        zoo[1][0] = zoo[1][1] = zoo[1][2] = BigInteger.ONE;
//        zoo[1][0] = BigInteger.ONE;
        for( int i=2; i<=zooRowSize; i++ ){
            zoo[i][0] = zoo[i-1][0].add( zoo[i-1][1] ).add( zoo[i-1][2] );
            // zoo[i-1][0] + zoo[i-1][1] + zoo[i-1][2]
            zoo[i][1] = zoo[i-1][0].add( zoo[i-1][2] );
            // zoo[i-1][0] + zoo[i-1][2]
            zoo[i][2] = zoo[i-1][0].add( zoo[i-1][1] );
            // zoo[i-1][0] + zoo[i-1][1]
        }
        return zoo[zooRowSize][0].add( zoo[zooRowSize][1] ).add( zoo[zooRowSize][2] );
    }
}
