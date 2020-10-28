package algorithm.swea;

import java.io.*;
import java.util.StringTokenizer;

public class Sw_2115 {

    private static BufferedReader br = new BufferedReader( new InputStreamReader( System.in ));
    private static BufferedWriter bw = new BufferedWriter( new OutputStreamWriter( System.out ));
    private static int testCase;
    private static int beehiveSize;
    private static int selectSize;
    private static int maxSelectedHoney;
    private static int[][] hive;
    private static int[][] visit;
    private static int[] worker;
    private static boolean[] workerVisit;
    private static int maxProfit = 0;
    private static int maxTotalProfit = 0;

    public static void main(String[] args) throws IOException {
        testCase = Integer.parseInt(br.readLine());
        for( int i=0; i<testCase; i++ ){
            StringTokenizer st = new StringTokenizer(br.readLine());
            beehiveSize = Integer.parseInt(st.nextToken());
            selectSize = Integer.parseInt(st.nextToken());
            maxSelectedHoney = Integer.parseInt(st.nextToken());
            visit = new int[beehiveSize][beehiveSize];
            maxTotalProfit = 0;
            initHive();
            collectHoneyWorker1();
            bw.write( "#"+ (i+1) + " " + maxTotalProfit + "\n" );
        }
        bw.flush();
        bw.close();
    }

    public static void initHive() throws IOException{
        hive = new int[beehiveSize][beehiveSize];
        for( int i=0; i<beehiveSize; i++ ){
            StringTokenizer st = new StringTokenizer(br.readLine());
            for( int j=0; j<beehiveSize; j++ ){
                hive[i][j] = Integer.parseInt(st.nextToken());
            }
        }
    }

    public static void collectHoneyWorker1(){
        worker = new int[selectSize];
        workerVisit = new boolean[selectSize];
        for( int i=0; i<beehiveSize; i++ ){
            for( int j=0; j<=beehiveSize-selectSize; j++ ){
                checkHoney( j, i, 1 );
                for( int k=1; k<=selectSize; k++ ){
                    combination( 0, 0, k );
                }
                int worker1Profit = maxProfit;
                maxProfit = 0;
                collectHoneyWorker2( j, i, 2, worker1Profit );
                checkHoney( j, i, 0 );
            }
        }
    }

    public static void checkHoney( int x, int y, int value ){
        for( int i=x; i<x+selectSize; i++ ){
            visit[y][i] = value;
            worker[i-x] = hive[y][i];
        }
    }

    public static void collectHoneyWorker2( int x, int y, int value, int worker1Profit ){
        for( int i=y; i<beehiveSize; i++ ){
            for( int j=x; j<=beehiveSize-selectSize; j++ ){
                if( visit[i][j] != 1 ){
                    checkHoney( j, i, value );
                    for( int k=1; k<=selectSize; k++ ){
                        combination( 0, 0, k );
                    }
                    checkHoney( j, i, 0 );
                    int worker2Profit = maxProfit;
                    maxProfit = 0;
                    maxTotalProfit = Math.max( maxTotalProfit, worker1Profit + worker2Profit );
                }
            }
            x = 0;
        }
    }

    public static void combination( int k, int index, int number ){
        if( index == number ){
            if( maxHoney() ){
                int output = getProfit();
                maxProfit = Math.max( maxProfit, output );
            }
            return;
        }
        for( int i=k; i<selectSize; i++ ){
            workerVisit[i] = true;
            combination( i+1, index+1, number );
            workerVisit[i] = false;
        }
    }

    public static boolean maxHoney(){
        int temp = 0;
        for( int i=0; i<selectSize; i++ ){
            if( workerVisit[i] ){
                temp += worker[i];
            }
        }
        if( temp > maxSelectedHoney ) return false;
        else return true;
    }

    public static int getProfit(){
        int temp = 0;
        for( int i=0; i<selectSize; i++ ){
            if( workerVisit[i] ){
                temp += Math.pow( worker[i], 2 );
            }
        }
        return temp;
    }

}
