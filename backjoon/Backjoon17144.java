package algorithm.backjoon;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Backjoon17144 {
    public static class AirPurifier{
        private int positionX;
        private int positionY;
        public AirPurifier( int positionX, int positionY ){
            this.positionX = positionX;
            this.positionY = positionY;
        }
    }
    private static BufferedReader br = new BufferedReader( new InputStreamReader( System.in ));
    private static int roomRow;
    private static int roomCol;
    private static int elapsedTime;
    private static int[][] room;
    private static AirPurifier upAirPurifier;
    private static AirPurifier downAirPurifier;
    private static int[] directionX = { 1, 0, -1, 0 };
    private static int[] directionY = { 0, 1, 0, -1 };
    public static void main(String[] args) throws IOException {
        StringTokenizer st = new StringTokenizer(br.readLine());
        roomRow = Integer.parseInt(st.nextToken());
        roomCol = Integer.parseInt(st.nextToken());
        elapsedTime = Integer.parseInt(st.nextToken());
        initRoom();
        for( int i=0; i<elapsedTime; i++ ){
            spreadFineDust();
            operateUpAirPurifier();
            operateDownAirPurifier();
        }
        int output = countAllFineDust();
        System.out.println( output );
    }

    public static void initRoom() throws IOException{
        room = new int[roomRow][roomCol];
        int index = 0;
        for( int i=0; i<roomRow; i++ ){
            StringTokenizer st = new StringTokenizer(br.readLine());
            for( int j=0; j<roomCol; j++ ){
                room[i][j] = Integer.parseInt(st.nextToken());
                if( room[i][j] == -1 && index == 0 ){
                    upAirPurifier = new AirPurifier( j, i );
                    index++;
                }else if( room[i][j] == -1 && index == 1 ){
                    downAirPurifier = new AirPurifier( j, i );
                }
            }
        }
    }

    public static void spreadFineDust(){
        int[][] copyRoom = new int[roomRow][roomCol];
        for( int i=0; i<roomRow; i++ ){
            for( int j=0; j<roomCol; j++ ){
                if( room[i][j] != -1 ){
                    int spreadCount = 0;
                    int spreadAmount = room[i][j] / 5;
                    for( int k=0; k<4; k++ ){
                        int nextX = j + directionX[k];
                        int nextY = i + directionY[k];
                        if( movable( nextX, nextY ) && room[nextY][nextX] != -1 ){
                            copyRoom[nextY][nextX] += spreadAmount;
                            spreadCount++;
                        }
                    }
                    copyRoom[i][j] += ( room[i][j] - spreadAmount * spreadCount );
                }
            }
        }
        copyFromCopyRoomToRoom( copyRoom );
    }

    public static boolean movable( int nextX, int nextY ){
        return ( nextX>=0 && nextX<roomCol && nextY>=0 && nextY<roomRow );
    }

    public static void copyFromCopyRoomToRoom( int[][] copyRoom ){
        for( int i=0; i<roomRow; i++ ){
            for( int j=0; j<roomCol; j++ ){
                if( room[i][j] != -1 ){
                    room[i][j] = copyRoom[i][j];
                }
            }
        }
    }

    public static void operateUpAirPurifier(){
        downOperation( 0, upAirPurifier.positionY-1, 0 );
        leftOperation( 0 );
        upOperation( roomCol-1, 0, upAirPurifier.positionY );
        rightOperation( upAirPurifier.positionY );
    }

    public static void operateDownAirPurifier(){
        upOperation( 0, downAirPurifier.positionY+1, roomRow-1 );
        leftOperation( roomRow-1 );
        downOperation( roomCol-1, roomRow-1, downAirPurifier.positionY );
        rightOperation( downAirPurifier.positionY );
    }

    public static void rightOperation( int row ){
        for( int x=roomCol-2; x>=1; x-- ){
            room[row][x+1] = room[row][x];
            room[row][x] = 0;
        }
    }

    public static void leftOperation( int row ){
        for( int x=1; x<=roomCol-1; x++ ){
            room[row][x-1] = room[row][x];
            room[row][x] = 0;
        }
    }

    public static void upOperation( int col, int start, int end ){
        for( int y=start+1; y<=end; y++ ){
            room[y-1][col] = room[y][col];
            room[y][col] = 0;
        }
    }

    public static void downOperation( int col, int start, int end ){
        for( int y=start-1; y>=end; y-- ){
            room[y+1][col] = room[y][col];
            room[y][col] = 0;
        }
    }

    public static int countAllFineDust(){
        int allFineDust = 0;
        for( int i=0; i<roomRow; i++ ){
            for( int j=0; j<roomCol; j++ ){
                if( room[i][j] != -1 ){
                    allFineDust += room[i][j];
                }
            }
        }
        return allFineDust;
    }
}
