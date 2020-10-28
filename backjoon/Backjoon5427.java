package algorithm.backjoon;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Backjoon5427 {
    public static class Movement{
        private int positionX;
        private int positionY;
        private int depth;
        public Movement( int positionX, int positionY, int depth ){
            this.positionX = positionX;
            this.positionY = positionY;
            this.depth = depth;
        }
    }
    private static BufferedReader br = new BufferedReader( new InputStreamReader( System.in ) );
    private static Queue<Movement> fireManagement = new LinkedList<>();
    private static Queue<Movement> personManagement = new LinkedList<>();
    private static int testCase;
    private static int minValue;
    private static char[][] map;
    private static int[][] fire;
    private static boolean[][] visited;
    private static int[] directionX = { 1, 0, -1, 0 };
    private static int[] directionY = { 0, 1, 0, -1 };
    public static void main(String[] args) throws IOException {
        testCase = Integer.parseInt(br.readLine());
        for( int i=0; i<testCase; i++ ){
            StringTokenizer st = new StringTokenizer( br.readLine() );
            int colSize = Integer.parseInt( st.nextToken() );
            int rowSize = Integer.parseInt( st.nextToken() );
            minValue = Integer.MAX_VALUE;
            initMap( rowSize, colSize );
            initFireSpread( rowSize, colSize );
            if( doEscape( rowSize, colSize ) ){
                System.out.println( minValue + 1 );
            }else{
                System.out.println( "IMPOSSIBLE" );
            }
        }
    }

    public static void initMap( int row, int col ) throws IOException{
        map = new char[row][col];
        visited = new boolean[row][col];
        for (int i = 0; i < row; i++) {
            String temp = br.readLine();
            for (int j = 0; j < col; j++) {
                map[i][j] = temp.charAt(j);
                if( map[i][j] == '*' ) {
                    fireManagement.add( new Movement( j, i, 0 ) );
                    visited[i][j] = true;
                }else if( map[i][j] == '@' ){
                    personManagement.add( new Movement( j, i, 0 ) );
                }
            }
        }
    }

    public static void initFireSpread( int row, int col ){
        fire = new int[row][col];
        while( !fireManagement.isEmpty() ){
            Movement temp = fireManagement.poll();
            int currentX = temp.positionX;
            int currentY = temp.positionY;
            int currentDepth = temp.depth;
            for( int i=0; i<4; i++ ){
                int nextX = currentX + directionX[i];
                int nextY = currentY + directionY[i];
                if( movable( nextX, nextY, row, col ) && !visited[nextY][nextX]
                        && map[nextY][nextX] != '#' ){
                    visited[nextY][nextX] = true;
                    fire[nextY][nextX] = currentDepth + 1;
                    fireManagement.add( new Movement( nextX, nextY, currentDepth + 1 ) );
                }
            }
        }
    }

    public static boolean movable( int nextX, int nextY, int row, int col ){
        return ( nextX >=0 && nextX <col && nextY >= 0 && nextY < row );
    }

    public static boolean doEscape( int row, int col ){
        boolean check = false;
        visited = new boolean[row][col];
        visited[personManagement.peek().positionY][personManagement.peek().positionX] = true;
        while( !personManagement.isEmpty() ){
            Movement temp = personManagement.poll();
            int currentX = temp.positionX;
            int currentY = temp.positionY;
            int currentDepth = temp.depth;
            if( currentX == 0 || currentX == col-1 || currentY == 0 || currentY == row-1 ){
                check = true;
                minValue = Math.min( minValue, currentDepth );
            }
            for( int i=0; i<4; i++ ){
                int nextX = currentX + directionX[i];
                int nextY = currentY + directionY[i];
                if( movable( nextX, nextY, row, col ) && !visited[nextY][nextX]
                        && map[nextY][nextX] != '#' && map[nextY][nextX] != '*'
                        && ( fire[nextY][nextX] == 0
                        || ( fire[nextY][nextX] > 0 && fire[nextY][nextX] > currentDepth + 1 ) ) ) {
                    visited[nextY][nextX] = true;
                    personManagement.add( new Movement( nextX, nextY, currentDepth + 1 ) );
                }
            }
        }
        return check;
    }

}
