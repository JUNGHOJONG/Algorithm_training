package algorithm.backjoon;

import java.io.*;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Backjoon5213 {

    public static class Tile{
        private int value;
        private int tileNumber;
        public Tile( int value, int tileNumber){
            this.value = value;
            this.tileNumber = tileNumber;
        }
    }

    public static class Person{
        private int positionX;
        private int positionY;
        private int count;
        public Person( int positionX, int positionY, int count ){
            this.positionX = positionX;
            this.positionY = positionY;
            this.count = count;
        }

    }

    private static BufferedReader br = new BufferedReader( new InputStreamReader( System.in ));
    private static BufferedWriter bw = new BufferedWriter( new OutputStreamWriter( System.out ));
    private static Queue<Person> queue = new LinkedList<>();
    private static int tileSize;
    private static Tile[][] path;
    private static int[][] visited;
    private static int[] directionX = { 1, 0, -1, 0 };
    private static int[] directionY = { 0, 1, 0, -1 };
    private static boolean check = false;
    private static int[] parent;
    private static int maxTile = 0;
    private static int shortestDistance = 0;

    public static void main(String[] args) throws IOException {
        tileSize = Integer.parseInt(br.readLine());
        initPath();
        initParent();
//        print();
        visited = new int[tileSize][tileSize*2];
        bfs();
        if( check ){
            bw.write( visited[tileSize-1][tileSize*2-1] + "\n" );
            findShortestPath( tileSize*tileSize-tileSize/2 );
        }else{
            bw.write( shortestDistance + "\n" );
            findShortestPath( maxTile );
        }
        print();
        bw.flush();
        bw.close();
    }

    public static void initPath() throws IOException{
        path = new Tile[tileSize][tileSize*2];
        int index = 0;
        for( int i=0; i<tileSize; i++ ){
            if( i % 2 == 0 ){
                for( int j=0; j<tileSize; j++ ){
                    index++;
                    StringTokenizer st = new StringTokenizer(br.readLine());
                    path[i][2*j] = new Tile( Integer.parseInt(st.nextToken()), index);
                    path[i][2*j+1] = new Tile( Integer.parseInt(st.nextToken()), index);
                }
            }else{
                path[i][0] = new Tile( 0, 0);
                path[i][2*tileSize-1] = new Tile( 0, 0);
                for( int j=0; j<tileSize-1; j++ ){
                    index++;
                    StringTokenizer st = new StringTokenizer(br.readLine());
                    path[i][2*j+1] = new Tile( Integer.parseInt(st.nextToken()), index);
                    path[i][2*j+2] = new Tile( Integer.parseInt(st.nextToken()), index);
                }
            }
        }
    }

    public static void initParent(){
        parent = new int[tileSize*tileSize-tileSize/2+1];//N*N-N/2
        int size = parent.length;
        for( int i=1; i<size; i++ ){
            parent[i] = i;
        }
    }

    public static void bfs(){
        queue.add( new Person( 0, 0, 1 ) );
        visited[0][0] = 1;
        while(!queue.isEmpty()){
            Person current = queue.poll();
            int currentX = current.positionX;
            int currentY = current.positionY;
            int tempCount = current.count;
//            System.out.println("@@@");
            if( currentX == 2*tileSize-1 && currentY == tileSize-1 ){
                check = true;
            }
//            if( maxTile < path[currentY][currentX].tileNumber ){
//                maxTile = path[currentY][currentX].tileNumber;
//                shortestDistance = visited[currentY][currentX];
//            }
//            maxTile = Math.max(maxTile, path[currentY][currentX].tileNumber);
//            shortestDistance =
            for( int i=0; i<4; i++ ){
                int nextX = currentX + directionX[i];
                int nextY = currentY + directionY[i];
                if( movable(nextX, nextY) && visited[nextY][nextX] == 0
                        && path[currentY][currentX].tileNumber == path[nextY][nextX].tileNumber ){
                    visited[nextY][nextX] = tempCount;
                    queue.add( new Person( nextX, nextY, tempCount ) );
                }else if( movable(nextX, nextY) && visited[nextY][nextX] == 0 && path[currentY][currentX].value == path[nextY][nextX].value
                        && path[currentY][currentX].tileNumber != path[nextY][nextX].tileNumber ){
                    visited[nextY][nextX] = tempCount+1;
                    parent[path[nextY][nextX].tileNumber] = path[currentY][currentX].tileNumber;
                    queue.add( new Person( nextX, nextY, visited[nextY][nextX] ) );
                    if( maxTile < path[nextY][nextX].tileNumber ){
                        maxTile = path[nextY][nextX].tileNumber;
                        shortestDistance = visited[nextY][nextX];
                    }
                }
            }
        }
    }

    public static boolean movable( int nextX, int nextY ){
        return ( nextX>=0 && nextX<2*tileSize && nextY>=0 && nextY<tileSize );
    }

    public static void findShortestPath( int index ) throws IOException{
        if( index == parent[index] ){
            bw.write( index + " " );
            return;
        }
//        System.out.println("@@");
        findShortestPath( parent[index] );
        bw.write( index + " " );
    }

    public static void findBigestTile( int index ){
        if( index == parent[index] ){
            if( index > maxTile ) maxTile = index;
            return;
        }
//        System.out.println("@");
        findBigestTile( parent[index] );
        if( index > maxTile ) maxTile = index;
    }

    public static void print(){
        for (int i = 0; i < tileSize; i++) {
            for (int j = 0; j < 2*tileSize; j++) {
                System.out.print(visited[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();
//        for (int i = 0; i < tileSize; i++) {
//            for (int j = 0; j < 2*tileSize; j++) {
//                System.out.print(path[i][j].tileNumber + " ");
//            }
//            System.out.println();
//        }
    }
}
