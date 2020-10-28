package algorithm.backjoon;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Backjoon17825 {

    public static void main(String[] args) throws IOException{
        Solution_Backjoon17825 processCommand = new Solution_Backjoon17825();
        processCommand.solution();
    }
}

class Solution_Backjoon17825{
    public static class Horse{
        private int location;
        private int root;
        private boolean arrived = false;
        public Horse(int location, int root) {
            this.location = location;
            this.root = root;
        }
    }
    private BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    private int max = 0;
    private int[] cube = new int[10];
    private Horse[] horse = new Horse[4];
    private boolean[][] visited = new boolean[4][22];
    private int[][] map = {
            {0, 2, 4, 6, 8, 10, 12, 14, 16, 18, 20, 22, 24, 26, 28, 30, 32, 34, 36, 38, 40, 0}, // turn X
            {10, 13, 16, 19, 25, 30, 35, 40, 0}, // 10번 turn
            {20, 22, 24, 25, 30, 35, 40, 0}, // 20번 turn
            {30, 28, 27, 26, 25, 30, 35, 40, 0} // 30번 turn
    };

    private int[] mapLength = {21, 8, 7, 8};
    public void solution() throws IOException{
        initCube();
        initHorse();
        dfs(0, 0);
        System.out.println(max);
    }

    public void initCube() throws IOException{
        StringTokenizer st = new StringTokenizer(br.readLine());
        for(int i=0; i<10; i++) {
            cube[i] = Integer.parseInt(st.nextToken()); // 1 2 3 4 1 2 3 4 1 2
        }
    }

    public void initHorse() {
        for(int i=0; i<4; i++) {
            horse[i] = new Horse(0, 0);
        }
    }

    public void dfs(int count, int sum) { // 이미 도착 지점에 도달한 말은 어떻게 처리??
        if(count == 10) {
//            System.out.println("sum: " + sum);
            max = Math.max(max, sum);
            return;
        }

        for(int i=0; i<4; i++) {
            int currentLocation = horse[i].location;
            int currentRoot = horse[i].root;
            int nextLocation = currentLocation + cube[count];
            // 말이 도착점에 도착했거나 이동하고자 하는 지점(루트도 동일해야함)에 이미 말이 있는 경우 continue
            if(horse[i].arrived) continue;
            if(currentRoot == 0) {
                if(nextLocation == 5) {
                    if(!movable(0, 1)) {
                        continue;
                    }
                    horse[i].location = 0;
                    horse[i].root = 1;
                    sum += map[horse[i].root][horse[i].location];
                    visited[horse[i].root][horse[i].location] = true;
                    dfs(count+1, sum);
                    visited[horse[i].root][horse[i].location] = false;
                    sum -= map[horse[i].root][horse[i].location];
                    horse[i].root = currentRoot;
                    horse[i].location = currentLocation;
                }else if(nextLocation == 10) { // 9
                    if(!movable(0, 2)) {
                        continue;
                    }
                    horse[i].location = 0;
                    horse[i].root = 2;
                    sum += map[horse[i].root][horse[i].location];
                    visited[horse[i].root][horse[i].location] = true;
                    dfs(count+1, sum);
                    visited[horse[i].root][horse[i].location] = false;
                    sum -= map[horse[i].root][horse[i].location];
                    horse[i].root = currentRoot;
                    horse[i].location = currentLocation;
                }else if(nextLocation == 15) { // 13
                    if(!movable(0, 3)) {
                        continue;
                    }
                    horse[i].location = 0;
                    horse[i].root = 3;
                    sum += map[horse[i].root][horse[i].location];
                    visited[horse[i].root][horse[i].location] = true;
                    dfs(count+1, sum);
                    visited[horse[i].root][horse[i].location] = false;
                    sum -= map[horse[i].root][horse[i].location];
                    horse[i].root = currentRoot;
                    horse[i].location = currentLocation;
                }else if(nextLocation >= mapLength[currentRoot]){ // 도착지점을 넘은 경우
                    horse[i].location = mapLength[currentRoot];
                    horse[i].arrived = true;
                    dfs(count+1, sum);
                    horse[i].arrived = false;
                    horse[i].location = currentLocation;
                }else {
                    if(!movable(nextLocation, 0)) {
                        continue;
                    }
                    horse[i].location = nextLocation;
                    sum += map[horse[i].root][horse[i].location];
                    visited[horse[i].root][horse[i].location] = true;
                    dfs(count+1, sum);
                    visited[horse[i].root][horse[i].location] = false;
                    sum -= map[horse[i].root][horse[i].location];
                    horse[i].location = currentLocation;
                }
            }else if(currentRoot == 1) {
                if(nextLocation >= mapLength[currentRoot]){ // 도착지점을 넘은 경우
                    horse[i].location = mapLength[currentRoot];
                    horse[i].arrived = true;
                    dfs(count+1, sum);
                    horse[i].arrived = false;
                    horse[i].location = currentLocation;
                }else {
                    if(!movable(nextLocation, currentRoot)) {
                        continue;
                    }
                    horse[i].location = nextLocation;
                    sum += map[horse[i].root][horse[i].location];
                    visited[horse[i].root][horse[i].location] = true;
                    dfs(count+1, sum);
                    visited[horse[i].root][horse[i].location] = false;
                    sum -= map[horse[i].root][horse[i].location];
                    horse[i].location = currentLocation;
                }
            }else if(currentRoot == 2) {
//                if(!movable(nextLocation, currentRoot)) {
//                    continue;
//                }
                if(nextLocation >= mapLength[currentRoot]){ // 도착지점을 넘은 경우
                    horse[i].location = mapLength[currentRoot];
                    horse[i].arrived = true;
                    dfs(count+1, sum);
                    horse[i].arrived = false;
                    horse[i].location = currentLocation;
                }else {
                    if(!movable(nextLocation, currentRoot)) {
                        continue;
                    }
                    horse[i].location = nextLocation;
                    sum += map[horse[i].root][horse[i].location];
                    visited[horse[i].root][horse[i].location] = true;
                    dfs(count+1, sum);
                    visited[horse[i].root][horse[i].location] = false;
                    sum -= map[horse[i].root][horse[i].location];
                    horse[i].location = currentLocation;
                }
            }else if(currentRoot == 3) {
//                if(!movable(nextLocation, currentRoot)) {
//                    continue;
//                }
                if(nextLocation >= mapLength[currentRoot]){ // 도착지점을 넘은 경우
                    horse[i].location = mapLength[currentRoot];
                    horse[i].arrived = true;
                    dfs(count+1, sum);
                    horse[i].arrived = false;
                    horse[i].location = currentLocation;
                }else {
                    if(!movable(nextLocation, currentRoot)) {
                        continue;
                    }
                    horse[i].location = nextLocation;
                    sum += map[horse[i].root][horse[i].location];
                    visited[horse[i].root][horse[i].location] = true;
                    dfs(count+1, sum);
                    visited[horse[i].root][horse[i].location] = false;
                    sum -= map[horse[i].root][horse[i].location];
                    horse[i].location = currentLocation;
                }
            }
        }
    }

    public boolean movable(int nextLocation, int currentRoot) {
//        System.out.println("1: " + nextLocation + " : " + currentRoot);
        for(int i=0; i<4; i++) {
//            System.out.println("2: " + horse[i].location + " : " + horse[i].root);
            if(horse[i].arrived) continue;
            if(map[horse[i].root][horse[i].location] == 0) continue;
            if(horse[i].root == currentRoot && horse[i].location == nextLocation) {
                return false;
            }else if(map[horse[i].root][horse[i].location] == 25 && map[currentRoot][nextLocation] == 25) {
                return false;
            }else if(map[horse[i].root][horse[i].location] == 30 && map[currentRoot][nextLocation] == 30) {
                if(map[horse[i].root][horse[i].location+1] == 35 && map[currentRoot][nextLocation+1] == 35) {
                    return false;
                }
            }else if(map[horse[i].root][horse[i].location] == 35 && map[currentRoot][nextLocation] == 35) {
                return false;
            }else if(map[horse[i].root][horse[i].location] == 40 && map[currentRoot][nextLocation] == 40) {
                return false;
            }
        }
        return true;
    }
}
