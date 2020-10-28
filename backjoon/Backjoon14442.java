package algorithm.backjoon;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Backjoon14442 {
    public static class Movement{
        private int positionX;
        private int positionY;
        private int depth;
        private int brokenCount;
        public Movement( int x, int y, int depth, int brokenCount ){
            this.positionX = x;
            this.positionY = y;
            this.depth = depth;
            this.brokenCount = brokenCount;
        }
    }

    private static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    private static int[][] map;
    private static boolean[][][] visited;
    private static int rowSize;
    private static int colSize;
    private static int breakableCount;
    private static int minValue = Integer.MAX_VALUE;
    private static boolean check = false;
    private static int[] directionX = { 1, 0, -1, 0 };
    private static int[] directionY = { 0, 1, 0, -1 };
    public static void main(String[] args) throws IOException {
        StringTokenizer st = new StringTokenizer( br.readLine() );
        rowSize = Integer.parseInt( st.nextToken() );
        colSize = Integer.parseInt( st.nextToken() );
        breakableCount = Integer.parseInt( st.nextToken() );
        initMap();
        bfs();
        print();
    }

    public static void initMap() throws IOException{
        map = new int[rowSize][colSize];
        for( int i=0; i<rowSize; i++ ){
            String temp = br.readLine();
            for( int j=0; j<colSize; j++ ){
                map[i][j] = temp.charAt( j ) - '0';
            }
        }
    }

    public static void bfs(){
        visited = new boolean[rowSize][colSize][breakableCount+1];
        Queue<Movement> queue = new LinkedList<>();
        visited[0][0][0] = true;
        queue.add( new Movement( 0, 0, 0, 0 ) );
        while( !queue.isEmpty() ){
            Movement temp =  queue.poll();
            int currentX = temp.positionX;
            int currentY = temp.positionY;
            int currentDepth = temp.depth;
            int currentBrokenCount = temp.brokenCount;
            if( currentX == colSize-1 && currentY == rowSize-1 ){
                check = true;
                minValue = Math.min( minValue, currentDepth );
            }
            for( int i=0; i<4; i++ ){
                int nextX = currentX + directionX[i];
                int nextY = currentY + directionY[i];
                if( !movable( nextX, nextY ) ) continue;
                if( map[nextY][nextX] == 1 ){
                    if( currentBrokenCount < breakableCount
                            && !visited[nextY][nextX][currentBrokenCount+1]){
                        visited[nextY][nextX][currentBrokenCount+1] = true;
                        queue.add( new Movement( nextX, nextY, currentDepth+1, currentBrokenCount+1) );
                    }
                }else{
                    if( !visited[nextY][nextX][currentBrokenCount] ){
                        visited[nextY][nextX][currentBrokenCount] = true;
                        queue.add( new Movement( nextX, nextY, currentDepth+1, currentBrokenCount) );
                    }
                }
            }

        }
    }

    public static boolean movable( int nextX, int nextY ){
        return ( nextX>=0 && nextX<colSize && nextY>=0 && nextY<rowSize );
    }

    public static void print(){
        System.out.println( check ? minValue+1 : -1 );
    }
}
