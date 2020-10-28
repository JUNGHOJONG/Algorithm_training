package algorithm.backjoon;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Backjoon15684 {
    public static class Ladder{
        private boolean left = false;
        private boolean right = false;
    }

    private static BufferedReader br = new BufferedReader( new InputStreamReader( System.in ));
    private static int ladderColSize;
    private static int bridgeLadderCount;
    private static int ladderRowSize;
    private static Ladder[][] ladders;
    private static boolean check = false;
    private static int minCount = Integer.MAX_VALUE;
    private static boolean improvement = true;

    public static void main(String[] args) throws IOException {
        StringTokenizer st = new StringTokenizer(br.readLine());
        ladderColSize = Integer.parseInt(st.nextToken());
        bridgeLadderCount = Integer.parseInt(st.nextToken());
        ladderRowSize = Integer.parseInt(st.nextToken());
        initLadder();
        for( int i=0; i<=3; i++ ) {
            improvement = true;
            dfs( 0, i, 0 );
            if( !improvement ) break;
        }
        System.out.println( check ? minCount : -1 );
    }

    public static void initLadder() throws IOException{
        ladders = new Ladder[ladderRowSize+1][ladderColSize];
        for( int i=0; i<=ladderRowSize; i++ ){
            for( int j=0; j<ladderColSize; j++ ){
               ladders[i][j] = new Ladder();
            }
        }
        for( int i=0; i<bridgeLadderCount; i++ ){
            StringTokenizer st = new StringTokenizer(br.readLine());
            int startY = Integer.parseInt(st.nextToken())-1;
            int startX = Integer.parseInt(st.nextToken())-1;
            ladders[startY][startX].right = true;
            ladders[startY][startX+1].left = true;
        }
    }

    public static void dfs( int index, int additionalLadder, int count ){
        if( count == additionalLadder ) {
            if(isPossibleMatching()){
                improvement = false;
                check = true;
                minCount = Math.min(count, minCount);
            }
            return;
        }
        for( int i=index; i<=(ladderColSize-1)*ladderRowSize-1 && improvement; i++ ){
            int currentX = i % ( ladderColSize - 1);
            int currentY = i / ( ladderColSize - 1);
            int nextX = currentX + 1;
            int nextY = currentY;
            if( !ladders[currentY][currentX].left && !ladders[currentY][currentX].right
                    && !ladders[nextY][nextX].left && !ladders[nextY][nextX].right ){
                ladders[currentY][currentX].right = true;
                ladders[nextY][nextX].left = true;
                dfs( i+2, additionalLadder, count+1 );
                ladders[currentY][currentX].right = false;
                ladders[nextY][nextX].left = false;
            }
        }
    }

    public static boolean isPossibleMatching(){
        for( int i=0; i<ladderColSize; i++ ){
            int x = i;
            int y = 0;
            while( y != ladderRowSize ){
                if( ladders[y][x].left ) --x;
                else if( ladders[y][x].right ) ++x;
                y++;
            }
            if( i != x ) return false;
        }
        return true;
    }
}
