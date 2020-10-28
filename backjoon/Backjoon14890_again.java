package algorithm.backjoon;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Backjoon14890_again {
    private static BufferedReader br = new BufferedReader( new InputStreamReader( System.in ));
    private static int mapSize;
    private static int slopeLength;
    private static int[][] map;
    private static boolean[][] visited;
    private static int possiblePassCount = 0;
    public static void main(String[] args) throws IOException {
        StringTokenizer st = new StringTokenizer(br.readLine());
        mapSize = Integer.parseInt(st.nextToken());
        slopeLength =  Integer.parseInt(st.nextToken());
        initMap();
        doCommand();
        System.out.println(possiblePassCount);
//        for( int i=0; i<mapSize; i++ ){
//            for( int j=0; j<mapSize; j++ ){
//                System.out.print(visited[i][j] + " ");
//            }
//            System.out.println();
//        }
    }

    public static void initMap() throws IOException{
        map = new int[mapSize][mapSize];
        visited = new boolean[mapSize][mapSize];
        for( int i=0; i<mapSize; i++ ){
            StringTokenizer st = new StringTokenizer(br.readLine());
            for( int j=0; j<mapSize; j++ ){
                map[i][j] = Integer.parseInt(st.nextToken());
            }
        }
    }

    public static void doCommand(){
        checkTotalCol();
        visited = new boolean[mapSize][mapSize];
        swapMap();
//        checkTotalRow();
        checkTotalCol();
    }

    public static void checkTotalCol(){
        for( int i=0; i<mapSize; i++ ){
            boolean check = true;
            for( int j=0; j<mapSize; j++ ){
                int currentX = j;
                if( j == 0 ){
                    int rightX = currentX+1;
                    if( map[i][currentX] - map[i][rightX] > 0 ){
                        if( !rightCheck( currentX, i ) ){
                            check = false;
                            break;
                        }
                    }
                }else if( j > 0 && j < mapSize - 1 ){
                    int leftX = currentX-1;
                    int rightX = currentX+1;
                    if( map[i][currentX] - map[i][leftX] > 0 ){
                        if( !leftCheck( currentX, i ) ){
                            check = false;
                            break;
                        }
                    }
                    if( map[i][currentX] - map[i][rightX] > 0 ){
                        if( !rightCheck( currentX, i ) ){
                            check = false;
                            break;
                        }
                    }
                }else if( j == mapSize - 1 ){
                    int leftX = currentX-1;
                    if( map[i][currentX] - map[i][leftX] > 0 ){
                        if( !leftCheck( currentX, i ) ){
                            check = false;
                            break;
                        }
                    }
                }
            }
            if( check ) System.out.println("positionX: " + i);
            if( check ) possiblePassCount++;
        }
    }

    public static boolean leftCheck( int currentX, int currentY ){
        for( int j=currentX-1; j>=currentX-slopeLength; j-- ){
            if( !movable( j, currentY ) ) {
                return false;
            }else if( Math.abs( map[currentY][currentX] - map[currentY][j] ) != 1 ){
                return false;
            }else if( visited[currentY][j] ){
                return false;
            }
            // 차이가 1이 아니면 false
            // 주어진 영역을 넘으면 false
            // 주어진 visited가 true면 false
            //
        }
        for( int i=currentX-1; i>=currentX-slopeLength; i-- ){
            visited[currentY][i] = true;
        }
        return true;
    }

    public static boolean rightCheck( int currentX, int currentY ){
        for( int j=currentX+1; j<=currentX+slopeLength; j++ ){
            if( !movable( j, currentY ) ) {
                return false;
            }else if( Math.abs( map[currentY][currentX] - map[currentY][j] ) != 1 ){
                return false;
            }else if( visited[currentY][j] ){
                return false;
            }
        }
        for( int i=currentX+1; i<=currentX+slopeLength; i++ ){
            visited[currentY][i] = true;
        }
        return true;
    }

    public static boolean movable( int nextX, int nextY ){
        return ( nextX>=0 && nextX<mapSize && nextY>=0 && nextY<mapSize );
    }

    public static void swapMap(){
        for( int i=0; i<mapSize; i++ ){
            for( int j=i; j<mapSize; j++ ){
                if( i != j ) {
                    int temp = map[i][j];
                    map[i][j] = map[j][i];
                    map[j][i] = temp;
                }
            }
        }
    }
}
