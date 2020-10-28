package algorithm.backjoon;

import java.io.*;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Backjoon2636{
    public static class Movement{
        private int positionX;
        private int positionY;
        public Movement( int positionX, int positionY ){
            this.positionX = positionX;
            this.positionY = positionY;
        }
    }
    private static BufferedReader br = new BufferedReader( new InputStreamReader( System.in ));
    private static BufferedWriter bw = new BufferedWriter( new OutputStreamWriter( System.out ));
    private static Queue<Movement> queue = new LinkedList<>();
    private static int mapRow;
    private static int mapCol;
    private static int[][] map;
    private static boolean[][] visited;
    private static int[] directionX = { 1, 0, -1, 0 };
    private static int[] directionY = { 0, 1, 0, -1 };

    public static void main(String[] args) throws IOException {
        StringTokenizer st = new StringTokenizer(br.readLine());
        mapRow = Integer.parseInt(st.nextToken());
        mapCol = Integer.parseInt(st.nextToken());
        initMap();
        int time = 0;
        int output = 0;
        while( true ){
            output = countCheese();
            bfs();
            time++;
            if( checkCheese() ){ break; }
        }
        bw.write( time + "\n" );
        bw.write( output + "\n" );
        bw.flush();
        bw.close();
    }

    public static void initMap() throws IOException{
        map = new int[mapRow][mapCol];
        for( int i=0; i<mapRow; i++ ){
            StringTokenizer st = new StringTokenizer(br.readLine());
            for( int j=0; j<mapCol; j++ ){
                map[i][j] = Integer.parseInt(st.nextToken());
            }
        }
    }

    public static void bfs(){
        visited = new boolean[mapRow][mapCol];
        queue.add( new Movement(0, 0) );
        visited[0][0] = true;
        while(!queue.isEmpty()){
            Movement temp = queue.poll();
            for( int i=0; i<4; i++ ){
                int nextX = temp.positionX + directionX[i];
                int nextY = temp.positionY + directionY[i];
                if( movable( nextX, nextY ) && !visited[nextY][nextX] ){
                    if( map[nextY][nextX] == 1 ){
                        visited[nextY][nextX] = true;
                        map[nextY][nextX] = 0;
                    }else{
                        visited[nextY][nextX] = true;
                        queue.add( new Movement( nextX, nextY ));
                    }
                }
            }
        }
    }

    public static boolean movable( int nextX, int nextY ){
        return ( nextX>=0 && nextX<mapCol && nextY>=0 && nextY<mapRow );
    }

    public static int countCheese(){
        int count = 0;
        for( int i=0; i<mapRow; i++ ){
            for( int j=0; j<mapCol; j++ ){
                if( map[i][j] == 1 ) count++;
            }
        }
        return count;
    }

    public static boolean checkCheese(){
        for( int i=0; i<mapRow; i++ ){
            for( int j=0; j<mapCol; j++ ){
                if( map[i][j] == 1 ) return false;
            }
        }
        return true;
    }
}