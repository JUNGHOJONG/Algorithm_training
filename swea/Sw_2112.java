package algorithm.swea;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Sw_2112 {
    private static BufferedReader br = new BufferedReader( new InputStreamReader( System.in ));
    private static int testCase;
    private static int depth;
    private static int width;
    private static int passCount;
    private static int minValue;
    private static int[][] map;
    private static boolean check = true;
    public static void main(String[] args) throws IOException {
        testCase = Integer.parseInt(br.readLine());
        for( int i=0; i<testCase; i++ ){
            StringTokenizer st = new StringTokenizer(br.readLine());
            depth = Integer.parseInt(st.nextToken());
            width = Integer.parseInt(st.nextToken());
            passCount = Integer.parseInt(st.nextToken());
            minValue = Integer.MAX_VALUE;
            check = true;
            initMap();
            for( int j=0; j<=depth && check; j++ ){
                dfs(0, 0, j);
            }
            System.out.println( "#" + (i+1) + " " + minValue );
        }
    }

    public static void initMap() throws IOException{
        map = new int[depth][width];
        for( int i=0; i<depth; i++ ){
            StringTokenizer st = new StringTokenizer(br.readLine());
            for( int j=0; j<width; j++ ){
                map[i][j] = Integer.parseInt(st.nextToken());
            }
        }
    }

    public static void dfs( int k, int count, int end ){
        if( count == end ){
            if( possiblePathTest() ){
                minValue = Math.min( minValue, count );
                check = false;
            }
            return;
        }
        for( int i=0; i<2 && check; i++ ){
            for( int j=k; j<depth && check; j++ ){
                int[] copyMap = copyMap( j );
                inputDrug( i, j );
                dfs( j+1, count+1, end );
                resetMap( copyMap, j );
            }
        }
    }

    public static int[] copyMap( int row ){
        int[] copyMap = new int[width];
            for( int j=0; j<width; j++ ){
                copyMap[j] = map[row][j];
            }
        return copyMap;
    }

    public static void resetMap(int[] copyMap, int row) {
        for (int j = 0; j < width; j++) {
            map[row][j] = copyMap[j];
        }
    }

    public static void inputDrug( int kindOfDrug, int row ){
        for( int j=0; j<width; j++ ){
            map[row][j] = kindOfDrug;
        }
    }

    public static boolean possiblePathTest(){
        for( int j=0; j<width; j++ ){
            int index = 1;
            boolean check = false;
            for( int i=0; i<depth-1; i++ ){
                if( map[i][j] == map[i+1][j] ){
                    index++;
                }else{
                    index = 1;
                }
                if( index == passCount ) {
                    check = true;
                    break;
                }
            }
            if (!check) {
                return false;
            }
        }
        return true;
    }
}
