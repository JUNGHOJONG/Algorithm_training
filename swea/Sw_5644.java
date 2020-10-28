package algorithm.swea;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Sw_5644 {
    public static class Person{
        private int positionX;
        private int positionY;
        public Person( int positionX, int positionY ){
            this.positionX = positionX;
            this.positionY = positionY;
        }
    }
    private static BufferedReader br = new BufferedReader( new InputStreamReader( System.in ));
    private static int testCase;
    private static int totalTime;
    private static int wifiCount;
    private static int totalPower;
    private static ArrayList<Integer>[][] map;
    private static int[] wifiPower;
    private static Queue<Person> personA = new LinkedList<>();
    private static Queue<Person> personB = new LinkedList<>();
    private static ArrayList<Integer> usableWifiOfPersonA = new ArrayList<>();
    private static ArrayList<Integer> usableWifiOfPersonB = new ArrayList<>();
    private static int[] directionX = { 0, 0, 1, 0, -1 };
    private static int[] directionY = { 0, -1, 0, 1, 0 };

    public static void main(String[] args) throws IOException {
        testCase = Integer.parseInt(br.readLine());
        for( int i=0; i<testCase; i++ ){
            StringTokenizer st = new StringTokenizer(br.readLine());
            totalTime = Integer.parseInt(st.nextToken());
            wifiCount = Integer.parseInt(st.nextToken());
            totalPower = 0;
            saveUserPosition();
            initMap();
            doCommand();
            System.out.println( "#" + (i+1) + " " + totalPower );
        }
    }

    public static void saveUserPosition() throws IOException{
        for( int i=0; i<2; i++ ){
            StringTokenizer st = new StringTokenizer(br.readLine());
            int x, y;
            if( i == 0 ) {
                x = 0;
                y = 0;
                personA.add( new Person( x, y ) );
            }else{
                x = 9;
                y = 9;
                personB.add( new Person( x, y ) );
            }
            for( int j=0; j<totalTime; j++ ){
                int direction = Integer.parseInt(st.nextToken());
                int nextX = x + directionX[direction];
                int nextY = y + directionY[direction];
                if( i == 0 ) personA.add( new Person( nextX, nextY ));
                else if( i == 1 ) personB.add( new Person( nextX, nextY ));
                x = nextX;
                y = nextY;
            }
        }
    }

    public static void initMap() throws IOException{
        map = new ArrayList[10][10];
        wifiPower = new int[wifiCount+1];
        for( int i=0; i<10; i++ ){
            for( int j=0; j<10; j++ ){
                map[i][j] = new ArrayList<>();
                map[i][j].add( 0 );
            }
        }
        for( int i=0; i<wifiCount; i++ ){
            StringTokenizer st = new StringTokenizer(br.readLine());
            int wifiX = Integer.parseInt(st.nextToken())-1;
            int wifiY = Integer.parseInt(st.nextToken())-1;
            int Arrange = Integer.parseInt(st.nextToken());
            int Power = Integer.parseInt(st.nextToken());
            installWifi( wifiX, wifiY, Arrange, i+1 );
            wifiPower[i+1] = Power;
        }
    }

    public static void installWifi( int wifiX, int wifiY, int wifiArrange, int wifiNumber ){
        int y = 0;
        for( int j = wifiX-wifiArrange; j <= wifiX+wifiArrange; j++ ){
            for( int i = wifiY-y; i <= wifiY+y; i++ ){
                if( movable( j, i ) ) map[i][j].add( wifiNumber );
            }
            if( j < wifiX ) y++;
            else y--;
        }
    }

    public static boolean movable( int nextX, int nextY ){
        return ( nextX>=0 && nextX<10 && nextY>=0 && nextY<10 );
    }

    public static void doCommand(){
        for( int i=0; i<=totalTime; i++ ){
            Person tempA = personA.poll();
            for( Integer n : map[tempA.positionY][tempA.positionX] ){
                usableWifiOfPersonA.add( n );
            }
            Person tempB = personB.poll();
            for( Integer n : map[tempB.positionY][tempB.positionX] ){
                usableWifiOfPersonB.add( n );
            }
            getWifiPower();
            usableWifiOfPersonA.clear();
            usableWifiOfPersonB.clear();
        }
    }

    public static void getWifiPower(){
        int sizeA = usableWifiOfPersonA.size();
        int sizeB = usableWifiOfPersonB.size();
        int maxPower = 0;
        for( int i=0; i<sizeA; i++ ){
            int numberOfwifiA = usableWifiOfPersonA.get(i);
            for( int j=0; j<sizeB; j++ ){
                int numberOfwifiB = usableWifiOfPersonB.get(j);
                if( numberOfwifiA == numberOfwifiB ){
                    int value = ( wifiPower[numberOfwifiA] + wifiPower[numberOfwifiB] ) / 2;
                    maxPower = Math.max( maxPower, value );
                }else{
                    int value = wifiPower[numberOfwifiA] + wifiPower[numberOfwifiB];
                    maxPower = Math.max( maxPower, value );
                }
            }
        }
        totalPower += maxPower;
    }
}
