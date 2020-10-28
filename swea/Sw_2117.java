package algorithm.swea;

import java.io.*;
import java.util.StringTokenizer;

public class Sw_2117 {

    private static BufferedReader br = new BufferedReader( new InputStreamReader( System.in ));
    private static BufferedWriter bw = new BufferedWriter( new OutputStreamWriter( System.out ));
    private static int testCase;
    private static int citySize;
    private static int homeProfit;
    private static int[][] city;
    private static int serviceLength = 0;
    private static int maxCount = 0;

    public static void main(String[] args) throws IOException {
        testCase = Integer.parseInt(br.readLine());
        for( int i=0; i<testCase; i++ ){
            StringTokenizer st = new StringTokenizer(br.readLine());
            citySize = Integer.parseInt(st.nextToken());
            homeProfit = Integer.parseInt(st.nextToken());
            maxCount = 0;
            initCity();
            getServiceLength();
            for( int j=1; j<=serviceLength; j++ ){
                for( int row=0; row<citySize; row++ ){
                    for( int col=0; col<citySize; col++ ){
                        doCommand( col, row, j );
                    }
                }
            }
            print( i );
        }
        bw.flush();
        bw.close();
    }

    public static void initCity() throws IOException{
        city = new int[citySize][citySize];
        for( int i=0; i<citySize; i++ ){
            StringTokenizer st = new StringTokenizer(br.readLine());
            for( int j=0; j<citySize; j++ ){
                city[i][j] = Integer.parseInt(st.nextToken());
            }
        }
    }

    public static void getServiceLength(){
        int allHomeProfit = getAllHomeProfit();
        int k = 1;
        while( k*k+(k-1)*(k-1) <= allHomeProfit ){
            k++;
        }
        serviceLength = k - 1;
    }

    public static int getAllHomeProfit(){
        int homeCount = 0;
        for( int i=0; i<citySize; i++ ){
            for( int j=0; j<citySize; j++ ){
                if( city[i][j] == 1 ){
                    homeCount++;
                }
            }
        }
        return ( homeCount * homeProfit );
    }

    public static void doCommand( int x, int y, int serviceLength ){
        int yLength = 0;
        int homeCount = 0;
        for( int i=x-(serviceLength-1); i<=x+(serviceLength-1); i++ ){
            if( x > i ){
                for( int j=y-yLength; j<=y+yLength; j++ ){
                    if( movable( i , j ) && city[j][i] == 1 ){
                        homeCount++;
                    }
                }
                yLength++;
            }else{
                for( int j=y-yLength; j<=y+yLength; j++ ){
                    if( movable( i , j ) && city[j][i] == 1 ){
                        homeCount++;
                    }
                }
                yLength--;
            }
        }
        int serviceArea = serviceLength * serviceLength + (serviceLength-1) * (serviceLength-1);
        if( homeCount * homeProfit - serviceArea >= 0){
            maxCount = Math.max( maxCount, homeCount );
        }
    }

    public static boolean movable( int x, int y ){
        return ( x>=0 && x<citySize && y>=0 && y<citySize );
    }


    public static void print( int number ) throws IOException{
        bw.write( "#" + (number+1) + " " + maxCount + "\n" );
    }
}
