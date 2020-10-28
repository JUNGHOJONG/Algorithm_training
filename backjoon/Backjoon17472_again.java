package algorithm.backjoon;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Backjoon17472_again {
    public static class Movement{
        private int positionX;
        private int positionY;
        public Movement( int positionX, int positionY ){
            this.positionX = positionX;
            this.positionY = positionY;
        }
    }
    public static class Distance{
        private int island1;
        private int island2;
        private int distance;
        public Distance( int island1, int island2, int distance ){
            this.island1 = island1;
            this.island2 = island2;
            this.distance = distance;
        }
    }
    private static BufferedReader br = new BufferedReader( new InputStreamReader( System.in ));
    private static ArrayList<Distance> distanceManagement = new ArrayList();
    private static int mapRowSize;
    private static int mapColSize;
    private static int islandIndex = 1;
    private static int[][] map;
    private static int[][] distance;
    private static int[] directionX = { 1, 0, -1, 0 };
    private static int[] directionY = { 0, 1, 0, -1 };
    private static boolean[] visited;
    private static int[] parent;
    private static boolean check = false;
    private static int minTotalLength = Integer.MAX_VALUE;

    public static void main(String[] args) throws IOException {
        StringTokenizer st = new StringTokenizer(br.readLine());
        mapRowSize = Integer.parseInt(st.nextToken());
        mapColSize = Integer.parseInt(st.nextToken());
        initMap();
        numberingMap();
        saveShortestDistance();
        initDistanceManagement();
        int size = distanceManagement.size();
        visited = new boolean[islandIndex];
        combination( 0, 0, size );
        print();
    }

    public static void initMap() throws IOException{
        map = new int[mapRowSize][mapColSize];
        for(int i = 0; i < mapRowSize; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            for(int j = 0; j < mapColSize; j++) {
                map[i][j] = Integer.parseInt(st.nextToken());
            }
        }
    }

    public static void numberingMap(){
        boolean[][] visited = new boolean[mapRowSize][mapColSize];
        Queue<Movement> queue = new LinkedList();
        for(int i = 0; i < mapRowSize; i++) {
            for(int j = 0; j < mapColSize; j++) {
                if( map[i][j] == 1 && !visited[i][j] ){
                    visited[i][j] = true;
                    map[i][j] = islandIndex;
                    queue.add( new Movement( j, i ) );
                    while(!queue.isEmpty()){
                        Movement temp = queue.poll();
                        int currentX = temp.positionX;
                        int currentY = temp.positionY;
                        for( int k=0; k<4; k++ ){
                            int nextX = currentX + directionX[k];
                            int nextY = currentY + directionY[k];
                            if( movable( nextX, nextY ) && !visited[nextY][nextX] && map[nextY][nextX] == 1 ){
                                visited[nextY][nextX] = true;
                                map[nextY][nextX] = islandIndex;
                                queue.add( new Movement( nextX, nextY ));
                            }
                        }
                    }
                    islandIndex++;
                }
            }
        }
    }

    public static boolean movable( int nextX, int nextY ){
        return ( nextX>=0 && nextX<mapColSize && nextY>=0 && nextY<mapRowSize );
    }

    public static void saveShortestDistance(){
        distance = new int[islandIndex][islandIndex];
        initDistance();
        for(int i = 0; i < mapRowSize; i++) {
            for(int j = 0; j < mapColSize; j++) {
                if( map[i][j] != 0 ){
                    int islandIndex = map[i][j];
                    for( int k=0; k<4; k++ ){
                        int nextX = j + directionX[k];
                        int nextY = i + directionY[k];
                        while(true){
                            if( !movable( nextX, nextY ) ) break;
                            else if( map[nextY][nextX] == islandIndex ) break;
                            else if( map[nextY][nextX] != islandIndex && map[nextY][nextX] != 0 ) {
                                int bridgelength = Math.abs( nextX - j ) + Math.abs( nextY - i ) - 1;
                                int nextIslandIndex = map[nextY][nextX];
                                if( bridgelength < 2 ) break;
                                if( distance[islandIndex][nextIslandIndex] > bridgelength ){
                                    distance[islandIndex][nextIslandIndex] = bridgelength;
                                }
                                break;
                            }
                            nextX += directionX[k];
                            nextY += directionY[k];
                        }
                    }
                }
            }
        }
    }

    public static void initDistance(){
        for(int i = 1; i< islandIndex; i++ ){
            for(int j = 1; j< islandIndex; j++ ){
                distance[i][j] = Integer.MAX_VALUE;
            }
        }
    }

    public static void initDistanceManagement(){
        for(int i = 1; i< islandIndex; i++ ){
            for(int j = i; j< islandIndex; j++ ){
                if( i != j && distance[i][j] != Integer.MAX_VALUE ){
                    distanceManagement.add( new Distance( i, j, distance[i][j] ) );
                }
            }
        }
    }

    public static void combination( int k, int count, int size ){
        if( count == islandIndex - 2 ){
            updateTotalBridgeLength();
            return;
        }
        for( int i=k; i<size; i++ ){
            visited[i] = true;
            combination( i+1, count+1, size );
            visited[i] = false;
        }
    }

    public static void updateTotalBridgeLength(){
        int size = distanceManagement.size();
        parent = new int[islandIndex];
        initParent();
        int value = 0;
        for( int i=0; i<size; i++ ){
            if( visited[i] ){
                value += distanceManagement.get(i).distance;
                int island1 = distanceManagement.get(i).island1;
                int island2 = distanceManagement.get(i).island2;
                mergeParent( island1, island2 );
            }
        }
        for(int i = 1; i< islandIndex; i++ ){
            if( getParent(i) != 1 ){
                return;
            }
        }
        check = true;
        minTotalLength = Math.min( minTotalLength, value );
    }

    public static void initParent(){
        for(int i = 1; i < islandIndex; i++ ){
            parent[i] = i;
        }
    }

    public static int getParent( int data ){
        if( parent[data] == data ) return data;
        return parent[data] = getParent( parent[data] );
    }

    public static void mergeParent( int a, int b ){
        int parentA = getParent(a);
        int parentB = getParent(b);
        if( parentA < parentB ) parent[parentB] = parentA;
        else parent[parentA] = parentB;
    }

    public static void print(){
        if( check ){
            System.out.println(minTotalLength);
        }else{
            System.out.println(-1);
        }
    }
}
