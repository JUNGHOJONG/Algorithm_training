package algorithm.swea;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Swea_5656 {
    public static class Bead{
        private int positionX;
        private int positionY;
        private int distance;
        public Bead( int positionX, int positionY, int distance ){
            this.positionX = positionX;
            this.positionY = positionY;
            this.distance = distance;
        }
    }
    private static BufferedReader br = new BufferedReader( new InputStreamReader( System.in ));
    private static Queue<Bead> queue;
    private static int testCase;
    private static int dropCount;
    private static int brickCol;
    private static int brickRow;
    private static int minBrick;
    private static int[][] brick;
    private static boolean[][] visited;
    private static int[] directionX = { 1, 0, -1, 0 };
    private static int[] directionY = { 0, 1, 0, -1 };

    public static void main(String[] args) throws IOException {
        testCase = Integer.parseInt(br.readLine());
        for( int i=0; i<testCase; i++ ){
            StringTokenizer st = new StringTokenizer(br.readLine());
            queue = new LinkedList<>();
            dropCount = Integer.parseInt(st.nextToken());
            brickCol = Integer.parseInt(st.nextToken());
            brickRow = Integer.parseInt(st.nextToken());
            initBrick();
            minBrick = Integer.MAX_VALUE;
            dfs( 0 );
            System.out.println( "#" + (i+1) + " " + minBrick );
        }
    }

    public static void initBrick() throws IOException{
        brick = new int[brickRow][brickCol];
        visited = new boolean[brickRow][brickCol];
        for( int i=0; i<brickRow; i++ ){
            StringTokenizer st = new StringTokenizer(br.readLine());
            for( int j=0 ;j<brickCol; j++ ){
                brick[i][j] = Integer.parseInt(st.nextToken());
            }
        }
    }

    public static void dfs( int count ){
        if( count == dropCount ){
            int output = countBrick();
            minBrick = Math.min( output, minBrick );
            return;
        }
        for( int i=0; i<brickCol; i++ ){
            int[][] copyBrick = copyBrick();
            visited = new boolean[brickRow][brickCol];
            bfs( i );
            terminateBrick();
            gravityBrick();
            dfs( count + 1 );
            rollBackBrick( copyBrick );
        }
    }

    public static int[][] copyBrick(){
        int[][] copyBrick = new int[brickRow][brickCol];
        for( int i=0; i<brickRow; i++ ){
            for( int j=0 ;j<brickCol; j++ ){
                copyBrick[i][j] = brick[i][j];
            }
        }
        return copyBrick;
    }

    public static void bfs( int x ){
        int y = 0;
        while( movable( x, y ) && brick[y][x] == 0 ){
            y++;
        }
        if( y == brickRow ) return;
        visited[y][x] = true;
        queue.add( new Bead( x, y, brick[y][x] ) );
        while(!queue.isEmpty()){
            Bead temp = queue.poll();
            int currentX = temp.positionX;
            int currentY = temp.positionY;
            int currentDistance = temp.distance;
            for( int i=0; i<4; i++ ){
                int nextX = currentX;
                int nextY = currentY;
                for( int j=0; j<currentDistance-1; j++ ){
                    nextX += directionX[i];
                    nextY += directionY[i];
                    if( movable( nextX, nextY ) && !visited[nextY][nextX] && brick[nextY][nextX] > 0 ){
                        visited[nextY][nextX] = true;
                        queue.add( new Bead( nextX, nextY, brick[nextY][nextX] ));
                    }
                }
            }
        }
    }

    public static boolean movable( int nextX, int nextY ){
        return ( nextX>=0 && nextX<brickCol && nextY>=0 && nextY<brickRow );
    }

    public static void terminateBrick(){
        for( int i=0; i<brickRow; i++ ){
            for( int j=0 ;j<brickCol; j++ ){
                if ( visited[i][j] && brick[i][j] > 0 ) {
                    brick[i][j] = 0;
                }
            }
        }
    }

    public static void gravityBrick(){
        for (int i = brickRow-1; i >= 0; i--) {
            for (int j = brickCol-1; j >= 0; j--) {
                if ( brick[i][j] > 0 ) {
                    int temp = brick[i][j];
                    brick[i][j] = 0;
                    int y = i + 1;
                    while ( movable( j, y ) && brick[y][j] == 0 ) {
                        y++;
                    }
                    y--;
                    brick[y][j] = temp;
                }
            }
        }
    }

    public static void rollBackBrick( int[][] copyBrick ){
        for( int i=0; i<brickRow; i++ ){
            for( int j=0 ;j<brickCol; j++ ){
                brick[i][j] = copyBrick[i][j];
            }
        }
    }

    public static int countBrick(){
        int count = 0;
        for( int i=0; i<brickRow; i++ ){
            for( int j=0 ;j<brickCol; j++ ){
                if( brick[i][j] > 0 ) count++;
            }
        }
        return count;
    }
}
