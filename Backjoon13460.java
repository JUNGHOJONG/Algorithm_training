package algorithm;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Backjoon13460 {
    public static class Bead{
        private int redX;
        private int redY;
        private int blueX;
        private int blueY;
        private int count;
        public Bead( int redX, int redY, int blueX, int blueY, int count ){
            this.redX = redX;
            this.redY = redY;
            this.blueX = blueX;
            this.blueY = blueY;
            this.count = count;
        }
    }
    private static BufferedReader br = new BufferedReader( new InputStreamReader( System.in ) );
    private static Queue<Bead> queue = new LinkedList<>();
    private static char[][] board;
    private static boolean[][][][] visited;
    private static int[] directionX = { 1, 0, -1, 0 };
    private static int[] directionY = { 0, 1, 0, -1 };
    private static int rowSize;
    private static int colSize;

    public static void main(String[] args) throws IOException {
        StringTokenizer st = new StringTokenizer(br.readLine());
        rowSize = Integer.parseInt(st.nextToken());
        colSize = Integer.parseInt(st.nextToken());
        initBoard();
        bfs();
    }

    public static void initBoard() throws IOException{
        board = new char[rowSize][colSize];
        visited = new boolean[rowSize][colSize][rowSize][colSize];
        int redX = 0, redY = 0, blueX = 0, blueY = 0;
        for( int i=0; i<rowSize; i++ ){
            String temp = br.readLine();
            for( int j=0; j<colSize; j++ ){
                board[i][j] = temp.charAt(j);
                if( board[i][j] == 'R' ){
                    redX = j; redY = i;
                }else if( board[i][j] == 'B' ){
                    blueX = j; blueY = i;
                }
            }
        }
        visited[redY][redX][blueY][blueX] = true;
        queue.add( new Bead( redX, redY, blueX, blueY, 0 ) );
    }

    public static void bfs(){
        int minValue = Integer.MAX_VALUE;
        while(!queue.isEmpty()){
            Bead temp = queue.poll();
            int currentRedX = temp.redX; int currentRedY = temp.redY;
            int currentBlueX = temp.blueX; int currentBlueY = temp.blueY;
            int currentCount = temp.count;
            for( int i=0; i<4; i++ ){
                boolean redHole = false;
                boolean blueHole = false;
                int distanceRed = 0;
                int nextRedX = currentRedX; int nextRedY = currentRedY;
                int nextBlueX = currentBlueX; int nextBlueY = currentBlueY;
                while(true){
                    if( board[nextRedY][nextRedX] == '#' ) break;
                    if( board[nextRedY][nextRedX] == 'O' ) redHole = true;
                    nextRedX += directionX[i];
                    nextRedY += directionY[i];
                    distanceRed++;
                }
                nextRedX -= directionX[i];
                nextRedY -= directionY[i];
                distanceRed--;

                int distanceBlue = 0;
                while(true){
                    if( board[nextBlueY][nextBlueX] == '#' ) break;
                    if( board[nextBlueY][nextBlueX] == 'O' ) blueHole = true;
                    nextBlueX += directionX[i];
                    nextBlueY += directionY[i];
                    distanceBlue++;
                }
                nextBlueX -= directionX[i];
                nextBlueY -= directionY[i];
                distanceBlue--;

                if( nextRedX == nextBlueX && nextRedY == nextBlueY ){
                    if( distanceRed > distanceBlue ){
                        nextRedX -= directionX[i];
                        nextRedY -= directionY[i];
                    }else{
                        nextBlueX -= directionX[i];
                        nextBlueY -= directionY[i];
                    }
                }
                if( blueHole ) continue;
                if( redHole ) {
                    if( currentCount < 10 ) {
                        minValue = Math.min( minValue, currentCount + 1 );
                    }
                    continue;
                }
                if( !visited[nextRedY][nextRedX][nextBlueY][nextBlueX] ){
                    visited[nextRedY][nextRedX][nextBlueY][nextBlueX] = true;
                    queue.add( new Bead( nextRedX, nextRedY, nextBlueX, nextBlueY, currentCount + 1 ) );
                }
            }
        }
        print( minValue );
    }

    public static void print( int minValue ){
        System.out.println( minValue == Integer.MAX_VALUE ? -1 : minValue );
    }
}
