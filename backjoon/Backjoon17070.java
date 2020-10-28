package algorithm.backjoon;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Backjoon17070 {
    private static BufferedReader br = new BufferedReader( new InputStreamReader( System.in ));
    private static int homeSize;
    private static int[][] home;
    private static int maxCount = 0;
    private static int[] directionX = { 0, 1, 0, 1 };
    private static int[] directionY = { 0, 0, 1, 1 };
    public static void main(String[] args) throws IOException {
        homeSize = Integer.parseInt(br.readLine());
        initHome();
        dfs( 1, 0, 1 );
        System.out.println( maxCount );
    }

    public static void initHome()throws IOException{
        home = new int[homeSize][homeSize];
        for( int i=0; i<homeSize; i++ ){
            StringTokenizer st = new StringTokenizer(br.readLine());
            for( int j=0; j<homeSize; j++ ){
                home[i][j] = Integer.parseInt(st.nextToken());
            }
        }
    }

    public static void dfs( int x, int y, int pipeMode ){
        if( movable( x, y, pipeMode ) && x == homeSize-1 && y == homeSize-1 ){
            maxCount++;
            return;
        }
        for( int i=1; i<=3; i++ ){
            if( pipeMode == 1 && i == 2 ) continue;
            else if( pipeMode == 2 && i == 1 ) continue;
            int nextX = x + directionX[i];
            int nextY = y + directionY[i];
            if( movable( nextX, nextY, i ) ){
                dfs( nextX, nextY, i );
            }
        }
    }

    public static boolean movable( int nextX, int nextY, int index ){
        if( index == 3 ){
            return ( nextX >= 0 && nextX < homeSize && nextY >= 0 && nextY < homeSize
                    && home[nextY][nextX] != 1 && home[nextY][nextX-1] != 1 && home[nextY-1][nextX] != 1 );
        }else{
            return ( nextX >= 0 && nextX < homeSize && nextY >= 0 && nextY < homeSize && home[nextY][nextX] != 1 );
        }
    }
}
