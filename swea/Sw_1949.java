package algorithm.swea;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Sw_1949 {
    private static BufferedReader br = new BufferedReader( new InputStreamReader( System.in ));
    private static int testCase;
    private static int mapSize;
    private static int drillCount;
    private static int maxLength;
    private static int[][] map;
    private static int[][] copyMap;
    private static boolean[][] visited;
    private static int[] directionX = { 1, 0, -1, 0 };
    private static int[] directionY = { 0, 1, 0, -1 };
    public static void main(String[] args) throws IOException {
        testCase = Integer.parseInt(br.readLine());
        for( int i=0; i<testCase; i++ ){
            StringTokenizer st = new StringTokenizer(br.readLine());
            mapSize = Integer.parseInt(st.nextToken());
            drillCount = Integer.parseInt(st.nextToken());
            maxLength = 0;
            initMap();
            visited = new boolean[mapSize][mapSize];
            doCommand();
            System.out.println( "#" + (i+1) + " " + maxLength );
        }
    }

    public static void initMap() throws IOException{
        map = new int[mapSize][mapSize];
        copyMap = new int[mapSize][mapSize];
        for( int i=0; i<mapSize; i++ ){
            StringTokenizer st = new StringTokenizer(br.readLine());
            for( int j=0; j<mapSize; j++ ){
                map[i][j] = Integer.parseInt(st.nextToken());
                copyMap[i][j] = map[i][j];
            }
        }
    }

    public static void doCommand(){
        int highestLand = findHighestLand();
        for( int i=0; i<mapSize; i++ ){
            for( int j=0; j<mapSize; j++ ){
                if ( map[i][j] == highestLand ) {
                    visited[i][j] = true;
                    dfs( j, i, 1, drillCount, false );
                    visited[i][j] = false;
                }
            }
        }
    }

    public static int findHighestLand(){
        int temp = 0;
        for( int i=0; i<mapSize; i++ ){
            for( int j=0; j<mapSize; j++ ){
                if ( map[i][j] > temp ) {
                    temp = map[i][j];
                }
            }
        }
        return temp;
    }

    public static void dfs( int x, int y, int length, int drillCount, boolean check ){
        maxLength = Math.max( maxLength, length);
        for( int i=0; i<4; i++ ){
            int nextX = x + directionX[i];
            int nextY = y + directionY[i];
            if( !movable( nextX, nextY ) ) continue;
            if( map[nextY][nextX] < map[y][x] && !visited[nextY][nextX] ){
                visited[nextY][nextX] = true;
                dfs( nextX, nextY, length+1, drillCount, check );
                visited[nextY][nextX] = false;
            }else if( map[nextY][nextX] >= map[y][x] && !visited[nextY][nextX] ){
                if( map[nextY][nextX] - map[y][x] < drillCount && ( !check || copyMap[nextY][nextX] != map[nextY][nextX] ) ){
                    for( int j=map[nextY][nextX]-map[y][x]+1; j<=drillCount; j++ ){
                        visited[nextY][nextX] = true;
                        map[nextY][nextX] -= j;
                        dfs( nextX, nextY, length+1, drillCount - j, true );
                        map[nextY][nextX] += j;
                        visited[nextY][nextX] = false;
                    }
                }
            }
        }
    }

    public static boolean movable( int nextX, int nextY ){
        return ( nextX>=0 && nextX<mapSize && nextY>=0 && nextY<mapSize );
    }
}
