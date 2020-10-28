package algorithm.backjoon;

import java.io.*;
import java.util.StringTokenizer;

public class Backjoon16918 {
    private static BufferedReader br = new BufferedReader( new InputStreamReader( System.in ));
    private static BufferedWriter bw = new BufferedWriter( new OutputStreamWriter( System.out ));
    private static int boardRow;
    private static int boardCol;
    private static int elapsedTime;
    private static char[][] board;
    private static boolean[][] visited;
    private static int[] directionX = { 1, 0, -1, 0 };
    private static int[] directionY = { 0, 1, 0, -1 };
    public static void main(String[] args) throws IOException {
        StringTokenizer st = new StringTokenizer(br.readLine());
        boardRow = Integer.parseInt(st.nextToken());
        boardCol = Integer.parseInt(st.nextToken());
        elapsedTime = Integer.parseInt(st.nextToken());
        initBoard();
        for( int i=1; i<elapsedTime; i++ ){
            if (i % 2 == 1) {
                fillUpBomb();
            } else {
                explodeBomb();
            }
        }
        print();
    }

    public static void initBoard() throws IOException{
        board = new char[boardRow][boardCol];
        visited = new boolean[boardRow][boardCol];
        for( int i=0; i<boardRow; i++ ){
            String temp = br.readLine();
            for( int j=0; j<boardCol; j++ ){
                board[i][j] = temp.charAt(j);
                if( board[i][j] == 'O' ) visited[i][j] = true;
            }
        }
    }

    public static void fillUpBomb(){
        for( int i=0; i<boardRow; i++ ){
            for( int j=0; j<boardCol; j++ ){
                board[i][j] = 'O';
            }
        }
    }

    public static void explodeBomb(){
        for( int i=0; i<boardRow; i++ ){
            for( int j=0; j<boardCol; j++ ){
                if(visited[i][j] && board[i][j] == 'O' ) {
                    board[i][j] = '.';
                    for( int k=0; k<4; k++ ){
                        int nextX = j + directionX[k];
                        int nextY = i + directionY[k];
                        if( movable( nextX, nextY ) && !visited[nextY][nextX] && board[nextY][nextX] == 'O' ){
                            visited[nextY][nextX] = true;
                            board[nextY][nextX] = '.';
                        }
                    }
                }
            }
        }

        for( int i=0; i<boardRow; i++ ){
            for( int j=0; j<boardCol; j++ ){
                if(visited[i][j]) {
                    visited[i][j] = false;
                }else{
                    visited[i][j] = true;
                }
            }
        }
    }

    public static boolean movable( int nextX, int nextY ){
        return ( nextX>=0 && nextX<boardCol && nextY>=0 && nextY<boardRow );
    }

    public static void print(){
        for( int i=0; i<boardRow; i++ ){
            for( int j=0; j<boardCol; j++ ){
                System.out.print( board[i][j] );
            }
            System.out.println();
        }
    }

}
