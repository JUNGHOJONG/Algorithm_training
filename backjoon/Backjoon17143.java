package algorithm.backjoon;

import java.io.*;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class Backjoon17143 {
    public static class Shark{
        private int positionY;
        private int positionX;
        private int speed;
        private int direction;
        private int size;
        private int time;
        public Shark( int positionY, int positionX, int speed, int direction, int size, int time ){
            this.positionY = positionY;
            this.positionX = positionX;
            this.speed = speed;
            this.direction = direction;
            this.size = size;
            this.time = time;
        }
    }
    private static BufferedReader br = new BufferedReader( new InputStreamReader( System.in ) );
    private static BufferedWriter bw = new BufferedWriter( new OutputStreamWriter( System.out ) );
    private static ArrayList<Shark>[][] fishingMarket;
    private static int fishingMarketRow;
    private static int fishingMarketCol;
    private static int totalShark;
    private static int totalSize;
    private static int[] directionX = { 0, 0, 1, -1 };
    private static int[] directionY = { -1, 1, 0, 0 };

    public static void main(String[] args) throws IOException {
        StringTokenizer st = new StringTokenizer( br.readLine() );
        fishingMarketRow = Integer.parseInt(st.nextToken());
        fishingMarketCol = Integer.parseInt(st.nextToken());
        totalShark = Integer.parseInt(st.nextToken());
        initFishingMarket();
        for( int time=1; time<=fishingMarketCol; time++ ){
            fishShark( time );
            moveAllShark( time );
            removeSamePositionShark();
        }
        bw.write( totalSize + "\n" );
        bw.flush();
        bw.close();
    }

    public static void initFishingMarket() throws IOException{
        fishingMarket = new ArrayList[fishingMarketRow+1][fishingMarketCol+1];
        for( int i=1; i<=fishingMarketRow; i++ ){
            for( int j=1; j<=fishingMarketCol; j++ ){
                fishingMarket[i][j] = new ArrayList<Shark>();
            }
        }
        for( int i=0; i<totalShark; i++ ){
            StringTokenizer st = new StringTokenizer(br.readLine());
            int row = Integer.parseInt(st.nextToken());
            int col = Integer.parseInt(st.nextToken());
            int speed = Integer.parseInt(st.nextToken());
            int direction = Integer.parseInt(st.nextToken());
            int size = Integer.parseInt(st.nextToken());
            fishingMarket[row][col].add( new Shark(row, col, speed, direction-1, size, 1) );
        }
    }

    public static void fishShark( int time ){
        for( int i=1; i<=fishingMarketRow; i++ ){
            if( fishingMarket[i][time].size() != 0 ){
                Shark temp = fishingMarket[i][time].get(0);
                totalSize += temp.size;
                fishingMarket[i][time].remove(0);
                break;
            }
        }
    }

    public static void moveAllShark( int time ){
        for( int i=1; i<=fishingMarketRow; i++ ){
            for( int j=1; j<=fishingMarketCol; j++ ){
                if( fishingMarket[i][j].size() != 0 ){
                    moveShark( j, i, time );
                }
            }
        }
    }

    public static void moveShark( int x, int y, int time ){
        int size = fishingMarket[y][x].size();
        for( int i=size-1; i>=0; i-- ){
            if( fishingMarket[y][x].get(i).time != time + 1 ){
                Shark temp = fishingMarket[y][x].get(i);
                move( temp );
                fishingMarket[y][x].remove(i);
            }
        }
    }

    public static void move( Shark temp ){
        int currentX = temp.positionX;
        int currentY = temp.positionY;
        int direction = temp.direction;
        int tempSpeed = temp.speed;
        int size = temp.size;
        int time = temp.time;
        if( direction == 2 || direction == 3 ){
            tempSpeed = tempSpeed % ( 2 * fishingMarketCol - 2 );
        }else {
            tempSpeed = tempSpeed % ( 2 * fishingMarketRow - 2 );
        }
        int nextX = currentX, nextY = currentY;
        if( tempSpeed == 0 ) fishingMarket[nextY][nextX].add( new Shark( nextY, nextX, temp.speed, direction, size, time + 1) );
        for( int i=0; i<tempSpeed; i++ ){
            nextX += directionX[direction];
            nextY += directionY[direction];
            if( !movable( nextX, nextY ) ){
                nextX -= directionX[direction];
                nextY -= directionY[direction];
                direction = changeDirection(direction);
                nextX += directionX[direction];
                nextY += directionY[direction];
            }
            if( i == tempSpeed - 1 ) fishingMarket[nextY][nextX].add( new Shark( nextY, nextX, temp.speed, direction, size, time + 1) );
        }
    }

    public static boolean movable( int nextX, int nextY ){
        return ( nextX>0 && nextX<=fishingMarketCol && nextY>0 && nextY<=fishingMarketRow );
    }

    public static int changeDirection( int direction ){
        if( direction == 0 ){
            return 1;
        }else if( direction == 1 ){
            return 0;
        }else if( direction == 2 ){
            return 3;
        }else{
            return 2;
        }
    }

    public static void removeSamePositionShark(){
        for( int i=1; i<=fishingMarketRow; i++ ){
            for( int j=1; j<=fishingMarketCol; j++ ){
                if( fishingMarket[i][j].size() > 1 ){
                    int bigestSize = getBigestShark( j, i );
                    removeShark( j, i, bigestSize );
                }
            }
        }
    }

    public static int getBigestShark( int x, int y ){
        int size = fishingMarket[y][x].size();
        int temp = 0;
        for( int i=size-1; i>=0; i-- ){
            temp = Math.max(temp, fishingMarket[y][x].get(i).size);
        }
        return temp;
    }

    public static void removeShark( int x, int y, int bigestSize ){
        int size = fishingMarket[y][x].size();
        for( int i=size-1; i>=0; i-- ){
            if( fishingMarket[y][x].get(i).size != bigestSize ){
                fishingMarket[y][x].remove(i);
            }
        }
    }
}
