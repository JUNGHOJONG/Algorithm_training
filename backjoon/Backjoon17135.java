package algorithm.backjoon;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Backjoon17135 {
    public static class Enemy{
        private int positionX;
        private int positionY;
        private int distance = 0;
        public Enemy( int positionX, int positionY, int distance ){
            this.positionX = positionX;
            this.positionY = positionY;
            this.distance = distance;
        }
    }
    private static BufferedReader br = new BufferedReader( new InputStreamReader( System.in ));
    private static int[][] field;
    private static int[][] copyField;
    private static int[][] enemyDistance;
//    private static boolean[][] visited;
    private static boolean[][] enemyToBoRemoved;
    private static int fieldRow;
    private static int fieldCol;
    private static int attackDistanceLimited;
    private static int totalEnemyToBeRemoved = 0;
    private static int maxEnemyToBeRemoved = 0;
    private static int[] directionX = { 1, 0, -1, 0 };
    private static int[] directionY = { 0, 1, 0, -1 };

    public static void main(String[] args) throws IOException {
        StringTokenizer st = new StringTokenizer(br.readLine());
        fieldRow = Integer.parseInt(st.nextToken());
        fieldCol = Integer.parseInt(st.nextToken());
        attackDistanceLimited = Integer.parseInt(st.nextToken());
        initField();
        combination( 0, 0 );
        System.out.println(maxEnemyToBeRemoved);
    }

    public static void initField() throws IOException{
        field = new int[fieldRow+1][fieldCol];
        copyField = new int[fieldRow+1][fieldCol];
//        visited = new boolean[fieldRow+1][fieldCol];
        for( int i=0; i<fieldRow; i++ ){
            StringTokenizer st = new StringTokenizer(br.readLine());
            for( int j=0; j<fieldCol; j++ ){
                field[i][j] = Integer.parseInt(st.nextToken());
                copyField[i][j] = field[i][j];
            }
        }
    }

    public static void combination( int k, int count ){
        if (count == 3) {
            doCommand();
            resetField();
            maxEnemyToBeRemoved = Math.max( maxEnemyToBeRemoved, totalEnemyToBeRemoved );
            totalEnemyToBeRemoved = 0;
            return;
        }
        for( int i=k; i<fieldCol; i++ ){
            copyField[fieldRow][i] = 2;
            combination( i+1, count+1 );
            copyField[fieldRow][i] = 0;
        }
    }

    public static void doCommand(){
        while(true){
            enemyToBoRemoved = new boolean[fieldRow+1][fieldCol];
            for( int i=0; i<fieldCol; i++ ){
                if( copyField[fieldRow][i] == 2 ){
                    bfs( i, fieldRow );
                    int shoretestDistance = getShortesetDistance();
                    if( shoretestDistance == Integer.MAX_VALUE ) continue;
                    getShortestEnemy( shoretestDistance );
                }
            }
            removeEnemy();
            moveAllEnemy();
            if( isPossibleGameOver() ) break;
        }
    }

    public static void bfs( int x, int y ){
        boolean[][] visited = new boolean[fieldRow+1][fieldCol];
        enemyDistance = new int[fieldRow+1][fieldCol];
        visited[y][x] = true;
        Queue<Enemy> queue = new LinkedList<>();
        queue.add( new Enemy( x, y, 0 ) );
        while(!queue.isEmpty()){
            Enemy temp = queue.poll();
            int currentX = temp.positionX;
            int currentY = temp.positionY;
            int distance = temp.distance;
            for( int i=0; i<4; i++ ){
                int nextX = currentX + directionX[i];
                int nextY = currentY + directionY[i];
                if( movable( nextX, nextY ) && !visited[nextY][nextX] ){
                    visited[nextY][nextX] = true;
                    enemyDistance[nextY][nextX] = distance+1;
                    queue.add( new Enemy( nextX, nextY, distance+1));
                }
            }
        }
    }

    public static boolean movable( int nextX, int nextY ){
        return ( nextX>=0 && nextX<fieldCol && nextY>=0 && nextY<=fieldRow );
    }

    public static int getShortesetDistance(){ // check
        int temp = Integer.MAX_VALUE;
        for( int i=0; i<fieldRow; i++ ){
            for( int j=0; j<fieldCol; j++ ){
                if( copyField[i][j] == 1 && enemyDistance[i][j] <= attackDistanceLimited ){
                    temp = Math.min( temp, enemyDistance[i][j] );
                }
            }
        }
        return temp;
    }

    public static void getShortestEnemy( int shoretestDistance ){
        Queue<Enemy> queue = new LinkedList<>();
        for( int i=0; i<fieldRow; i++ ){
            for( int j=0; j<fieldCol; j++ ){
                if( copyField[i][j] == 1 && enemyDistance[i][j] == shoretestDistance ){
                    queue.add( new Enemy( j, i, 0 ));
                }
            }
        }
        selectEnemy( queue );
    }

    public static void selectEnemy( Queue<Enemy> queue ){
        int currentX = queue.peek().positionX;
        int currentY = queue.peek().positionY;
        while(!queue.isEmpty()){
            Enemy temp = queue.poll();
            if( currentX > temp.positionX ){
                currentX = temp.positionX;
                currentY = temp.positionY;
            }
        }
        enemyToBoRemoved[currentY][currentX] = true;
    }

    public static void removeEnemy(){
        for( int i=0; i<fieldRow; i++ ){
            for( int j=0; j<fieldCol; j++ ){
                if( enemyToBoRemoved[i][j] ){
                    copyField[i][j] = 0;
                    totalEnemyToBeRemoved++;
                }
            }
        }
    }

    public static void moveAllEnemy(){
        for( int i=fieldRow-2; i>=0; i-- ){
            for( int j=0; j<fieldCol; j++ ){
                copyField[i+1][j] = copyField[i][j];
                if( i == 0 ) copyField[i][j] = 0;
            }
        }
    }

    public static boolean isPossibleGameOver(){
        for( int i=0; i<fieldRow; i++ ){
            for( int j=0; j<fieldCol; j++ ){
                if( copyField[i][j] == 1 ){
                    return false;
                }
            }
        }
        return true;
    }

    public static void resetField(){
        for( int i=0; i<fieldRow; i++ ){
            for( int j=0; j<fieldCol; j++ ){
                copyField[i][j] = field[i][j];
            }
        }
    }

}
