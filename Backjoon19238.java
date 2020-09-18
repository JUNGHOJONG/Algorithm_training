package algorithm;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Backjoon19238 {
    public static class Movement{
        private int positionX;
        private int positionY;
        private int count;
        public Movement( int positionX, int positionY, int count ){
            this.positionX = positionX;
            this.positionY = positionY;
            this.count = count;
        }
    }
    public static class Passenger{
        private int index;
        private Movement startPoint;
        private Movement endPoint;
        public Passenger(int index, int sX, int sY, int eX, int eY){
            this.index = index;
            this.startPoint = new Movement(sX, sY, 0);
            this.endPoint = new Movement(eX, eY, 0);
        }
    }
    public static class Taxi{
        private Movement position;
        private int fuel;
        private boolean check = true;
        public Taxi(int x, int y, int fuel){
            this.position = new Movement(x, y, 0);
            this.fuel = fuel;
        }
        public boolean movableTaxi(int distance){
            return fuel >= distance;
        }
        public void updateInfo(int distance, int x, int y){
            fuel += distance;
            this.position.positionX = x;
            this.position.positionY = y;
        }
        public void stopTaxi(){
            fuel = -1;
            check = false;
        }
    }
    private static BufferedReader br = new BufferedReader( new InputStreamReader( System.in ) );
    private static ArrayList<Passenger> passengerManagement;
    private static int[] directionX = { 1, 0, -1, 0 };
    private static int[] directionY = { 0, 1, 0, -1 };
    private static int roadSize;
    private static int passengerInfo;
    private static Taxi taxi;
    private static int[][] road;
    private static int[][] copyRoad;
    public static void main(String[] args) throws IOException {
        StringTokenizer st = new StringTokenizer( br.readLine() );
        passengerManagement = new ArrayList<>();
        roadSize = Integer.parseInt( st.nextToken() );
        passengerInfo = Integer.parseInt( st.nextToken() );
        int fuel = Integer.parseInt(st.nextToken());
        initRoad();
        initTaxi( fuel );
        initPassenger();
        doCommand();
        System.out.println( taxi.fuel );
    }

    public static void initRoad() throws IOException{
        road = new int[roadSize][roadSize];
        copyRoad = new int[roadSize][roadSize];
        for( int i=0; i<roadSize; i++ ){
            StringTokenizer st = new StringTokenizer( br.readLine() );
            for( int j=0; j<roadSize; j++ ){
                road[i][j] = Integer.parseInt( st.nextToken() );
                copyRoad[i][j] = road[i][j];
            }
        }
    }

    public static void initTaxi(int fuel) throws IOException{
        StringTokenizer st = new StringTokenizer( br.readLine() );
        int y = Integer.parseInt(st.nextToken())-1;
        int x = Integer.parseInt(st.nextToken())-1;
        taxi = new Taxi(x, y, fuel);
    }

    public static void initPassenger() throws IOException{
        for( int i=0; i<passengerInfo; i++ ){
            StringTokenizer st = new StringTokenizer( br.readLine() );
            int startY = Integer.parseInt(st.nextToken())-1;
            int startX = Integer.parseInt(st.nextToken())-1;
            int endY = Integer.parseInt(st.nextToken())-1;
            int endX = Integer.parseInt(st.nextToken())-1;
            passengerManagement.add(new Passenger(i+1, startX, startY, endX, endY));
        }
    }

    public static void doCommand(){
        while(taxi.check){
            int index = startBfs();
            if( !taxi.check ) break;
            endBfs( index );
        }
    }

    public static int startBfs(){
        Queue<Movement> queue = new LinkedList<>();
        boolean[][] visited = new boolean[roadSize][roadSize];
        visited[taxi.position.positionY][taxi.position.positionX] = true;
        queue.add( new Movement( taxi.position.positionX, taxi.position.positionY, 0 ) );
        while(!queue.isEmpty()){
            Movement temp = queue.poll();
            int currentX = temp.positionX;
            int currentY = temp.positionY;
            int currentCount = temp.count;
            for( int i=0; i<4; i++ ){
                int nextX = currentX + directionX[i];
                int nextY = currentY + directionY[i];
                if( movable( nextX, nextY ) && !visited[nextY][nextX] ){
                    visited[nextY][nextX] = true;
                    road[nextY][nextX] = currentCount + 1;
                    queue.add( new Movement( nextX, nextY, currentCount + 1 ) );
                }
            }
        }
        if(!isMovableAllPassenger(visited)){
            taxi.stopTaxi();
            return 0;
        }
        int distance = getShortestDistance(visited);
        int index = getSelectedStartPassenger( distance );
        resetRoad();
        return index;
    }

    public static boolean movable( int nextX, int nextY ){
        return( nextX>=0 && nextX<roadSize && nextY>=0
                    && nextY<roadSize && road[nextY][nextX] == 0 );
    }

    public static boolean isMovableAllPassenger(boolean[][] visited){
        for (Passenger passenger : passengerManagement) {
            Movement temp = passenger.startPoint;
            if (!visited[temp.positionY][temp.positionX]) {
                return false;
            }
        }
        return true;
    }

    public static int getShortestDistance(boolean[][] visited){
        int distance = Integer.MAX_VALUE;
        for (Passenger passenger : passengerManagement) {
            Movement temp = passenger.startPoint;
            if (visited[temp.positionY][temp.positionX])
                distance = Math.min(distance, road[temp.positionY][temp.positionX]); // 승객1의 도착지와 승객2의 출발지가 같은 경우 반례
        }
        return distance;
    }

    public static void resetRoad(){
        for( int i=0; i<roadSize; i++ ){
            System.arraycopy(copyRoad[i], 0, road[i], 0, roadSize);
        }
    }

    public static int getSelectedStartPassenger( int distance ){
        int x = roadSize; // roadSize -1 로 하면 런타임에러(주의)
        int y = roadSize;
        int index = 0;
        for (Passenger passenger : passengerManagement) {
            Movement temp = passenger.startPoint;
            if (road[temp.positionY][temp.positionX] == distance) {
                if (y > temp.positionY
                        || (y == temp.positionY && x > temp.positionX)) {
                    x = temp.positionX;
                    y = temp.positionY;
                    index = passenger.index;
                }
            }
        }
        updateFuel( x, y, distance, 0 );
        return index;
    }

    public static void updateFuel( int x, int y, int distance, int kind ){
        int startPoint = 0;
        if( kind == startPoint ) distance = -distance;
        if(taxi.movableTaxi(distance)) {
            taxi.updateInfo(distance, x, y);
            return;
        }
        taxi.stopTaxi();
    }

    public static void endBfs( int index ){
        Queue<Movement> queue = new LinkedList<>();
        boolean[][] visited = new boolean[roadSize][roadSize];
        visited[taxi.position.positionY][taxi.position.positionX] = true;
        queue.add( new Movement( taxi.position.positionX, taxi.position.positionY, 0 ) );
        while(!queue.isEmpty()){
            Movement temp = queue.poll();
            int currentX = temp.positionX;
            int currentY = temp.positionY;
            int currentCount = temp.count;
            for( int i=0; i<4; i++ ){
                int nextX = currentX + directionX[i];
                int nextY = currentY + directionY[i];
                if( movable( nextX, nextY ) && !visited[nextY][nextX] ){
                    visited[nextY][nextX] = true;
                    road[nextY][nextX] = currentCount + 1;
                    queue.add( new Movement( nextX, nextY, currentCount + 1 ) );
                }
            }
        }
        Movement temp = getEndPoint(index);
        assert temp != null;
        int distance = road[temp.positionY][temp.positionX];
        if( distance == 0 ) { // 도착지로 갈 수 없을 때
            taxi.stopTaxi();
            return;
        }
        updateFuel( temp.positionX, temp.positionY, distance, 1 );
        removePassenger(index);
        if( passengerManagement.size() == 0 ) {
            taxi.check = false;
            return;
        }
        resetRoad();
    }

    public static Movement getEndPoint(int index){
        for(Passenger passenger : passengerManagement){
            if(passenger.index == index) return passenger.endPoint;
        }
        return null;
    }

    public static void removePassenger(int index){
        passengerManagement.removeIf(passenger -> passenger.index == index);
    }

}
