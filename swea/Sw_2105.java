package algorithm.swea;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Sw_2105 {
    private static BufferedReader br = new BufferedReader( new InputStreamReader( System.in ));
    private static int testCase;
    private static int mapSize;
    private static int[][] caffe;
    private static boolean[] check;
    private static int maxValue;
    private static int[] directionX = { 1, 1, -1, -1 };
    private static int[] directionY = { -1, 1, 1, -1 };
    public static void main(String[] args) throws IOException {
        testCase = Integer.parseInt(br.readLine());
        for( int i=0; i<testCase; i++ ){
            mapSize = Integer.parseInt(br.readLine());
            maxValue = -1;
            check = new boolean[101];
            initCaffe();
            doCommand();
            System.out.println( "#" + (i+1) + " " + maxValue );
        }

    }

    public static void initCaffe() throws IOException{
        caffe = new int[mapSize][mapSize];
        for( int i=0; i<mapSize; i++ ){
            StringTokenizer st = new StringTokenizer(br.readLine());
            for( int j=0;j<mapSize; j++ ){
                caffe[i][j] = Integer.parseInt(st.nextToken());
            }
        }
    }

    public static void doCommand(){
        for( int i=0; i<mapSize; i++ ){
            for( int j=0;j<mapSize; j++ ){
                    dfs( j, i, j, i, 0, 0, 0 );
            }
        }
    }

    public static void dfs( int startX, int startY, int x, int y, int count, int direction, int rotationChange ){
        if( startX == x && startY == y && rotationChange == 3 ){
            maxValue = Math.max( maxValue, count );
            return;
        }else if( rotationChange >= 4 ){
            return;
        }
        for( int i=0; i<2; i++ ){
            direction = ( direction + i ) % 4;
            int nextX = x + directionX[direction];
            int nextY = y + directionY[direction];
            if( !movable( nextX, nextY ) || check[caffe[nextY][nextX]] ) continue;
            if( i==1 ) rotationChange += 1;
            check[caffe[nextY][nextX]] = true; // dessert = caffe[nextY][nextX]
            dfs( startX, startY, nextX, nextY, count+1, direction, rotationChange );
            check[caffe[nextY][nextX]] = false;
        }
    }

    public static boolean movable( int nextX, int nextY ){
        return ( nextX>=0 && nextX<mapSize && nextY>=0 && nextY<mapSize );
    }
}
