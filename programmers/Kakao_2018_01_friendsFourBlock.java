package algorithm.programmers;

import java.util.HashSet;

public class Kakao_2018_01_friendsFourBlock {
    private static boolean[][] visited;
    private static char[][] changedBoard;
    private static int erasedBlockCount = 0;
    public static void main(String[] args) {
        String[] board = { "CCBDE", "AAADE", "AAABF", "CCBBF" };
        int rowSize = 4;
        int colSize = 5;
        changedBoard = new char[rowSize][colSize];
        for( int i=0; i<rowSize; i++ ){
            changedBoard[i] = board[i].toCharArray();
        }
        visited = new boolean[rowSize][colSize];
        doCommand( rowSize, colSize );
        System.out.println( erasedBlockCount );
    }

    public static void doCommand( int rowSize, int colSize ){
        while( true ){
            int tempCount = erasedBlockCount;
            checkPossibleErase( rowSize, colSize );
            eraseBlock( rowSize, colSize );
            fallBlock( rowSize, colSize );
            visited = new boolean[rowSize][colSize];
            if( tempCount == erasedBlockCount ) break;
        }
    }

    public static void checkPossibleErase( int rowSize, int colSize ){
        for( int i=0; i<rowSize-1; i++ ){
            for( int j=0; j<colSize-1; j++ ){
                if( changedBoard[i][j] >= 'A' && changedBoard[i][j] <= 'Z' ){
                    HashSet<Character> hashSet = new HashSet<>();
                    for( int k=i; k<=i+1; k++ ){
                        for( int l=j; l<=j+1; l++ ){
                            hashSet.add( changedBoard[k][l] );
                        }
                    }
                    if( hashSet.size() == 1 ){
                        for( int k=i; k<=i+1; k++ ){
                            for( int l=j; l<=j+1; l++ ){
                                visited[k][l] = true;
                            }
                        }
                    }
                }
            }
        }
    }

    public static void eraseBlock( int rowSize, int colSize ){
        for( int i=0; i<rowSize; i++ ){
            for( int j=0; j<colSize; j++ ){
                if( visited[i][j] ){
                    changedBoard[i][j] = '0';
                    erasedBlockCount++;
                }
            }
        }
    }

    public static void fallBlock( int rowSize, int colSize ){
        for( int i=rowSize-1; i>=0; i-- ){
            for( int j=colSize-1; j>=0; j-- ){
                if( changedBoard[i][j] >= 'A' && changedBoard[i][j] <= 'Z' ){
                    char temp = changedBoard[i][j];
                    int nextX = j;
                    int nextY = i+1;
                    while( movable( nextX, nextY, rowSize, colSize )
                            && changedBoard[nextY][nextX] == '0' ){
                        nextY++;
                    }
                    nextY--;
                    changedBoard[i][j] = '0';
                    changedBoard[nextY][nextX] = temp;
                }
            }
        }
    }

    public static boolean movable( int nextX, int nextY, int rowSize, int colSize ){
        return ( nextX>=0 && nextX<colSize && nextY>=0 && nextY<rowSize );
    }

}
