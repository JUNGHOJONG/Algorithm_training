package algorithm;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Backjoon12100 {
    private static BufferedReader br = new BufferedReader( new InputStreamReader( System.in ));
    private static int[][] board;
    private static int[] directionX = { 1, 0, -1, 0 };
    private static int[] directionY = { 0, 1, 0, -1 };
    private static int boardSize;
    private static int maxValue = 0;
    public static void main(String[] args) throws IOException {
        boardSize = Integer.parseInt(br.readLine());
        initBoard();
        dfs( 0 );
        System.out.println( maxValue );
    }

    public static void initBoard() throws IOException{
        board = new int[boardSize][boardSize];
        for( int i=0; i<boardSize; i++ ){
            StringTokenizer st = new StringTokenizer(br.readLine());
            for( int j=0; j<boardSize; j++ ){
                board[i][j] = Integer.parseInt(st.nextToken());
            }
        }
    }

    public static void dfs( int depth ){
        if( depth == 5 ){
            maxValue = Math.max( maxValue, getMaxValue() );
            return;
        }
        for( int i=0; i<4; i++ ){
            int[][] copyBoard = getCopyBoard();
            moveBoard( i );
            mergeBoard( i );
            moveBoard( i );
            dfs( depth+1 );
            resetBoard( copyBoard );
        }
    }

    public static int getMaxValue(){
        int temp = 0;
        for( int i=0; i<boardSize; i++ ){
            for( int j=0; j<boardSize; j++ ){
                if( temp < board[i][j] ) temp = board[i][j];
            }
        }
        return temp;
    }

    public static int[][] getCopyBoard(){
        int[][] copyBoard = new int[boardSize][boardSize];
        for( int i=0; i<boardSize; i++ ){
            for( int j=0; j<boardSize; j++ ){
                copyBoard[i][j] = board[i][j];
            }
        }
        return copyBoard;
    }

    public static void moveBoard( int direction ){
        if( direction == 0 ){
            for( int i=boardSize-1; i>=0; i-- ){
                for( int j=boardSize-1; j>=0; j-- ){
                    if( board[i][j] != 0 ){
                        int temp = board[i][j];
                        board[i][j] = 0;
                        while(true){
                        if( !movable( j, i ) || board[i][j] != 0 ) break;
                            i += directionY[direction];
                            j += directionX[direction];
                        }
                        i -= directionY[direction];
                        j -= directionX[direction];
                        board[i][j] = temp;
                    }
                }
            }
        }else if( direction == 1 ){
            for( int j=0; j<boardSize; j++ ){
                for( int i=boardSize-1; i>=0; i-- ){
                    if( board[i][j] != 0 ){
                        int temp = board[i][j];
                        board[i][j] = 0;
                        while(true){
                            if( !movable( j, i ) || board[i][j] != 0 ) break;
                            i += directionY[direction];
                            j += directionX[direction];
                        }
                        i -= directionY[direction];
                        j -= directionX[direction];
                        board[i][j] = temp;
                    }
                }
            }
        }else if( direction == 2 ){
            for( int i=boardSize-1; i>=0; i-- ){
                for( int j=0; j<boardSize; j++ ){
                    if( board[i][j] != 0 ){
                        int temp = board[i][j];
                        board[i][j] = 0;
                        while(true){
                            if( !movable( j, i ) || board[i][j] != 0 ) break;
                            i += directionY[direction];
                            j += directionX[direction];
                        }
                        i -= directionY[direction];
                        j -= directionX[direction];
                        board[i][j] = temp;
                    }
                }
            }
        }else{
            for( int j=0; j<boardSize; j++ ){
                for( int i=0; i<boardSize; i++ ){
                    if( board[i][j] != 0 ){
                        int temp = board[i][j];
                        board[i][j] = 0;
                        while(true){
                            if( !movable( j, i ) || board[i][j] != 0 ) break;
                            i += directionY[direction];
                            j += directionX[direction];
                        }
                        i -= directionY[direction];
                        j -= directionX[direction];
                        board[i][j] = temp;
                    }
                }
            }
        }
    }

    public static boolean movable( int x, int y ){
        return( x>=0 && x<boardSize && y>=0 && y<boardSize );
    }

    public static void mergeBoard( int direction ){
        if( direction == 0 ){
            for( int i=boardSize-1; i>=0; i-- ){
                for( int j=boardSize-1; j>=1; j-- ){
                    if( board[i][j] == board[i][j-1] ){
                        board[i][j] = 2 * board[i][j];
                        board[i][j-1] = 0;
                    }
                }
            }
        }else if( direction == 1 ){
            for( int j=0; j<boardSize; j++ ){
                for( int i=boardSize-1; i>0; i-- ){
                    if( board[i][j] == board[i-1][j] ){
                        board[i][j] = 2 * board[i][j];
                        board[i-1][j] = 0;
                    }
                }
            }
        }else if( direction == 2 ){
            for( int i=boardSize-1; i>=0; i-- ){
                for( int j=0; j<boardSize-1; j++ ){
                    if( board[i][j] == board[i][j+1] ){
                        board[i][j] = 2 * board[i][j];
                        board[i][j+1] = 0;
                    }
                }
            }
        }else{
            for( int j=0; j<boardSize; j++ ){
                for( int i=0; i<boardSize-1; i++ ){
                    if( board[i][j] == board[i+1][j] ){
                        board[i][j] = 2 * board[i][j];
                        board[i+1][j] = 0;
                    }
                }
            }
        }
    }

    public static void resetBoard( int[][] copyBoard ){
        for( int i=0; i<boardSize; i++ ){
            for( int j=0; j<boardSize; j++ ){
                board[i][j] = copyBoard[i][j];
            }
        }
    }
}
