package algorithm;
import java.util.*;
public class Kakao_constructionOfRacetrack {
    public class Road{
        private int positionX;
        private int positionY;
        private int cost;
        private int direction;
        public Road( int x, int y, int cost, int d ){
            this.positionX = x;
            this.positionY = y;
            this.cost = cost;
            this.direction = d;
        }

    }
    private int minValue = Integer.MAX_VALUE;
    private int boardSize;
    private int[][] cost;
    private boolean[][][] visited;
    private int[] directionX = { 1, 0, -1, 0 };
    private int[] directionY = { 0, 1, 0, -1 };
    public int solution(int[][] board) {
        boardSize = board.length;
        initCost();
        visited = new boolean[boardSize][boardSize][2];
        bfs( board );
        return minValue;
    }

    public void initCost(){
        cost = new int[boardSize][boardSize];
        for( int i=0; i<boardSize; i++ ){
            for( int j=0; j<boardSize; j++ ){
                cost[i][j] = Integer.MAX_VALUE;
            }
        }
        cost[0][0] = 0;
    }

    public void bfs( int[][] board ){
        Queue<Road> queue = new LinkedList<>();
        queue.add( new Road( 0, 0, 0, -1 ) );
        visited[0][0][0] = true;
        visited[0][0][1] = true;
        while(!queue.isEmpty()){
            Road temp = queue.poll();
            int currentX = temp.positionX;
            int currentY = temp.positionY;
            int currentCost = temp.cost;
            int currentDirection = temp.direction;
            if( currentX == boardSize-1 && currentY  == boardSize-1 ) {
                minValue = Math.min( minValue, currentCost );
                continue;
            }

            for( int i=0; i<4; i++ ){
                int nextX = currentX + directionX[i];
                int nextY = currentY + directionY[i];
                if( !movable( nextX, nextY, board ) ) continue;
                if( currentDirection == i || currentDirection == -1 ){ // 직선
                    if( !visited[nextY][nextX][0] ){
                        visited[nextY][nextX][0] = true;
                        cost[nextY][nextX] = currentCost + 100;
                        queue.add( new Road( nextX, nextY, cost[nextY][nextX], i ) );
                    }else{
                        if( cost[nextY][nextX] >= currentCost + 100 ){
                            cost[nextY][nextX] = currentCost + 100;
                            queue.add( new Road( nextX, nextY, cost[nextY][nextX], i ) );
                        }
                    }
                }else{ // 코너
                    if( !visited[nextY][nextX][1] ){
                        visited[nextY][nextX][1] = true;
                        cost[nextY][nextX] = currentCost + 600;
                        queue.add( new Road( nextX, nextY, cost[nextY][nextX], i ) );
                    }else{
                        if( cost[nextY][nextX] >= currentCost + 600 ){
                            cost[nextY][nextX] = currentCost + 600;
                            queue.add( new Road( nextX, nextY, cost[nextY][nextX], i ) );
                        }
                    }
                }
            }
        }
    }

    public boolean movable( int nextX, int nextY, int[][] board ){
        return( nextX>=0 && nextX<boardSize && nextY>=0 && nextY<boardSize && board[nextY][nextX] == 0 );
    }
}
