package algorithm;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Backjoon2294 {
    private static BufferedReader br = new BufferedReader( new InputStreamReader( System.in ) );
    private static int kindOfCoin;
    private static int totalValue;
    private static int[] coin;
    private static int[] sum;
    public static void main(String[] args) throws IOException {
        StringTokenizer st = new StringTokenizer( br.readLine() );
        kindOfCoin = Integer.parseInt( st.nextToken() );
        totalValue = Integer.parseInt( st.nextToken() );
        initCoin();
        sum = new int[totalValue+1];
        dynamicProgramming();
        if( sum[totalValue] == 0 ){
            System.out.println( -1 );
        }else{
            System.out.println( sum[totalValue] );
        }
    }

    public static void initCoin() throws IOException{
        coin = new int[kindOfCoin+1];
        for( int i=1; i<=kindOfCoin; i++ ){
            coin[i] = Integer.parseInt( br.readLine() );
        }
    }

    public static void dynamicProgramming(){
        for( int i=1; i<=totalValue; i++ ){
            boolean check = false;
            for( int j=1; j<=kindOfCoin; j++ ){
                if( i == coin[j] ){
                    sum[i] = 1;
                }else if( i > coin[j] && sum[i - coin[j]] != 0 ){
                    if( !check ) {
                        sum[i] = sum[i - coin[j]] + 1;
                    }else{
                        sum[i] = Math.min(sum[i], sum[i - coin[j]] + 1);
                    }
                    check = true;
                }
            }
        }
    }
}
