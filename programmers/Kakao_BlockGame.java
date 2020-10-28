package algorithm.programmers;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Kakao_BlockGame {
    public static class Movement{
        private int positionX;
        private int positionY;
        public Movement( int positionX, int positionY ){
            this.positionX = positionX;
            this.positionY = positionY;
        }
    }
    private static BufferedReader br = new BufferedReader( new InputStreamReader( System.in ) );
    private static boolean[][] visited;
    private static int[] directionX = { 1, 0, -1, 0 };
    private static int[] directionY = { 0, 1, 0, -1 };
    private static int boardSize;
    private static int initCount = 0;
    public static void main(String[] args) throws IOException {
        boardSize = 10;
        int[][] board = initBoard( 10 );
        visited = new boolean[boardSize][boardSize];
        doCommand( board );
        System.out.println( initCount );
    }

    public static int[][] initBoard( int size ) throws IOException {
        int[][] board = new int[size][size];
        for( int i=0; i<size; i++ ){
            StringTokenizer st = new StringTokenizer( br.readLine() );
            for( int j=0; j<size; j++ ){
                board[i][j] = Integer.parseInt( st.nextToken() );
            }
        }
        return board;
    }

    public static void doCommand( int[][] board ){
        initCount = getAllBlock( board );
        while(true){
            int startCount = getAllBlock( board );
            for( int i=0; i<2; i++ ){
                fallBlackBlock( board );
                removeBlock( board );
            }
            removeBlackBlock( board );
            int endCount = getAllBlock( board );
            if( startCount == endCount ){
                initCount -= endCount;
                break;
            }
        }
    }

    public static int getAllBlock( int [][] board ){
        Queue<Movement> queue = new LinkedList<>();
        int size = board.length;
        int count = 0;
        for( int i=0; i<size; i++ ){
            for( int j=0; j<size; j++ ){
                if( !visited[i][j] && board[i][j] != 0 && board[i][j] != -1 ){
                    visited[i][j] = true;
                    queue.add( new Movement( j, i ) );
                    while( !queue.isEmpty() ){
                        Movement temp = queue.poll();
                        int currentX = temp.positionX;
                        int currentY = temp.positionY;
                        for( int k=0; k<4; k++ ){
                            int nextX = currentX + directionX[k];
                            int nextY = currentY + directionY[k];
                            if( movable( nextX, nextY ) && !visited[nextY][nextX] && board[currentY][currentX] == board[nextY][nextX] ){
                                visited[nextY][nextX] = true;
                                queue.add( new Movement( nextX, nextY ) );
                            }
                        }
                    }
                    count++;
                }
            }
        }
        resetVisted();
        return count;
    }

    public static void resetVisted(){
        for( int i=0; i<boardSize; i++ ){
            for( int j=0; j<boardSize; j++ ){
                visited[i][j] = false;
            }
        }
    }

    public static boolean movable( int nextX, int nextY ){
        return( nextX>=0 && nextX<boardSize && nextY>=0 && nextY<boardSize );
    }

    public static void fallBlackBlock( int[][] board ){
        for( int j=0; j<boardSize; j++ ){
            int currentX = j;
            int currentY = 0;
            if( board[currentY][currentX] != 0 ) continue;
            boolean check = true;
            while(true){
                if( !movable( currentX, currentY ) ) {
                    check = false;
                    break;
                }
                if( board[currentY][currentX] != 0 ) break;
                currentY++;
            }
            currentY--;
            if( check ) board[currentY][currentX] = -1;
        }
    }

    public static void removeBlock( int[][] board ){
        for (int i = 0; i < boardSize - 1; i++) {
            for (int j = 0; j < boardSize - 2; j++) {
                if (possibleRemove( j, i, board )) {
                    removePartOfBlock( j, i, board );
                }
            }
        }
        for (int i = 0; i < boardSize - 2; i++) {
            for (int j = 0; j < boardSize - 1; j++) {
                if (possibleRemove2( j, i, board )) {
                    removePartOfBlock2( j, i, board );
                }
            }
        }
    }

    public static boolean possibleRemove( int x, int y, int[][] board ){
        HashSet<Integer> hashSet = new HashSet<>();
        int blackBlockCount = 0;
        for( int startY = y; startY < y + 2; startY++ ){
            for( int startX = x; startX < x + 3; startX++ ){
                if( board[startY][startX] == -1 || board[startY][startX] >= 0  ){
                    if( board[startY][startX] == -1 ) blackBlockCount++;
                    hashSet.add( board[startY][startX] );
                }
            }
        }
        if( blackBlockCount == 2 && hashSet.size() == 2 && !hashSet.contains( 0 ) ) return true;
        return false;
    }

    public static void removePartOfBlock( int x, int y, int[][] board ){
        for( int startY = y; startY < y + 2; startY++ ){
            for( int startX = x; startX < x + 3; startX++ ){
                board[startY][startX] = 0;
            }
        }
    }

    public static boolean possibleRemove2( int x, int y, int[][] board ){
        HashSet<Integer> hashSet = new HashSet<>();
        int blackBlockCount = 0;
        for( int startY = y; startY < y + 3; startY++ ){
            for( int startX = x; startX < x + 2; startX++ ){
                if( board[startY][startX] == -1 || board[startY][startX] >= 0  ){
                    if( board[startY][startX] == -1 ) blackBlockCount++;
                    hashSet.add( board[startY][startX] );
                }
            }
        }
        if( blackBlockCount == 2 && hashSet.size() == 2 && !hashSet.contains( 0 ) ) return true;
        return false;
    }

    public static void removePartOfBlock2( int x, int y, int[][] board ){
        for( int startY = y; startY < y + 3; startY++ ){
            for( int startX = x; startX < x + 2; startX++ ){
                board[startY][startX] = 0;
            }
        }
    }

    public static void removeBlackBlock( int[][] board ){
        for( int i=0; i<boardSize; i++ ){
            for( int j=0; j<boardSize; j++ ){
                if( board[i][j] == -1 ) board[i][j] = 0;
            }
        }
    }
}
