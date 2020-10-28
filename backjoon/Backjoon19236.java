package algorithm.backjoon;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.StringTokenizer;

public class Backjoon19236 {
    public static void main(String[] args) throws IOException{
        Solution_Backjoon19236 processCommand = new Solution_Backjoon19236();
        processCommand.solution();
    }
}

class Solution_Backjoon19236{
    public static class Movement{
        private int positionX;
        private int positionY;
        private int direction;
        public Movement(int positionX, int positionY, int direction){
            this.positionX = positionX;
            this.positionY = positionY;
            this.direction = direction;
        }
        public void setPositionX(int positionX){ this.positionX = positionX; }
        public int getPositionX(){ return positionX; }
        public void setPositionY(int positionY){ this.positionY = positionY; }
        public int getPositionY(){ return positionY; }
        public void setDirection(int direction){ this.direction = direction; }
        public int getDirection(){ return direction; }
    }

    public static class Shark extends Movement{
        public Shark(int positionX, int positionY, int direction) {
            super(positionX, positionY, direction);
        }
    }

    private int[] directionX = {0, -1, -1, -1, 0, 1, 1, 1};
    private int[] directionY = {-1, -1, 0, 1, 1, 1, 0, -1};
    private BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    private HashMap<Integer, Integer> hashMap = new HashMap<>();
    private int max = 0;
    public void solution() throws IOException{
        int[][] fishMap = new int[4][4];
        initMap(fishMap);
        int tempValue = fishMap[0][0];
        Shark shark = initShark(fishMap);
        dfs(shark, fishMap, tempValue, 0);
        System.out.println(max);
    }

    public void initMap(int[][] map) throws IOException {
        for(int i=0; i<4; i++){
            StringTokenizer st = new StringTokenizer(br.readLine());
            for(int j=0; j<4; j++){
                int index = Integer.parseInt(st.nextToken());
                int direction = Integer.parseInt(st.nextToken()) - 1;
                map[i][j] = index;
                hashMap.put(index, direction);
            }
        }
    }

    public Shark initShark(int[][] map){
        Shark shark = new Shark(0, 0, 0);
        shark.setDirection(hashMap.get(map[0][0]));
        map[0][0] = -1;
        return shark;
    }

    public void dfs(Shark shark, int[][] map, int sum, int depth){
//        if (!isMovableShark(shark, map)) { // 이 조건 삽입 시 틀림나온다ㅜㅜ 왜 그럴까ㅜㅜ
//            max = Math.max(max, sum);
//            return;
//        }
        max = Math.max(max, sum);
        int[][] copyMap = copyMap(map);
        HashMap<Integer, Integer> temp = (HashMap<Integer, Integer>) hashMap.clone();
        moveFish(map);
        int criteriaX = shark.getPositionX();
        int criteriaY = shark.getPositionY();
        int criteriaDir = shark.getDirection();
        for(int count=1; count<=3; count++){
            int nextX = criteriaX + directionX[criteriaDir] * count;
            int nextY = criteriaY + directionY[criteriaDir] * count;
            if(movable(nextX, nextY) && map[nextY][nextX] != 0){
                int tempValue = map[nextY][nextX];
                shark.setPositionX(nextX);
                shark.setPositionY(nextY);
                shark.setDirection(hashMap.get(map[nextY][nextX]));
                map[criteriaY][criteriaX] = 0;
                map[nextY][nextX] = -1;
                dfs(shark, map, sum + tempValue, depth+1);
                shark.setPositionX(criteriaX);
                shark.setPositionY(criteriaY);
                shark.setDirection(criteriaDir);
                map[nextY][nextX] = tempValue;
            }
        }
        hashMap = (HashMap<Integer, Integer>) temp.clone();
        rollbackMap(map, copyMap);
    }

    public boolean isMovableShark(Shark shark, int[][] map){ // 왜 이 함수 넣으면 틀릴까ㅜㅜㅜ
        int x = shark.getPositionX() + directionX[shark.getDirection()];
        int y = shark.getPositionY() + directionY[shark.getDirection()];
        while(movable(x, y)){
            if(map[y][x] != 0) return true;
            x += directionX[shark.getDirection()];
            y += directionY[shark.getDirection()];
        }
        return false;
    }

    public boolean movable(int x, int y){
        return (x>=0 && x<4 && y>=0 && y<4);
    }

    public void moveFish(int[][] map){
        for(int index=1; index<=16; index++){
            loop: for(int i=0; i<4; i++){
                for(int j=0; j<4; j++){
                    if(map[i][j] == index){
                        int dirNumber = hashMap.get(index);
                        int criteriaNumber = dirNumber;
                        int nextX = j + directionX[dirNumber];
                        int nextY = i + directionY[dirNumber];
                        while(!movable(nextX, nextY) ||
                                map[nextY][nextX] == -1){
                            dirNumber = (dirNumber + 1) % 8;
                            if(dirNumber == criteriaNumber) {
                                break loop;
                            }
                            nextX = j + directionX[dirNumber];
                            nextY = i + directionY[dirNumber];
                        }
                        hashMap.put(index, dirNumber);
                        if(map[nextY][nextX] == 0){
                            map[nextY][nextX] = index;
                            map[i][j] = 0;
                            break loop;
                        }
                        if(map[nextY][nextX] != 0){
                            int temp = map[i][j];
                            map[i][j] = map[nextY][nextX];
                            map[nextY][nextX] = temp;
                            break loop;
                        }
                    }
                }
            }
        }
    }

    public int[][] copyMap(int[][] map){
        int[][] temp = new int[4][4];
        for(int i=0; i<4; i++){
            System.arraycopy(map[i], 0, temp[i], 0, 4);
        }
        return temp;
    }

    public void rollbackMap(int[][] map, int[][] copyMap){
        for(int i=0; i<4; i++){
            System.arraycopy(copyMap[i], 0, map[i], 0, 4);
        }
    }

    public void print(int[][] map){
        for(int i=0; i<4; i++){
            for(int j=0; j<4; j++){
                System.out.print(map[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

}
