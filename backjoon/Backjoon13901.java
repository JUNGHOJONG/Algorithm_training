package algorithm.backjoon;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Backjoon13901 {
    private static BufferedReader br = new BufferedReader( new InputStreamReader( System.in ));
    private static int roomRow;
    private static int roomCol;
    private static char[][] room;
    private static boolean[][] visited;
    private static int[] movement = new int[4];
    private static int[] directionX = { 0, 0, -1, 1 };
    private static int[] directionY = { -1, 1, 0, 0 };
    private static int robotX = 0;
    private static int robotY = 0;

    public static void main(String[] args) throws IOException {
        initRoom();
        StringTokenizer st = new StringTokenizer(br.readLine());
        int startY = Integer.parseInt(st.nextToken());
        int startX = Integer.parseInt(st.nextToken());
        initMovement();
        bfs( startX, startY );
        System.out.println( robotY + " " + robotX );
    }

    public static void initRoom() throws IOException {
        StringTokenizer st2 = new StringTokenizer(br.readLine());
        roomRow = Integer.parseInt(st2.nextToken());
        roomCol = Integer.parseInt(st2.nextToken());
        room = new char[roomRow][roomCol];
        visited = new boolean[roomRow][roomCol];
        int obstacle = Integer.parseInt(br.readLine());
        for( int i=0; i<obstacle; i++ ){
            StringTokenizer st = new StringTokenizer(br.readLine());
            int y = Integer.parseInt(st.nextToken());
            int x = Integer.parseInt(st.nextToken());
            room[y][x] = '*';
        }
        for( int i=0; i<roomRow; i++ ){
            for( int j=0; j<roomCol; j++ ){
                if (room[i][j] != '*') {
                    room[i][j] = '.';
                }
            }
        }
    }

    public static void initMovement() throws IOException{
        StringTokenizer st = new StringTokenizer(br.readLine());
        for( int i=0; i<4; i++ ){
            movement[i] = Integer.parseInt(st.nextToken())-1;
        }
    }

    public static void bfs( int x, int y ){
        int index = 0;
        int count = 0;
        int currentX = x;
        int currentY = y;
        visited[currentY][currentX] = true;
        while( true ){
            if( count == 4 ) break;
            int direction = movement[index];
            int nextX = currentX;
            int nextY = currentY;
            while(true){
                nextX += directionX[direction];
                nextY += directionY[direction];
                if( movable( nextX, nextY ) && !visited[nextY][nextX] && room[nextY][nextX] != '*' ){
                    visited[nextY][nextX] = true;
                    count = 0;
                }else{
                    count++;
                    break;
                }
            }
            nextX -= directionX[direction];
            nextY -= directionY[direction];
            currentX = nextX;
            currentY = nextY;
            index = ( index + 1 ) % 4;
        }
        robotX = currentX;
        robotY = currentY;
    }

    public static boolean movable( int nextX, int nextY ){
        return ( nextX>=0 && nextX<roomCol && nextY>=0 && nextY<roomRow ) ;
    }
}
