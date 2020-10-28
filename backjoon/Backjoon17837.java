package algorithm.backjoon;

import java.io.*;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class Backjoon17837 {

    private static BufferedReader br = new BufferedReader( new InputStreamReader( System.in ) );
    private static BufferedWriter bw = new BufferedWriter( new OutputStreamWriter( System.out ));
    private static int chessSize;
    private static int chessCount;
    private static int[][] chessBoard;
    private static ArrayList<Integer>[][] chessMove;
    private static int[] chessRotation;
    private static int[] directionX = { 1, -1, 0, 0 };
    private static int[] directionY = { 0, 0, -1, 1 };

    public static void main(String[] args) throws IOException {
        StringTokenizer st = new StringTokenizer(br.readLine());
        chessSize = Integer.parseInt(st.nextToken());
        chessCount = Integer.parseInt(st.nextToken());
        initChessBoard();
        initChessMove();
        int index = 0;
        while( index<= 1000 ){
            index++;
            if( doCommand() ){
                break;
            }
        }
        print( index );
    }

    public static void initChessBoard() throws IOException{
        chessBoard = new int[chessSize+1][chessSize+1];
        for( int i=1; i<=chessSize; i++ ){
            StringTokenizer st = new StringTokenizer(br.readLine());
            for( int j=1; j<=chessSize; j++ ){
                chessBoard[i][j] = Integer.parseInt(st.nextToken());
            }
        }
    }

    public static void initChessMove() throws IOException{
        chessMove = new ArrayList[chessSize+1][chessSize+1];
        chessRotation = new int[chessCount+1];
        for( int i=1; i<=chessSize; i++ ){
            for( int j=1; j<=chessSize; j++ ){
                chessMove[i][j] = new ArrayList();
            }
        }
        for( int i=1; i<=chessCount; i++ ){
            StringTokenizer st = new StringTokenizer(br.readLine());
            int row = Integer.parseInt(st.nextToken());
            int col = Integer.parseInt(st.nextToken());
            int rotation = Integer.parseInt(st.nextToken());
            chessMove[row][col].add(i);
            chessRotation[i] = rotation-1;
        }
    }

    public static boolean doCommand(){
        for (int i=1; i<=chessCount; i++) {
            boolean check = true;
            for( int j=1; j<=chessSize && check; j++ ){
                for (int k=1; k<=chessSize && check; k++) {
                    if( chessMove[j][k].contains(i) ){
                        int currentX = k, currentY = j;
                        int rotation = chessRotation[i];
                        int index = chessMove[currentY][currentX].indexOf(i);
                        int nextX = currentX + directionX[rotation];
                        int nextY = currentY + directionY[rotation];
                        int white = 0, red = 1, blue = 2;
                        if( !movable( nextX, nextY ) || chessBoard[nextY][nextX] == blue ){ // blue
                            rotation = changeRotation( rotation );
                            chessRotation[i] = rotation;
                            nextX = currentX + directionX[rotation];
                            nextY = currentY + directionY[rotation];
                            if( !movable( nextX, nextY ) || chessBoard[nextY][nextX] == blue ){ // blue
                                check = false;
                                continue;
                            } else if( chessBoard[nextY][nextX] == white ){
                                moveChessToWhiteArea( currentX, currentY, index, rotation );
                                deleteChessPreviousArea( currentX, currentY, index );
                            } else if( chessBoard[nextY][nextX] == red ){
                                moveChessToRedArea( currentX, currentY, index, rotation );
                                deleteChessPreviousArea( currentX, currentY, index );
                            }
                        } else if( chessBoard[nextY][nextX] == white ){
                            moveChessToWhiteArea( currentX, currentY, index, rotation );
                            deleteChessPreviousArea( currentX, currentY, index );
                        } else if( chessBoard[nextY][nextX] == red ){
                            moveChessToRedArea( currentX, currentY, index, rotation );
                            deleteChessPreviousArea( currentX, currentY, index );
                        }
                        check = false;
                        if( checkChess() ) return true;
                    }
                }
            }
        }
        return false;
    }

    public static boolean movable( int nextX, int nextY ){
        return ( nextX>0 && nextX<=chessSize && nextY>0 && nextY<=chessSize );
    }

    public static int changeRotation( int rotation ){
        if( rotation == 0 ){
            return 1;
        } else if( rotation == 1 ){
            return 0;
        } else if( rotation == 2 ){
            return 3;
        } else {
            return 2;
        }
    }

    public static void moveChessToWhiteArea( int currentX, int currentY, int index, int rotation ){
        int size = chessMove[currentY][currentX].size();
        int nextX = currentX + directionX[rotation];
        int nextY = currentY + directionY[rotation];
        for( int i=index; i<=size-1; i++ ){
            chessMove[nextY][nextX].add( chessMove[currentY][currentX].get(i) );
        }
    }

    public static void deleteChessPreviousArea( int currentX, int currentY, int index ) {
        int size = chessMove[currentY][currentX].size();
        for( int i=size-1; i>=index; i-- ){
            chessMove[currentY][currentX].remove(i);
        }
    }


    public static void moveChessToRedArea( int currentX, int currentY, int index, int rotation ){
        int size = chessMove[currentY][currentX].size();
        int nextX = currentX + directionX[rotation];
        int nextY = currentY + directionY[rotation];
        for( int i=size-1; i>=index; i-- ){
            chessMove[nextY][nextX].add( chessMove[currentY][currentX].get(i) );
        }
    }

    public static boolean checkChess(){
        for( int i=1; i<=chessSize; i++ ){
            for( int j=1; j<=chessSize; j++ ){
                if( chessMove[i][j].size() >= 4 ){
                    return true;
                }
            }
        }
        return false;
    }

    public static void print( int index ) throws IOException{
        if( index > 1000 ){
            bw.write( -1 + "\n" );
        }else{
            bw.write( index + "\n" );
        }
        bw.flush();
        bw.close();
    }

}
