package algorithm.backjoon;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Backjoon17406 {
    public static class Rotation{
        private int startRow;
        private int startCol;
        private int endRow;
        private int endCol;
        public Rotation( int startRow, int startCol, int endRow, int endCol ){
            this.startRow = startRow;
            this.startCol = startCol;
            this.endRow = endRow;
            this.endCol = endCol;
        }
    }
    private static BufferedReader br = new BufferedReader( new InputStreamReader( System.in ));
    private static int rowSize;
    private static int colSize;
    private static int rotationCount;
    private static int[][] map;
    private static int[][] copyMap;
    private static Rotation[] rotations;
    private static int minValue = Integer.MAX_VALUE;

    public static void main(String[] args) throws IOException {
        StringTokenizer st = new StringTokenizer(br.readLine());
        rowSize = Integer.parseInt(st.nextToken());
        colSize = Integer.parseInt(st.nextToken());
        rotationCount = Integer.parseInt(st.nextToken());
        initMap();
        initRotationArray();
        permutation( 0 );
        System.out.println( minValue );
    }

    public static void initMap() throws IOException{
        map = new int[rowSize][colSize];
        copyMap = new int[rowSize][colSize];
        for( int i=0; i<rowSize; i++ ){
            StringTokenizer st = new StringTokenizer(br.readLine());
            for( int j=0; j<colSize; j++ ){
                map[i][j] = Integer.parseInt(st.nextToken());
                copyMap[i][j] = map[i][j];
            }
        }
    }

    public static void initRotationArray() throws IOException{
        rotations = new Rotation[rotationCount];
        for( int i=0; i<rotationCount; i++ ){
            StringTokenizer st = new StringTokenizer(br.readLine());
            int row = Integer.parseInt(st.nextToken())-1;
            int col = Integer.parseInt(st.nextToken())-1;
            int length = Integer.parseInt(st.nextToken());
            rotations[i] = new Rotation( row-length, col-length, row+length, col+length );
        }
    }

    public static void permutation( int k ){
        if( k == rotationCount ){
            doCommand();
            getMinSum();
            resetMap();
            return;
        }
        for( int i=k; i<rotationCount; i++ ){
            swap( i, k );
            permutation( k+1 );
            swap( i, k );
        }
    }

    public static void swap( int i, int k ){
        Rotation temp = rotations[i];
        rotations[i] = rotations[k];
        rotations[k] = temp;
    }

    public static void doCommand(){
        for( int i=0; i<rotationCount; i++ ){
            int startRow = rotations[i].startRow;
            int startCol = rotations[i].startCol;
            int endRow = rotations[i].endRow;
            int endCol = rotations[i].endCol;
            while(true){
                if( startCol == endCol && startRow == endRow ){
                    break;
                }else if( startCol > endCol && startRow > endRow ){
                    break;
                }
                int endValue = moveRight( startRow, startCol, endCol - startCol );
                endValue = moveDown( startRow, endCol, endCol - startCol, endValue );
                endValue = moveLeft( endRow, endCol, endCol - startCol, endValue );
                moveUp(startRow, startCol, endCol - startCol, endValue);
                startRow++;
                startCol++;
                endRow--;
                endCol--;
            }
        }

    }

    public static int moveRight( int startRow, int startCol, int length ){
        int temp = map[startRow][startCol+length];
        for( int j=startCol+length-1; j>=startCol; j-- ){
            map[startRow][j+1] = map[startRow][j];
        }
        return temp;
    }

    public static int moveDown( int startRow, int startCol, int length, int endValue ){
        int temp = map[startRow+length][startCol];
        for( int i=startRow+length-1; i>=startRow+1; i-- ){
            map[i+1][startCol] = map[i][startCol];
        }
        map[startRow+1][startCol] = endValue;
        return temp;
    }

    public static int moveLeft( int startRow, int startCol, int length, int endValue ){
        int temp = map[startRow][startCol-length];
        for( int j=startCol-length+1; j<=startCol-1; j++ ){
            map[startRow][j-1] = map[startRow][j];
        }
        map[startRow][startCol-1] = endValue;
        return temp;
    }

    public static void moveUp( int startRow, int startCol, int length, int endValue ){
        for (int i = startRow + 1; i < startRow + length; i++) {
            map[i-1][startCol] =  map[i][startCol];
        }
        map[startRow+length-1][startCol] = endValue;
    }

    public static void getMinSum(){
        for( int i=0; i<rowSize; i++ ){
            int sum = 0;
            for( int j=0; j<colSize; j++ ){
                sum += map[i][j];
            }
            minValue = Math.min( minValue, sum );
        }
    }

    public static void resetMap(){
        for( int i=0; i<rowSize; i++ ){
            for( int j=0; j<colSize; j++ ){
                map[i][j] = copyMap[i][j];
            }
        }
    }

}
