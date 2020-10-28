package algorithm.backjoon;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Backjoon3980 {
    private static BufferedReader br = new BufferedReader( new InputStreamReader( System.in ) );
    private static int testCase;
    private static int[][] abilityValue = new int[11][11];
    private static boolean[] visited = new boolean[11];
    private static int maxValue;
    public static void main(String[] args) throws IOException {
        testCase = Integer.parseInt(br.readLine());
        for( int i=0; i<testCase; i++ ){
            maxValue = 0;
            initAbilityValue();
            dfs( 0, 0 );
            System.out.println( maxValue );
        }
    }

    public static void initAbilityValue() throws IOException{
        for( int i=0; i<11; i++ ){
            StringTokenizer st = new StringTokenizer( br.readLine() );
            for( int j=0; j<11; j++ ){
                abilityValue[i][j] = Integer.parseInt( st.nextToken() );
            }
        }
    }

    public static void dfs( int depth, int sum ){
        if( depth == 11 ){
            if( checkAllVisited() ) maxValue = Math.max( maxValue, sum );
            return;
        }
        for( int i=0; i<11; i++ ){
            if ( !visited[i] && abilityValue[depth][i] != 0 ) {
                visited[i] = true;
                dfs(depth + 1, sum + abilityValue[depth][i]);
                visited[i] = false;
            }
        }
    }

    public static boolean checkAllVisited(){
        for( int i=0; i<11; i++ ){
            if( !visited[i] ) return false;
        }
        return true;
    }
}
