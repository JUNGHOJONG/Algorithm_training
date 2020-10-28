package algorithm.backjoon;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Backjoon17472 {
    public static class Island{
        private int number1;
        private int number2;
        public Island( int number1, int number2 ){
            this.number1 = number1;
            this.number2 = number2;
        }
    }
    public static class Area{
        private int positionX;
        private int positionY;
        public Area( int positionX, int positionY ){
            this.positionX = positionX;
            this.positionY = positionY;
        }
    }
    private static BufferedReader br = new BufferedReader( new InputStreamReader( System.in ));
    private static int mapRowSize;
    private static int mapColSize;
    private static int[][] map;
    private static boolean[][] visited;
    private static int islandCount;
    private static int linkCount;
    private static int[] directionX = { 1, 0, -1, 0 };
    private static int[] directionY = { 0, 1, 0, -1 };
    private static boolean[] combinationVisited;
    private static int[] combination;
//    private static Island[] islands;
    private static ArrayList<Island> list = new ArrayList<>();
    private static boolean[] linkVisited;

    public static void main(String[] args) throws IOException {
        StringTokenizer st = new StringTokenizer(br.readLine());
        mapRowSize = Integer.parseInt(st.nextToken());
        mapColSize = Integer.parseInt(st.nextToken());
        initMap();
        numberingIsland();
        initCombination();
        combination( 0, 0 );
        System.out.println( list.size() ); // 10개
        linkCount = list.size();
//        combanation2( 0, 0 );
    }

    public static void initMap() throws IOException{
        map = new int[mapRowSize][mapColSize];
        visited = new boolean[mapRowSize][mapColSize];
        for( int i=0; i<mapRowSize; i++ ){
            StringTokenizer st = new StringTokenizer(br.readLine());
            for( int j=0; j<mapColSize; j++ ){
                map[i][j] = Integer.parseInt(st.nextToken());
            }
        }
    }

    public static void numberingIsland(){
        int islandNumber = 0;
        for( int i=0; i<mapRowSize; i++ ){
            for( int j=0; j<mapColSize; j++ ){
                if( map[i][j] == 1 && !visited[i][j] ){
                    islandNumber++;
                    bfs( j, i, islandNumber );
                }
            }
        }
        islandCount = islandNumber;
    }

    public static void bfs( int x, int y, int index ){
        Queue<Area> queue = new LinkedList<>();
        visited[y][x] = true;
        queue.add( new Area( x, y ));
        while(!queue.isEmpty()){
            Area temp = queue.poll();
            int currentX = temp.positionX;
            int currentY = temp.positionY;
            for( int i=0; i<4; i++ ){
                int nextX = currentX + directionX[i];
                int nextY = currentY + directionY[i];
                if( movable( nextX, nextY ) && !visited[nextY][nextX] && map[nextY][nextX] == 1 ){
                    visited[nextY][nextX] = true;
                    map[nextY][nextX] = index;
                    queue.add( new Area( nextX, nextY ) );
                }
            }
        }
    }

    public static boolean movable( int nextX, int nextY ){
        return ( nextX>=0 && nextX<mapColSize && nextY>=0 && nextY<mapRowSize);
    }

    public static void initCombination(){
        combinationVisited = new boolean[islandCount];
        combination = new int[islandCount];
        for( int i=0; i<islandCount; i++ ){
            combination[i] = i+1;
        }
    }

    public static void combination( int k, int count ){
        if( count == 2 ){
            initLinkedIsland();
            return;
        }
        for( int i=k; i<islandCount; i++ ){
            combinationVisited[i] = true;
            combination( i+1, count+1);
            combinationVisited[i] = false;
        }
    }

    public static void initLinkedIsland(){
        int[] temp = new int[2];
        int index = 0;
        for (int i = 0; i < islandCount; i++) {
            if (combinationVisited[i]) {
                temp[index] = combination[i];
                index++;
            }
        }
        list.add( new Island( temp[0], temp[1] ) );
    }

    public static void combination2( int k, int count ){
        if( count == islandCount-1 ){
           if( isPossibleLink() ){
//               if(  ) doCommand();
               int output = doCommand();
               if( output != -1){
                   // 최신화 min 갱신
               }
           }
            return;
        }
        for( int i=k; i<linkCount; i++ ){
            linkVisited[i] = true;
            combination2( i+1, count+1);
            linkVisited[i] = false;
        }
    }

    public static boolean isPossibleLink(){
        HashSet<Integer> hashSet = new HashSet<>();
        for (int i = 0; i < islandCount; i++) {
            if (combinationVisited[i]) {
                hashSet.add( list.get(i).number1 );
                hashSet.add( list.get(i).number2 );
            }
        }
        for( int i=1; i<=islandCount; i++ ){
            if(!hashSet.contains(i)){
                return false;
            }
        }
        return true;
    }

    public static int doCommand(){
        int tempOutput = 0;
        for (int i = 0; i < islandCount; i++) {
            if (combinationVisited[i]) {
                int temp1 = list.get(i).number1;
                int temp2 = list.get(i).number2;
//                int output = getBridgeLength( temp1, temp2 );
//                if( output != -1 ){
//                    tempOutput += output;
//                }else{
//                    return -1;
//                }
            }
        }
        return tempOutput;
    }
}
