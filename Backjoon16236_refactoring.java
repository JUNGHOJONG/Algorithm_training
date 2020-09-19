package algorithm;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.StringTokenizer;

public class Backjoon16236_refactoring {
    public static class Movement implements Comparable<Movement>{
        private int positionX;
        private int positionY;
        private int depth = 0;
        public Movement(int positionX, int positionY){
            this.positionX = positionX;
            this.positionY = positionY;
        }
        public void updateDepth(int depth){
            this.depth = depth + 1;
        }
        public static boolean isMovable(int x, int y){
            return (x >= 0 && x < aquariumSize && y >= 0 && y < aquariumSize);
        }
        @Override
        public int compareTo(Movement o) {
            if(this.depth == o.depth){
                if(this.positionY == o.positionY){
                    return this.positionX - o.positionX;
                }
                return this.positionY - o.positionY;
            }
            return this.depth - o.depth;
        }
        public int getPositionX(){
            return this.positionX;
        }
        public int getPositionY(){
            return this.positionY;
        }
        public void setPositionX(int x){
            this.positionX = x;
        }
        public void setPositionY(int y){
            this.positionY = y;
        }
        public int getDepth(){
            return this.depth;
        }
    }
    public static class Shark extends Movement{
        private int size = 2;
        private int experience = 0;
        public Shark(int positionX, int positionY) {
            super(positionX, positionY);
        }
        public void eatFish(){
            experience++;
            if(experience == size) upgradeSize();
        }
        public void upgradeSize(){
            size++;
            experience = 0;
        }
    }
    public static class Fish extends Movement{
        public Fish(int positionX, int positionY){
            super(positionX, positionY);
        }
    }
    private static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    private static PriorityQueue<Fish> priorityQueue;
    private static int[][] aquarium;
    private static int[] directionX = { 1, 0, -1, 0 };
    private static int[] directionY = { 0, 1, 0, -1 };
    private static int aquariumSize;
    public static void main(String[] args) throws IOException {
        aquariumSize = Integer.parseInt(br.readLine());
        priorityQueue = new PriorityQueue<>();
        Shark shark = initAquarium();
        System.out.println(doCommand(shark));
    }

    public static Shark initAquarium() throws IOException {
        Shark shark = null;
        aquarium = new int[aquariumSize][aquariumSize];
        for(int i=0; i<aquariumSize; i++){
            StringTokenizer st = new StringTokenizer(br.readLine());
            for( int j=0; j<aquariumSize; j++){
                aquarium[i][j] = Integer.parseInt(st.nextToken());
                if(aquarium[i][j] == 9) shark = new Shark(j, i);
            }
        }
        assert shark != null;
        aquarium[shark.getPositionY()][shark.getPositionX()] = 0;
        return shark;
    }

    public static int doCommand(Shark shark){
        int time = 0;
        while(true){
            bfs(shark); // 우선순위큐에 먹을 수 있는 물고기를 모두 담는다.
            if(priorityQueue.isEmpty()) break;
            shark.eatFish();
            Fish eatedFish = priorityQueue.poll();// 우선순위큐에서 꺼내서 상어가 먹는다.
            assert eatedFish != null;
            aquarium[eatedFish.getPositionY()][eatedFish.getPositionX()] = 0;
            shark.setPositionX(eatedFish.getPositionX());// 상어 위치 변경
            shark.setPositionY(eatedFish.getPositionY());
            time += eatedFish.getDepth();
            priorityQueue.clear();
        }
        return time;
    }

    public static void bfs(Shark shark){
        boolean[][] visited = new boolean[aquariumSize][aquariumSize];
        visited[shark.getPositionY()][shark.getPositionX()] = true;
        Queue<Fish> queue = new LinkedList<>();
        queue.add(new Fish(shark.getPositionX(), shark.getPositionY()));
        while(!queue.isEmpty()){
            Fish temp = queue.poll();
            for(int i=0; i<4; i++){
                int nextX = temp.getPositionX() + directionX[i];
                int nextY = temp.getPositionY() + directionY[i];
                if(Shark.isMovable(nextX, nextY) && aquarium[nextY][nextX] <= shark.size
                        && !visited[nextY][nextX]){ // 상어 사이즈보다 같거나 작은 경우만 움직일 수 있다.
                    visited[nextY][nextX] = true;
                    Fish nextFish = new Fish(nextX, nextY);
                    nextFish.updateDepth(temp.getDepth());
                    queue.add(nextFish);
                    if(aquarium[nextY][nextX] != 0
                            && aquarium[nextY][nextX] < shark.size) {
                        priorityQueue.add(nextFish);
                    }
                }
            }
        }
    }
}
