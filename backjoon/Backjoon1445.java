package algorithm.backjoon;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Backjoon1445 {
    public static class Garbage{
        private int garbageThrough = Integer.MAX_VALUE;
        private int garbageSide = Integer.MAX_VALUE;
    }
    public static class Movement{
        private int positionX;
        private int positionY;
        public Movement( int positionX, int positionY ){
            this.positionX = positionX;
            this.positionY = positionY;
        }
    }
    private static BufferedReader br = new BufferedReader( new InputStreamReader( System.in ));
    private static int rowSize;
    private static int colSize;
    private static char[][] forest;
    private static Garbage[][] garbageCount;
    private static int[] directionX = { 1, 0, -1, 0 };
    private static int[] directionY = { 0, 1, 0, -1 };
    private static Movement start;
    public static void main(String[] args) throws IOException {
        StringTokenizer st = new StringTokenizer(br.readLine());
        rowSize = Integer.parseInt(st.nextToken());
        colSize = Integer.parseInt(st.nextToken());
        initForest();
        bfs();
        Movement movement = outputEndPosition();
        System.out.println( garbageCount[movement.positionY][movement.positionX].garbageThrough + " " + garbageCount[movement.positionY][movement.positionX].garbageSide );
    }

    public static void initForest()throws IOException{
        forest = new char[rowSize][colSize];
        garbageCount = new Garbage[rowSize][colSize];
        for( int i=0; i<rowSize; i++ ){
            String st = br.readLine();
            for( int j=0; j<colSize; j++ ){
                forest[i][j] = st.charAt(j);
                garbageCount[i][j] = new Garbage();
            }
        }
        setGarbageSide();
//        print();
    }

    public static void print(){
        for( int i=0; i<rowSize; i++ ){
            for( int j=0; j<colSize; j++ ){
                System.out.print( forest[i][j] + " " );
            }
            System.out.println();
        }
        System.out.println();
    }

    public static void setGarbageSide(){
        for( int i=0; i<rowSize; i++ ){
            for( int j=0; j<colSize; j++ ){
                if( forest[i][j] == 'g' ){
                    for( int k=0; k<4; k++ ){
                        int nextX = j + directionX[k];
                        int nextY = i + directionY[k];
                        if( movable( nextX, nextY ) && forest[nextY][nextX] == '.' ){
                            forest[nextY][nextX] = 'G';
                        }
                    }
                }else if( forest[i][j] == 'S' ){
                    start = new Movement(j, i);
                }
            }
        }
    }

    public static boolean movable( int nextX, int nextY ){
        return( nextX>=0 && nextX<colSize && nextY>=0 && nextY<rowSize );
    }

    public static void bfs(){
        Queue<Movement> queue = new LinkedList<>();
        garbageCount[start.positionY][start.positionX].garbageThrough = 0;
        garbageCount[start.positionY][start.positionX].garbageSide = 0;
        queue.add( start );
        while(!queue.isEmpty()){
            Movement temp = queue.poll();
            int currentX = temp.positionX;
            int currentY = temp.positionY;
            for( int i=0; i<4; i++ ){
                int nextX = currentX + directionX[i];
                int nextY = currentY + directionY[i];
                if( !movable( nextX, nextY ) ) continue;
                if( forest[nextY][nextX] == 'g' ){
                    if (garbageCount[nextY][nextX].garbageThrough > garbageCount[currentY][currentX].garbageThrough + 1) {
                        garbageCount[nextY][nextX].garbageThrough = garbageCount[currentY][currentX].garbageThrough + 1;
                        garbageCount[nextY][nextX].garbageSide = garbageCount[currentY][currentX].garbageSide;
                        queue.add(new Movement(nextX, nextY));
                    }else if(garbageCount[nextY][nextX].garbageThrough == garbageCount[currentY][currentX].garbageThrough + 1){
                        if( garbageCount[nextY][nextX].garbageSide > garbageCount[currentY][currentX].garbageSide ){
                            garbageCount[nextY][nextX].garbageThrough = garbageCount[currentY][currentX].garbageThrough + 1;
                            garbageCount[nextY][nextX].garbageSide = garbageCount[currentY][currentX].garbageSide;
                            queue.add(new Movement(nextX, nextY));
                        }
                    }
                }else if( forest[nextY][nextX] == 'G' ){
                    if ( garbageCount[nextY][nextX].garbageThrough > garbageCount[currentY][currentX].garbageThrough ) {
                        garbageCount[nextY][nextX].garbageThrough = garbageCount[currentY][currentX].garbageThrough;
                        garbageCount[nextY][nextX].garbageSide = garbageCount[currentY][currentX].garbageSide + 1;
                        queue.add(new Movement(nextX, nextY));
                    }else if(garbageCount[nextY][nextX].garbageThrough == garbageCount[currentY][currentX].garbageThrough ){
                        if( garbageCount[nextY][nextX].garbageSide > garbageCount[currentY][currentX].garbageSide + 1 ){
                            garbageCount[nextY][nextX].garbageThrough = garbageCount[currentY][currentX].garbageThrough;
                            garbageCount[nextY][nextX].garbageSide = garbageCount[currentY][currentX].garbageSide + 1;
                            queue.add(new Movement(nextX, nextY));
                        }
                    }
                }else{
                    if (garbageCount[nextY][nextX].garbageThrough > garbageCount[currentY][currentX].garbageThrough ) {
                        garbageCount[nextY][nextX].garbageThrough = garbageCount[currentY][currentX].garbageThrough;
                        garbageCount[nextY][nextX].garbageSide = garbageCount[currentY][currentX].garbageSide;
                        queue.add(new Movement(nextX, nextY));
                    }else if(garbageCount[nextY][nextX].garbageThrough == garbageCount[currentY][currentX].garbageThrough ){
                        if( garbageCount[nextY][nextX].garbageSide > garbageCount[currentY][currentX].garbageSide ){
                            garbageCount[nextY][nextX].garbageThrough = garbageCount[currentY][currentX].garbageThrough;
                            garbageCount[nextY][nextX].garbageSide = garbageCount[currentY][currentX].garbageSide;
                            queue.add(new Movement(nextX, nextY));
                        }
                    }
                }
            }
        }
    }

    public static Movement outputEndPosition(){
        Movement temp = null;
        for( int i=0; i<rowSize; i++ ){
            for( int j=0; j<colSize; j++ ){
                if ( forest[i][j] == 'F' ) {
                    temp = new Movement( j, i );
                }
            }
        }
        return temp;
    }
}
