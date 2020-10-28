package algorithm.programmers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

class Kakao_blockMovement {
    public static class Drone{
        private int leftX;
        private int leftY;
        private int rightX;
        private int rightY;
        private int count;
        public Drone( int leftX, int leftY, int rightX, int rightY, int count ){
            this.leftX = leftX;
            this.leftY = leftY;
            this.rightX = rightX;
            this.rightY = rightY;
            this.count = count;
        }
    }
    private static BufferedReader br = new BufferedReader( new InputStreamReader( System.in ));
    private static boolean[][][] visited;
    private static int[] directionX = { 0, 1, 0, -1, 0, -1, -1, 1, 1 };
    private static int[] directionY = { 0, 0, 1, 0, -1, -1, 1, -1, 1 };
    private static int mapSize;
    private static int[][] board;
    public static void main(String[] args) throws IOException {
        mapSize = Integer.parseInt(br.readLine());
        visited = new boolean[mapSize][mapSize][2];
        initboard();
        int answer = bfs();
        System.out.println( answer );
    }

    public static void initboard() throws IOException{
        board = new int[mapSize][mapSize];
        for( int i=0; i<mapSize; i++ ){
            StringTokenizer st = new StringTokenizer(br.readLine());
            for( int j=0; j<mapSize; j++ ){
                board[i][j] = Integer.parseInt(st.nextToken());
            }
        }
    }

    public static void print(){
        for( int i=0; i<mapSize; i++ ){
            for( int j=0; j<mapSize; j++ ){
                System.out.print( visited[i][j][0] + " " );
            }
            System.out.println();
        }
        System.out.println();

        for( int i=0; i<mapSize; i++ ){
            for( int j=0; j<mapSize; j++ ){
                System.out.print( visited[i][j][1] + " " );
            }
            System.out.println();
        }
        System.out.println();
    }

    public static int bfs(){
        Queue<Drone> queue = new LinkedList<>();
        visited[0][0][0] = true;
        visited[0][1][1] = true;
        int length = Integer.MAX_VALUE;
        queue.add( new Drone( 0, 0, 1, 0, 0 ) );
        while(!queue.isEmpty()){
            Drone temp = queue.poll();
            int currentLeftX = temp.leftX;
            int currentLeftY = temp.leftY;
            int currentRightX = temp.rightX;
            int currentRightY = temp.rightY;
            int currentCount = temp.count;
            if( ( currentLeftX == mapSize-1 && currentLeftY == mapSize-1 ) || ( currentRightX == mapSize-1 && currentRightY == mapSize-1 ) ){
                length = Math.min( length, currentCount );
                System.out.println("@@:" + length );
                print();
            }
            for( int i=1; i<=8; i++ ){
                Drone nextDrone = moveDrone( temp, i );
                int nextLeftX = nextDrone.leftX;
                int nextLeftY = nextDrone.leftY;
                int nextRightX = nextDrone.rightX;
                int nextRightY = nextDrone.rightY;
                if( i>=1 && i<=4 && movable( nextLeftX, nextLeftY ) && movable( nextRightX, nextRightY ) && ( !visited[nextLeftY][nextLeftX][0] || !visited[nextRightY][nextRightX][1] ) && board[nextLeftY][nextLeftX] != 1 && board[nextRightY][nextRightX] != 1 ){
                    // if( i == 5 && board[nextLeftY-1][nextLeftX+1] == 1 ) continue;
                    // if( i == 6 && board[nextLeftY+1][nextLeftX+1] == 1 ) continue;
                    // if( i == 7 && board[nextRightY-1][nextRightX-1] == 1 ) continue;
                    // if( i == 8 && board[nextRightY+1][nextRightX-1] == 1 ) continue;
                    visited[nextLeftY][nextLeftX][0] = true;
                    visited[nextRightY][nextRightX][1] = true;
                    queue.add( new Drone( nextLeftX, nextLeftY, nextRightX, nextRightY, currentCount+1 ) );
                }else if( i>=5 && i<=6 && movable( nextLeftX, nextLeftY ) && movable( nextRightX, nextRightY ) && !visited[nextRightY][nextRightX][1] && board[nextLeftY][nextLeftX] != 1 && board[nextRightY][nextRightX] != 1 ){
                    if( i == 5 && movable( nextLeftX+1, nextLeftY-1 ) && board[nextLeftY-1][nextLeftX+1] == 1 ) continue;
                    if( i == 6 && movable( nextLeftX+1, nextLeftY+1 ) && board[nextLeftY+1][nextLeftX+1] == 1 ) continue;
                    // if( i == 7 && board[nextRightY-1][nextRightX-1] == 1 ) continue;
                    // if( i == 8 && board[nextRightY+1][nextRightX-1] == 1 ) continue;
                    visited[nextLeftY][nextLeftX][0] = true;
                    visited[nextRightY][nextRightX][1] = true;
                    queue.add( new Drone( nextLeftX, nextLeftY, nextRightX, nextRightY, currentCount+1 ) );
                }else if( i>=7 && i<=8 && movable( nextLeftX, nextLeftY ) && movable( nextRightX, nextRightY ) && !visited[nextLeftY][nextLeftX][0] && board[nextLeftY][nextLeftX] != 1 && board[nextRightY][nextRightX] != 1 ){
                    // if( i == 5 && board[nextLeftY-1][nextLeftX+1] == 1 ) continue;
                    // if( i == 6 && board[nextLeftY+1][nextLeftX+1] == 1 ) continue;
                    if( i == 7 && movable( nextRightX-1, nextRightY-1 ) && board[nextRightY-1][nextRightX-1] == 1 ) continue;
                    if( i == 8 && movable( nextRightX-1, nextRightY+1 ) && board[nextRightY+1][nextRightX-1] == 1 ) continue;
                    visited[nextLeftY][nextLeftX][0] = true;
                    visited[nextRightY][nextRightX][1] = true;
                    queue.add( new Drone( nextLeftX, nextLeftY, nextRightX, nextRightY, currentCount+1 ) );
                }
            }
        }
        return length;
    }

    public static Drone moveDrone( Drone drone, int index ){
        if( index == 1 || index == 2 || index == 3 || index == 4 ){
            return new Drone( drone.leftX + directionX[index], drone.leftY + directionY[index], drone.rightX + directionX[index], drone.rightY + directionY[index], 0 );
        }else if( index == 5 ){ // 왼쪽 기준 반시계
            int x = drone.rightX - drone.leftX;
            int y = drone.rightY - drone.leftY;
            int temp = x;
            x = -y + drone.leftX;
            y = temp + drone.leftY;
            return new Drone( drone.leftX, drone.leftY, x, y, 0 );
        }else if( index == 6 ){ // 왼쪽 기준 시계
            int x = drone.rightX - drone.leftX;
            int y = drone.rightY - drone.leftY;
            int temp = x;
            x = y + drone.leftX;
            y = -temp + drone.leftY;
            return new Drone( drone.leftX, drone.leftY, x, y, 0 );
        }else if( index == 7 ){ // 오른쪽 기준 반시계
            int x = drone.leftX - drone.rightX;
            int y = drone.leftY - drone.rightY;
            int temp = x;
            x = -y + drone.rightX;
            y = temp + drone.rightY;
            return new Drone( x, y, drone.rightX, drone.rightY, 0 );
        }else { // 오른쪽 기준 시계
            int x = drone.leftX - drone.rightX;
            int y = drone.leftY - drone.rightY;
            int temp = x;
            x = y + drone.rightX;
            y = -temp + drone.rightY;
            return new Drone( x, y, drone.rightX , drone.rightY, 0 );
        }
    }

    public static boolean movable( int nextX, int nextY ){
        return( nextX>=0 && nextX<mapSize && nextY>=0 && nextY<mapSize );
    }
}