package algorithm.backjoon;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Backjoon16932_again {
    public static class Movement{
        private int positionX;
        private int positionY;
        public Movement( int positionX, int positionY ){
            this.positionX = positionX;
            this.positionY = positionY;
        }
    }
    private static BufferedReader br = new BufferedReader( new InputStreamReader( System.in ));
    private static int rowSize;
    private static int colSize;
    private static int[][] map;
    private static int[] directionX = { 1, 0, -1, 0 };
    private static int[] directionY = { 0, 1, 0, -1 };
    private static int maxShape = 0;
    private static int index = 1;
    private static HashMap<Integer, Integer> hashMap = new HashMap<>();
    private static boolean[] visited;
    public static void main(String[] args) throws IOException {
        StringTokenizer st = new StringTokenizer(br.readLine());
        rowSize = Integer.parseInt(st.nextToken());
        colSize = Integer.parseInt(st.nextToken());
        initMap();
        numberingMap();
        visited = new boolean[index]; // 0-5
        dfs( 0, 0 );
        System.out.println(maxShape);
    }

    public static void initMap() throws IOException{
        map = new int[rowSize][colSize];
        for( int i=0; i<rowSize; i++ ){
            StringTokenizer st = new StringTokenizer(br.readLine());
            for( int j=0; j<colSize; j++ ){
                map[i][j] = Integer.parseInt(st.nextToken());
            }
        }
    }

    public static void numberingMap(){
        boolean[][] visited = new boolean[rowSize][colSize];
        Queue<Movement> queue = new LinkedList<>();
        for( int i=0; i<rowSize; i++ ){
            for( int j=0; j<colSize; j++ ){
                if( map[i][j] != 0 && !visited[i][j] ){
                    map[i][j] = index;
                    int count = 1;
                    visited[i][j] = true;
                    queue.add( new Movement( j, i ));
                    while(!queue.isEmpty()){
                        Movement temp = queue.poll();
                        for( int k=0; k<4; k++ ){
                            int nextX = temp.positionX + directionX[k];
                            int nextY = temp.positionY + directionY[k];
                            if( movable( nextX, nextY ) && !visited[nextY][nextX] && map[nextY][nextX] == 1 ){
                                visited[nextY][nextX] = true;
                                queue.add( new Movement( nextX, nextY ));
                                map[nextY][nextX] = index;
                                count++;
                            }
                        }
                    }
                    hashMap.put( index, count );
                    index++;
                }
            }
        }
    }

    public static void dfs( int k, int count ){
        if( count == 1 ){
            int output = getShape( k );
            maxShape = Math.max( maxShape, output );
            return;
        }
        for( int i=k; i<rowSize*colSize; i++ ){
            int x = i % colSize;
            int y = i / colSize;
            if( map[y][x] == 0 ){
                map[y][x] = 1;
                dfs( i, count+1);
                map[y][x] = 0;
            }
        }
    }

    public static int getShape( int k ) {
        int x = k % colSize;
        int y = k / colSize;
        int count = 1;
        for (int i = 0; i < 4; i++) {
            int nextX = x + directionX[i];
            int nextY = y + directionY[i];
            if( !movable(nextX, nextY) ) continue;
            int tempIndex = map[nextY][nextX];
            if ( !visited[tempIndex] && map[nextY][nextX] != 0 ) {
                visited[tempIndex] = true;
                count += hashMap.get(tempIndex);
            }
        }
        for( int i=0; i<index; i++ ){
            visited[i] = false;
        }
        return count;
    }

    public static boolean movable( int nextX, int nextY ){
        return ( nextX>=0 && nextX<colSize && nextY>=0 && nextY<rowSize );
    }

}
