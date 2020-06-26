package algorithm;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Backjoon17779 {
    private static BufferedReader br = new BufferedReader( new InputStreamReader( System.in ));
    private static int mapSize;
    private static int[][] map;
    private static int[][] visited;
    private static int minValue = Integer.MAX_VALUE;
    public static void main(String[] args) throws IOException {
        mapSize = Integer.parseInt(br.readLine());
        initMap();
        doCommand();
        System.out.println( minValue );
    }

    public static void initMap() throws IOException{
        map = new int[mapSize+1][mapSize+1];
        visited = new int[mapSize+1][mapSize+1];
        for( int i=1; i<=mapSize; i++ ){
            StringTokenizer st = new StringTokenizer(br.readLine());
            for( int j=1; j<=mapSize; j++ ){
                map[i][j] = Integer.parseInt(st.nextToken());
            }
        }
    }

    public static void doCommand(){
        for (int x = 1; x <= mapSize; x++) {
            for (int y = 1; y <= mapSize; y++) {
                for (int d1 = 1; d1 <= mapSize; d1++) {
                    for( int d2 = 1; d2 <= mapSize; d2++ ){
                        if( x + d1 + d2 <= mapSize && 1 <= y - d1 && y + d2 <= mapSize ){
                            checkFiveArea( x, y, d1, d2 );
                            checkOtherArea( x, y, d1, d2 );
                            resetVisited();
                        }
                    }
                }
            }
        }
    }

    public static void resetVisited(){
        for( int i=1; i<=mapSize; i++ ){
            for( int j=1; j<=mapSize; j++ ){
                visited[i][j] = 0;
            }
        }
    }

    public static void checkFiveArea( int x, int y, int d1, int d2 ){
        for( int i=0; i<=d2; i++ ){
            for( int j=0; j<=d1; j++ ){
                visited[x+i+j][y+i-j] = 5;
            }
        }
        for( int i=0; i<=d2-1; i++ ){
            for( int j=0; j<=d1-1; j++ ){
                visited[x+1+i+j][y+i-j] = 5;
            }
        }
    }

    public static void checkOtherArea( int x, int y, int d1, int d2 ){
        int[] sum = new int[6];
        for( int j=1; j<=mapSize; j++ ){
            for( int i=1; i<=mapSize; i++ ){
                if( visited[j][i] == 5 ){
                    sum[5] += map[j][i];
                    continue;
                }
                if( j < x + d1 && i <= y ) {
                    sum[1] += map[j][i];
                }else if( j <= x + d2 && y < i ){
                    sum[2] += map[j][i];
                }else if( x + d1 <= j && i < y - d1 + d2 ){
                    sum[3] += map[j][i];
                }else if( x + d2 < j && y - d1 + d2 <= i ){
                    sum[4] += map[j][i];
                }
            }
        }
        Arrays.sort( sum );
        minValue = Math.min( sum[5] - sum[1], minValue );
    }
}
