package algorithm.backjoon;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Backjoon1194_again {
    public static class Person{
        private int positionX;
        private int positionY;
        private int count;
        private int key;
        public Person( int x, int y, int count, int key ){
            this.positionX = x;
            this.positionY = y;
            this.count = count;
            this.key = key;
        }
    }
    private static BufferedReader br = new BufferedReader( new InputStreamReader( System.in ));
    private static int mazeRowSize;
    private static int mazeColSize;
    private static char[][] maze;
    private static boolean[][][] visited;
    private static Queue<Person> queue = new LinkedList<>();
    private static int[] directionX = { 1, 0, -1, 0 };
    private static int[] directionY = { 0, 1, 0, -1 };
    private static boolean check = false;
    private static int minDistance = Integer.MAX_VALUE;

    public static void main(String[] args) throws IOException {
        StringTokenizer st = new StringTokenizer(br.readLine());
        mazeRowSize = Integer.parseInt(st.nextToken());
        mazeColSize = Integer.parseInt(st.nextToken());
        initMaze();
        bfs();
        System.out.println( check ? minDistance : -1 );
    }

    public static void initMaze() throws IOException{
        maze = new char[mazeRowSize][mazeColSize];
        visited = new boolean[mazeRowSize][mazeColSize][64];
        for( int i=0; i<mazeRowSize; i++ ){
            String temp = br.readLine();
            for( int j=0; j<mazeColSize; j++ ){
                maze[i][j] = temp.charAt(j);
                if( maze[i][j] == '0' ){
                    queue.add( new Person( j, i, 0, 0 ) );
                    visited[i][j][0] = true;
                }
            }
        }
    }

    public static void bfs() {
        while (!queue.isEmpty()) {
            Person person = queue.poll();
            int currentX = person.positionX;
            int currentY = person.positionY;
            int currentCount = person.count;
            int currentKey = person.key;
            if (maze[currentY][currentX] == '1') {
                check = true;
                if( currentCount < minDistance ) minDistance = currentCount;
            }
            for (int i = 0; i < 4; i++) {
                int nextX = currentX + directionX[i];
                int nextY = currentY + directionY[i];
                if ( !movable( nextX, nextY ) || maze[nextY][nextX] == '#' ) continue;
                if (maze[nextY][nextX] >= 'a' && maze[nextY][nextX] <= 'z') {
                    int nextKey = currentKey | ( 1 << ( maze[nextY][nextX] - 'a' ) );
                    if (!visited[nextY][nextX][nextKey]) {
                        visited[nextY][nextX][nextKey] = true;
                        queue.add(new Person( nextX, nextY, currentCount + 1, nextKey ));
                    }
                } else if (maze[nextY][nextX] >= 'A' && maze[nextY][nextX] <= 'Z') {
                    if ( checkKey( currentKey, maze[nextY][nextX] ) && !visited[nextY][nextX][currentKey] ) {
                        visited[nextY][nextX][currentKey] = true;
                        queue.add(new Person(nextX, nextY, currentCount + 1, currentKey ));
                    }
                } else { // . Or O
                    if( !visited[nextY][nextX][currentKey] ){
                        visited[nextY][nextX][currentKey] = true;
                        queue.add(new Person(nextX, nextY, currentCount + 1, currentKey));
                    }
                }
            }
        }
    }

    public static boolean movable( int nextX, int nextY ){
        return ( nextX>=0 && nextX<mazeColSize && nextY>=0 && nextY<mazeRowSize );
    }

    public static boolean checkKey( int key, char door ){
        int output = key & ( 1 << door - 'A' );
        return output != 0 ? true : false;
    }
}
