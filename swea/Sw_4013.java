package algorithm.swea;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Sw_4013 {
    private static BufferedReader br = new BufferedReader( new InputStreamReader( System.in ));
    private static int testCase;
    private static int rotationCount;
    private static int[][] cogwheel;
    private static int[] rotation;

    public static void main(String[] args) throws IOException {
        testCase = Integer.parseInt(br.readLine());
        for( int i=0; i<testCase; i++ ){
            rotationCount = Integer.parseInt(br.readLine());
            initCogwheel();
            doCommand();
            int output = getScore();
            System.out.println( "#" + (i+1) + " " + output );
        }
    }

    public static void initCogwheel() throws IOException{
        cogwheel = new int[4][8];
        for( int i=0; i<4; i++ ){
            StringTokenizer st = new StringTokenizer(br.readLine());
            for( int j=0; j<8; j++ ){
                cogwheel[i][j] = Integer.parseInt(st.nextToken());
            }
        }
    }

    public static void doCommand() throws IOException{
        for( int i=0; i<rotationCount; i++  ){
            StringTokenizer st = new StringTokenizer(br.readLine());
            int wheelIndex = Integer.parseInt(st.nextToken())-1;
            int wheelRotation = Integer.parseInt(st.nextToken());
            selectRotationableWheel( wheelIndex, wheelRotation );
        }
    }

    public static void selectRotationableWheel( int wheelIndex, int wheelRotation ){
        rotation = new int[4];
        rotation[wheelIndex] = wheelRotation;
        int startIndex = wheelIndex, endIndex = wheelIndex;;
        while( startIndex >= 1 && cogwheel[startIndex][6] != cogwheel[startIndex-1][2] ){
            startIndex--;
            rotation[startIndex] = rotation[startIndex+1] * -1;
        }
        while( endIndex <= rotation.length-2 && cogwheel[endIndex][2] != cogwheel[endIndex+1][6] ){
            endIndex++;
            rotation[endIndex] = rotation[endIndex-1] * -1;
        }
        rotateWheel( startIndex, endIndex );
    }

    public static void rotateWheel( int startIndex, int endIndex ){
        for( int i=startIndex; i<=endIndex; i++ ){
            int clockwise = 1 , antiClockwise = -1;
            if( rotation[i] == clockwise ){
                rotateClockwise( i );
            }else if( rotation[i] == antiClockwise ) {
                rotateAntiClockwise( i );
            }
        }
    }

    public static void rotateClockwise( int wheelIndex ){
        int size = cogwheel[0].length;
        int temp = cogwheel[wheelIndex][size-1];
        for( int i=size-2; i>=0; i-- ){
            cogwheel[wheelIndex][i+1] = cogwheel[wheelIndex][i];
        }
        cogwheel[wheelIndex][0] = temp;
    }

    public static void rotateAntiClockwise( int wheelIndex ){
        int size = cogwheel[0].length;
        int temp = cogwheel[wheelIndex][0];
        for( int i=1; i<size; i++ ){
            cogwheel[wheelIndex][i-1] = cogwheel[wheelIndex][i];
        }
        cogwheel[wheelIndex][size-1] = temp;
    }

    public static int getScore(){
        int sum = 0, sPole = 1;
        for( int i=0; i<4; i++ ){
            if( i == 0 && cogwheel[i][0] == sPole ) sum += 1;
            else if( i == 1 && cogwheel[i][0] == sPole ) sum += 2;
            else if( i == 2 && cogwheel[i][0] == sPole ) sum += 4;
            else if( i == 3 && cogwheel[i][0] == sPole ) sum += 8;
        }
        return sum;
    }
}

