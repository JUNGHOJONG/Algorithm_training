package algorithm.backjoon;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Backjoon16236 {

    public static class Shark{
        private int positionX;
        private int positionY;
        private int size = 2;
        private int experienceValue = 0;
        public Shark( int positionX, int positionY ){
            this.positionX = positionX;
            this.positionY = positionY;
        }
    }

    public static class Fish{
        private int positionX;
        private int positionY;
        public Fish( int positionX, int positionY ){
            this.positionX = positionX;
            this.positionY = positionY;
        }
    }

    private static BufferedReader br = new BufferedReader( new InputStreamReader( System.in ) );
    private static int aquariumSize;
    private static int[][] aquarium;
    private static boolean[][] visited;
    private static int[][] distance;
    private static Shark shark;
    private static int[] directionX = { 1, 0, -1, 0 };
    private static int[] directionY = { 0, 1, 0, -1 };
    private static int totalTime = 0;

    public static void main(String[] args) throws IOException {
        aquariumSize = Integer.parseInt(br.readLine());
        initAquarium();
        while(true){
            visited = new boolean[aquariumSize][aquariumSize];
            distance = new int[aquariumSize][aquariumSize];
            bfs();
            int distance = getShortestDistance();
            if( distance == Integer.MAX_VALUE ) break;
            totalTime += distance;
            eatFish( distance );
        }
        System.out.println(totalTime);
    }

    public static void initAquarium() throws IOException{
        aquarium = new int[aquariumSize][aquariumSize];
        for( int i=0; i<aquariumSize; i++ ){
            StringTokenizer st = new StringTokenizer(br.readLine());
            for( int j=0; j<aquariumSize; j++ ){
                aquarium[i][j] = Integer.parseInt(st.nextToken());
                if( aquarium[i][j] == 9 ) shark = new Shark( j, i );
            }
        }
    }

    public static void bfs(){
        Queue<Shark> queue = new LinkedList<>();
        queue.add( new Shark( shark.positionX, shark.positionY ) );
        visited[shark.positionY][shark.positionX] = true;
        while(!queue.isEmpty()){
            Shark current = queue.poll();
            int currentX = current.positionX;
            int currentY = current.positionY;
            for( int i=0; i<4; i++ ){
                int nextX = currentX + directionX[i];
                int nextY = currentY + directionY[i];
                if( movable( nextX, nextY ) && !visited[nextY][nextX]
                        && aquarium[nextY][nextX] <= shark.size ){
                    visited[nextY][nextX] = true;
                    distance[nextY][nextX] = distance[currentY][currentX] + 1;
                    queue.add( new Shark( nextX, nextY ) );
                }
            }
        }
    }

    public static boolean movable( int nextX, int nextY ){
        return ( nextX>=0 && nextX<aquariumSize && nextY>=0 && nextY<aquariumSize );
    }

    public static int getShortestDistance(){
        int temp = Integer.MAX_VALUE;
        for( int i=0; i<aquariumSize; i++ ){
            for( int j=0; j<aquariumSize; j++ ){
                if( distance[i][j] > 0 && aquarium[i][j] != 0 && aquarium[i][j] < shark.size ){
                    temp = Math.min( temp, distance[i][j] );
                }
            }
        }
        return temp;
    }

    public static void eatFish( int shortestDistance ){
        Queue<Fish> queue = new LinkedList<>();
        for( int i=0; i<aquariumSize; i++ ){
            for( int j=0; j<aquariumSize; j++ ){
                if( distance[i][j] == shortestDistance
                        && aquarium[i][j] > 0 && aquarium[i][j] < shark.size ){
                    queue.add( new Fish( j, i ));
                }
            }
        }
        selectFishToEat( queue );
    }

    public static void selectFishToEat( Queue<Fish> queue ){
        int x = queue.peek().positionX;
        int y = queue.peek().positionY;
        while(!queue.isEmpty()){
            Fish temp = queue.poll();
            if( y > temp.positionY || y == temp.positionY && x > temp.positionX ){
                x = temp.positionX;
                y = temp.positionY;
            }
        }
        updateSharkPosition( x, y );
    }

    public static void updateSharkPosition( int x, int y ){
        aquarium[shark.positionY][shark.positionX] = 0;
        shark.positionX = x;
        shark.positionY = y;
        aquarium[y][x] = 9;
        if( shark.size == shark.experienceValue + 1 ){
            shark.size++;
            shark.experienceValue = 0;
        }else{
            shark.experienceValue++;
        }
    }
}
