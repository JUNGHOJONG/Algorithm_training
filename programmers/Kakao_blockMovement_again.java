package algorithm.programmers;

import java.util.LinkedList;
import java.util.Queue;

class Kakao_blockMovement_again {
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
    private static boolean[][][][] visited;
    private static int[] directionX = { 0, 1, 0, -1, 0 };
    private static int[] directionY = { 0, 0, 1, 0, -1 };
    private static int mapSize;
    public int solution(int[][] board) {
        mapSize = board.length;
        visited = new boolean[mapSize][mapSize][mapSize][mapSize];
        int answer = bfs( board );
        return answer;
    }

    public static int bfs( int[][] board ){
        Queue<Drone> queue = new LinkedList<>();
        visited[0][0][0][1] = true;
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
            }
            for( int i=1; i<=4; i++ ){ // 이동
                int nextLeftX = currentLeftX + directionX[i];
                int nextLeftY = currentLeftY + directionY[i];
                int nextRightX = currentRightX + directionX[i];
                int nextRightY = currentRightY + directionY[i];
                if( movable( nextLeftX, nextLeftY ) && movable( nextRightX, nextRightY ) && !visited[nextLeftY][nextLeftX][nextRightY][nextRightX]
                        && board[nextLeftY][nextLeftX] != 1 && board[nextRightY][nextRightX] != 1 ){
                    visited[nextLeftY][nextLeftX][nextRightY][nextRightX] = true;
                    queue.add( new Drone( nextLeftX, nextLeftY, nextRightX, nextRightY, currentCount+1 ) );
                }
            }
            int[] rotation = { 1, -1 };
            if( currentLeftY == currentRightY ){ // 가로 회전
                for( int i=0; i<2; i++ ){
                    int nextLeftX = currentLeftX;
                    int nextLeftY = currentLeftY + rotation[i];
                    int nextRightX = currentRightX;
                    int nextRightY = currentRightY + rotation[i];
                    if( movable( nextLeftX, nextLeftY ) && movable( nextRightX, nextRightY ) &&
                            board[nextLeftY][nextLeftX] == 0 && board[nextRightY][nextRightX] == 0 && !visited[currentLeftY][currentLeftX][nextLeftY][nextLeftX] ){
                        visited[currentLeftY][currentLeftX][nextLeftY][nextLeftX] = true;
                        queue.add( new Drone( currentLeftX, currentLeftY, nextLeftX, nextLeftY, currentCount+1 ) );
                    }
                    if( movable( nextLeftX, nextLeftY ) && movable( nextRightX, nextRightY ) &&
                            board[nextLeftY][nextLeftX] == 0 && board[nextRightY][nextRightX] == 0 && !visited[currentRightY][currentRightX][nextRightY][nextRightX] ){
                        visited[currentRightY][currentRightX][nextRightY][nextRightX] = true;
                        queue.add( new Drone( currentRightX, currentRightY, nextRightX, nextRightY, currentCount+1 ) );
                    }
                }
            }else{ // 세로 회전
                for( int i=0; i<2; i++ ){
                    int nextLeftX = currentLeftX + rotation[i];
                    int nextLeftY = currentLeftY;
                    int nextRightX = currentRightX + rotation[i];
                    int nextRightY = currentRightY;
                    if( movable( nextLeftX, nextLeftY ) && movable( nextRightX, nextRightY ) &&
                            board[nextLeftY][nextLeftX] == 0 && board[nextRightY][nextRightX] == 0 && !visited[currentLeftY][currentLeftX][nextLeftY][nextLeftX] ){
                        visited[currentLeftY][currentLeftX][nextLeftY][nextLeftX] = true;
                        queue.add( new Drone( currentLeftX, currentLeftY, nextLeftX, nextLeftY, currentCount+1 ) );
                    }
                    if( movable( nextLeftX, nextLeftY ) && movable( nextRightX, nextRightY ) &&
                            board[nextLeftY][nextLeftX] == 0 && board[nextRightY][nextRightX] == 0 && !visited[currentRightY][currentRightX][nextRightY][nextRightX] ){
                        visited[currentRightY][currentRightX][nextRightY][nextRightX] = true;
                        queue.add( new Drone( currentRightX, currentRightY, nextRightX, nextRightY, currentCount+1 ) );
                    }
                }
            }
        }
        return length;
    }

    public static boolean movable( int nextX, int nextY ){
        return( nextX>=0 && nextX<mapSize && nextY>=0 && nextY<mapSize );
    }
}