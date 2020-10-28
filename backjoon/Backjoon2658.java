package algorithm.backjoon;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Backjoon2658 {
    public static void main(String[] args) throws IOException{
        Solution_Backjoon2658 processCommand = new Solution_Backjoon2658();
        processCommand.solution();
    }
}

class Solution_Backjoon2658{
    public static class Point{
        private int x;
        private int y;
        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }
    private BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    private int[][] map = new int[10][10];
    private boolean[][] visited = new boolean[10][10];
    private ArrayList<Point> list = new ArrayList<>();
    private int[] directionX = {1, 1, 0, -1, -1, -1, 0, 1};
    private int[] directionY = {0, 1, 1, 1, 0, -1, -1, -1};
    public void solution() throws IOException{
        mapInit();
        int index = checkArea();
        int count = 0;
        for(int i=1; i<=index; i++) {
            ArrayList<Point> temp = new ArrayList<>();
            for(boolean[] visit : visited){
                Arrays.fill(visit, false);
            }
            findThreePoint(i, temp);
            if(temp.size() != 3) continue;
            if(isrightAangleIsosceleTriangle(temp) && isAllOneInTriangle(i, temp)) {
                count++;
                for(Point p : temp) {
                    list.add(p);
                }
            }
        }
        if(count != 1) {
            System.out.println(0);
            return;
        }
        Collections.sort(list, (a, b) -> {
            if(a.y == b.y) {
                return a.x - b.x;
            }
            return a.y - b.y;
        });
        print();
    }

    public void mapInit() throws IOException{
        for(int i=0; i<10; i++) {
            String temp = br.readLine();
            for(int j=0; j<10; j++) {
                map[i][j] = temp.charAt(j) - '0';
            }
        }
    }

    public int checkArea() {
        int index = 1;
        Queue<Point> queue = new LinkedList<>();
        for(int i=0; i<10; i++) {
            for(int j=0; j<10; j++) {
                if(map[i][j] == 1 && !visited[i][j]) {
                    queue.add(new Point(j, i));
                    visited[i][j] = true;
                    map[i][j] = index;
                    while(!queue.isEmpty()) {
                        Point temp = queue.poll();
                        for(int k=0; k<8; k+=2) {
                            int nextX = temp.x + directionX[k];
                            int nextY = temp.y + directionY[k];
                            if(!movable(nextX, nextY)) continue;
                            if(!visited[nextY][nextX] && map[nextY][nextX] == 1) {
                                visited[nextY][nextX] = true;
                                map[nextY][nextX] = index;
                                queue.add(new Point(nextX, nextY));
                            }
                        }
                    }
                    index++;
                }
            }
        }
        return index;
    }

    public void findThreePoint(int index, ArrayList<Point> temp) {
        // 오른쪽 아래로 이동하며 체크(y 기준)
        loop: for(int i=0; i<10; i++) {
            for(int j=0; j<10; j++) {
                if(map[i][j] == index) {
                    for(Point p : temp) {
                        if(p.x == j && p.y == i) {
                            break loop;
                        }
                    }
                    temp.add(new Point(j, i));
                    break loop;
                }
            }
        }
        loop: for(int i=0; i<10; i++) {
            for(int j=9; j>=0; j--) {
                if(map[i][j] == index) {
                    for(Point p : temp) {
                        if(p.x == j && p.y == i) {
                            break loop;
                        }
                    }
                    temp.add(new Point(j, i));
                    break loop;
                }
            }
        }
        // 왼쪽 위로 이동하며 체크(y 기준)
        loop: for(int i=9; i>=0; i--) {
            for(int j=9; j>=0; j--) {
                if(map[i][j] == index) {
                    for(Point p : temp) {
                        if(p.x == j && p.y == i) {
                            break loop;
                        }
                    }
                    temp.add(new Point(j, i));
                    break loop;
                }
            }
        }
        loop: for(int i=9; i>=0; i--) {
            for(int j=0; j<10; j++) {
                if(map[i][j] == index) {
                    for(Point p : temp) {
                        if(p.x == j && p.y == i) {
                            break loop;
                        }
                    }
                    temp.add(new Point(j, i));
                    break loop;
                }
            }
        }
        // 오른쪽 위로 이동하며 체크(x 기준)
        loop: for(int j=0; j<10; j++) {
            for(int i=9; i>=0; i--) {
                if(map[i][j] == index) {
                    for(Point p : temp) {
                        if(p.x == j && p.y == i) {
                            break loop;
                        }
                    }
                    temp.add(new Point(j, i));
                    break loop;
                }
            }
        }
        loop: for(int j=0; j<10; j++) {
            for(int i=0; i<10; i++) {
                if(map[i][j] == index) {
                    for(Point p : temp) {
                        if(p.x == j && p.y == i) {
                            break loop;
                        }
                    }
                    temp.add(new Point(j, i));
                    break loop;
                }
            }
        }
        // 왼쪽 아래로 이동하며 체크(x 기준)
        loop: for(int j=9; j>=0; j--) {
            for(int i=0; i<10; i++) {
                if(map[i][j] == index) {
                    for(Point p : temp) {
                        if(p.x == j && p.y == i) {
                            break loop;
                        }
                    }
                    temp.add(new Point(j, i));
                    break loop;
                }
            }
        }
        loop: for(int j=9; j>=0; j--) {
            for(int i=9; i>=0; i--) {
                if(map[i][j] == index) {
                    for(Point p : temp) {
                        if(p.x == j && p.y == i) {
                            break loop;
                        }
                    }
                    temp.add(new Point(j, i));
                    break loop;
                }
            }
        }
    }

    public boolean isrightAangleIsosceleTriangle(ArrayList<Point> pointList) {
        // 적어도 한쌍은 x 또는 y축에 평행해야한다.
        boolean check = false;
        for(int i=0; i<2; i++) {
            for(int j=i+1; j<3; j++) {
                if(pointList.get(i).x == pointList.get(j).x
                        || pointList.get(i).y == pointList.get(j).y) {
                    check = true;
                }
            }
        }
        if(!check) return false;

        // a^2 = b^2 + c^2 만족해야함
        ArrayList<Double> temp = new ArrayList<>();
        for(int i=0; i<2; i++) {
            for(int j=i+1; j<3; j++) {
                double lengthX = Math.abs(pointList.get(i).x - pointList.get(j).x);
                double lengthY = Math.abs(pointList.get(i).y - pointList.get(j).y);
                double length = Math.sqrt(Math.pow(lengthX, 2) + Math.pow(lengthY, 2));
                temp.add(length);
            }
        }
        Collections.sort(temp);
        double a = Math.round((Math.pow(temp.get(2), 2)*1000000000)/1000000000.0);
        double b = Math.round((Math.pow(temp.get(1), 2)*1000000000)/1000000000.0);
        double c = Math.round((Math.pow(temp.get(0), 2)*1000000000)/1000000000.0); // pie*100)/100.0
        if(a != b + c || b != c) {
            return false;
        }
        return true;
    }

    public boolean isAllOneInTriangle(int index, ArrayList<Point> pointList) {
        // 0인 곳에서 bfs 돌린다
        Queue<Point> queue = new LinkedList<>();
        for(int i=0; i<10; i++) {
            for(int j=0; j<10; j++) {
                if(map[i][j] == 0) {
                    queue.add(new Point(j, i));
                    visited[i][j] = true;
                    while(!queue.isEmpty()) {
                        Point temp = queue.poll();
                        int count = 0;
                        for(int l=0; l<8; l++){
                            int checkX = temp.x + directionX[l];
                            int checkY = temp.y + directionY[l];
                            if(!movable(checkX, checkY)) continue;
                            if(map[temp.y][temp.x] == 0 && map[checkY][checkX] == 1) count++;
                        }
                        if(count >= 4) return false;
                        for(int k=0; k<8; k+=2) {
                            int nextX = temp.x + directionX[k];
                            int nextY = temp.y + directionY[k];
                            if(!movable(nextX, nextY)) continue;
                            if(!visited[nextY][nextX] && map[nextY][nextX] < 1) {
                                // 8방향으로 돌려서 4개 이상이면 return false;
                                visited[nextY][nextX] = true;
                                queue.add(new Point(nextX, nextY));
                            }
                        }
                    }
                }
            }
        }

        // 1인곳에서 bfs돌리다가 방문 안했으면서 0인 놈을 만나면 return false;
        for(int i=0; i<10; i++) {
            for (int j = 0; j < 10; j++) {
                if (map[i][j] == index) {
                    queue.add(new Point(j, i));
                    visited[i][j] = true;
                    while (!queue.isEmpty()) {
                        Point temp = queue.poll();
                        for (int k = 0; k < 8; k += 2) {
                            int nextX = temp.x + directionX[k];
                            int nextY = temp.y + directionY[k];
                            if (!movable(nextX, nextY)) continue;
                            if (!visited[nextY][nextX] && map[nextY][nextX] == 0) return false;
                            // 만약 방문한 0인데
                            if (!visited[nextY][nextX] && map[nextY][nextX] == index) {
                                visited[nextY][nextX] = true;
                                queue.add(new Point(nextX, nextY));
                            }
                        }
                    }
                }
            }
        }
        return true;
    }

    public boolean movable(int nextX, int nextY) {
        return (nextX >= 0 && nextX < 10 && nextY >= 0 && nextY < 10);
    }

    public void print() {
        for(Point p : list) {
            System.out.println((p.y + 1) + " " + (p.x + 1));
        }
    }
}
