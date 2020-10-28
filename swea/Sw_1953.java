package algorithm.swea;

import java.io.*;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Sw_1953 {

    public static class escapedPrisoner{
        private int positionX;
        private int positionY;
        private int count;
        public escapedPrisoner( int positionX, int positionY, int count ){
            this.positionX = positionX;
            this.positionY = positionY;
            this.count = count;
        }
    }

    private static BufferedReader br = new BufferedReader( new InputStreamReader( System.in ));
    private static BufferedWriter bw = new BufferedWriter( new OutputStreamWriter( System.out ));
    private static Queue<escapedPrisoner> queue = new LinkedList<>();
    private static int testCase;
    private static int tunnelRow;
    private static int tunnelCol;
    private static int escapeTime;
    private static int[][] tunnel;
    private static boolean[][] visited;
    private static int[][] directionX = {
            {0, 0, 0, 0},
            {1, 0, -1, 0},
            {0, 0, 0, 0},
            {1, 0, -1, 0},
            {1, 0, 0, 0},
            {1, 0, 0, 0},
            {0, 0, -1, 0},
            {0, 0, -1, 0}
    };
    private static int[][] directionY = {
            {0, 0, 0, 0},
            {0, 1, 0, -1},
            {0, 1, 0, -1},
            {0, 0, 0, 0},
            {0, 0, 0, -1},
            {0, 1, 0, 0},
            {0, 1, 0, 0},
            {0, 0, 0, -1}
    };

    public static void main(String[] args) throws IOException {
        testCase = Integer.parseInt(br.readLine());
        for( int i=0; i<testCase; i++ ){
        StringTokenizer st = new StringTokenizer(br.readLine());
        tunnelRow = Integer.parseInt(st.nextToken());
        tunnelCol = Integer.parseInt(st.nextToken());
        int manholeY = Integer.parseInt(st.nextToken());
        int manholeX = Integer.parseInt(st.nextToken());
        escapeTime = Integer.parseInt(st.nextToken());
            initTunnel();
            bfs( manholeX, manholeY );
            int output = countEscapeableSpot();
            bw.write( "#" + (i+1) + " " + output + "\n" );
        }
        bw.flush();
        bw.close();
    }

    public static void initTunnel() throws IOException{
        tunnel = new int[tunnelRow][tunnelCol];
        visited = new boolean[tunnelRow][tunnelCol];
        for( int i=0; i<tunnelRow; i++ ){
            StringTokenizer st = new StringTokenizer(br.readLine());
            for( int j=0; j<tunnelCol; j++ ){
                tunnel[i][j] = Integer.parseInt(st.nextToken());
            }
        }
    }

    public static void bfs( int x, int y ){
        visited[y][x] = true;
        queue.add( new escapedPrisoner( x, y, 1 ) );
        while(!queue.isEmpty()){
            escapedPrisoner current = queue.poll();
            int currentX = current.positionX;
            int currentY = current.positionY;
            int currentCount = current.count;
            int manholeNumber = tunnel[currentY][currentX];
            for( int i=0; i<4; i++ ){
                int nextX = currentX + directionX[manholeNumber][i];
                int nextY = currentY + directionY[manholeNumber][i];
                if( !movable( nextX, nextY ) ) continue;
                int nextManholeNumber = tunnel[nextY][nextX];
                if( !visited[nextY][nextX] && tunnel[nextY][nextX] != 0 && currentCount < escapeTime
                && directionX[manholeNumber][i] == -directionX[nextManholeNumber][(i+2)%4]
                && directionY[manholeNumber][i] == -directionY[nextManholeNumber][(i+2)%4] ){
                    visited[nextY][nextX] = true;
                    queue.add(new escapedPrisoner(nextX, nextY, currentCount + 1));
                }
            }
        }
    }

    public static boolean movable( int nextX, int nextY ){
        return ( nextX>=0 && nextX<tunnelCol && nextY>=0 && nextY<tunnelRow );
    }

    public static int countEscapeableSpot(){
        int count = 0;
        for( int i=0; i<tunnelRow; i++ ){
            for( int j=0; j<tunnelCol; j++ ){
                if (visited[i][j]) {
                    count++;
                }
            }
        }
        return count;
    }
}
