package algorithm.swea;

import java.io.*;
import java.util.StringTokenizer;

public class Sw_1210 {

    private static BufferedReader br = new BufferedReader( new InputStreamReader( System.in ));
    private static BufferedWriter bw = new BufferedWriter( new OutputStreamWriter( System.out ));
    private static int testCaseNumber;
    private static int[][] ladder;
    private static boolean[][] visited;
    private static int findStart = 0;
    private static int[] directionX = { 1, -1, 0 };
    private static int[] directionY = { 0, 0, -1 };
    private static boolean check = true;

    public static void main(String[] args) throws IOException {
        for( int i=1; i<=10; i++ ){
            testCaseNumber = Integer.parseInt(br.readLine());
            initLadder();
            for (int j = 1; j <= 100; j++) {
                if (ladder[100][j] == 2) {
                    visited[100][j] = true;
                    dfs(j, 100);
                }
            }
 int output = (int) Math.pow( 2, 3 );
            String temp = "12345";
            char[] temp2 = temp.toCharArray();
            bw.write( "#" + testCaseNumber + " " + findStart + "\n" );
            findStart = 0;
            check = true;
        }
        bw.flush();
        bw.close();
    }

    public static void initLadder() throws IOException{
        ladder = new int[101][101];
        visited = new boolean[101][101];
        for( int i=1; i<=100; i++ ){
            StringTokenizer st = new StringTokenizer(br.readLine());
            for( int j=1; j<=100; j++ ){
                ladder[i][j] = Integer.parseInt(st.nextToken());
            }
        }
    }

    public static void dfs( int x, int y ){
        if (y == 1) {
            findStart = x-1;
            check = false;
            return;
        }
        for( int i=0; i<3 && check; i++ ){
            int nextX = x + directionX[i];
            int nextY = y + directionY[i];
            if( movable( nextX, nextY ) && !visited[nextY][nextX] && ladder[nextY][nextX] == 1 ){
                visited[nextY][nextX] = true;
                dfs( nextX, nextY );
                visited[nextY][nextX] = false;
            }
        }
    }

    public static boolean movable( int nextX, int nextY ){
        return ( nextX>=1 && nextX<=100 && nextY<=100 && nextY>=1 );
    }
}
