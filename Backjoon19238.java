package algorithm;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Backjoon19238 {
    public static class Movement{
        private int positionX;
        private int positionY;
        private int count = 0;
        public Movement(int positionX, int positionY){
            this.positionX = positionX;
            this.positionY = positionY;
        }
        public static Movement createMovement(int x, int y){
            return new Movement(x, y);
        }
        public static boolean isMovable(int x, int y){
            return (x >= 0 && x < roadSize && y >= 0 && y < roadSize
                    && road[y][x] == 0);
        }
        public void increaseDepth(Movement movement){
            count += (movement.count + 1);
        }
    }
    public static class Passenger implements Comparable<Passenger>{
        private Movement startPoint;
        private Movement endPoint;
        private int distance;
        public Passenger(int sX, int sY, int eX, int eY, int distance){
            this.startPoint = Movement.createMovement(sX, sY);
            this.endPoint = Movement.createMovement(eX, eY);
            this.distance = distance;
        }
        public void setDistance( int distance ){
            this.distance = distance;
        }
        @Override
        public int compareTo(Passenger o) {
            if( this.distance == o.distance ){
                if(this.startPoint.positionY == o.startPoint.positionY){
                    return this.startPoint.positionX - o.startPoint.positionX;
                }
                return this.startPoint.positionY - o.startPoint.positionY;
            }
            return this.distance - o.distance;
        }
    }
    public static class Taxi{
        private Movement position;
        private int fuel;
        private boolean check = true;
        public Taxi(int x, int y, int fuel){
            this.position = new Movement(x, y);
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
    private static HashMap<Integer, Passenger> passengerManagement;
    private static PriorityQueue<Passenger> priorityQueue;
    private static int[] directionX = { 1, 0, -1, 0 };
    private static int[] directionY = { 0, 1, 0, -1 };
    private static int[][] startPoint;
    private static int roadSize;
    private static int passengerInfo;
    private static Taxi taxi;
    private static int[][] road;
    private static int[][] copyRoad;
    public static void main(String[] args) throws IOException {
        StringTokenizer st = new StringTokenizer( br.readLine() );
        passengerManagement = new HashMap<>();
        priorityQueue = new PriorityQueue<>();
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
        startPoint = new int[roadSize][roadSize];
        for( int i=0; i<passengerInfo; i++ ){
            StringTokenizer st = new StringTokenizer( br.readLine() );
            int startY = Integer.parseInt(st.nextToken())-1;
            int startX = Integer.parseInt(st.nextToken())-1;
            int endY = Integer.parseInt(st.nextToken())-1;
            int endX = Integer.parseInt(st.nextToken())-1;
            startPoint[startY][startX] = i+1;
            passengerManagement.put(i+1, new Passenger(startX, startY, endX, endY, 0));
        }
    }

    public static void doCommand(){
        while(true){
            startBfs(); // distance 반환
            if(priorityQueue.size() != passengerInfo) { // 해당되는 index 반환
                taxi.stopTaxi();
                break;
            }
            Passenger temp = priorityQueue.poll();
            assert temp != null;
            updateFuel(temp.startPoint.positionX, temp.startPoint.positionY, temp.distance, 0);
            if( !taxi.check ) break;
            resetRoad();

            int distance = endBfs(temp);
            if(distance == -1){
                taxi.stopTaxi();
                break;
            }
            updateFuel(temp.endPoint.positionX, temp.endPoint.positionY, distance, 1);
            if( !taxi.check ) break;
            passengerManagement.remove(
                    startPoint[temp.startPoint.positionY][temp.startPoint.positionX]);
            startPoint[temp.startPoint.positionY][temp.startPoint.positionX] = 0;
            resetRoad();
            if (passengerManagement.size() == 0) {
                taxi.check = false;
                break;
            }
            priorityQueue.clear();
            passengerInfo--;
        }
    }

    public static void startBfs(){
        Queue<Movement> queue = new LinkedList<>();
        boolean[][] visited = new boolean[roadSize][roadSize];
        visited[taxi.position.positionY][taxi.position.positionX] = true;
        queue.add(new Movement(taxi.position.positionX, taxi.position.positionY));
        if (startPoint[taxi.position.positionY][taxi.position.positionX] != 0) { // 예외
            Passenger passenger = passengerManagement.get(
                    startPoint[taxi.position.positionY][taxi.position.positionX]);
            passenger.setDistance(0);
            priorityQueue.add(passenger);
        }
        while(!queue.isEmpty()){
            Movement temp = queue.poll();
            for( int i=0; i<4; i++ ){
                int nextX = temp.positionX + directionX[i];
                int nextY = temp.positionY + directionY[i];
                if (!Movement.isMovable(nextX, nextY) || visited[nextY][nextX]) continue;
                visited[nextY][nextX] = true;
                Movement next = Movement.createMovement(nextX, nextY);
                next.increaseDepth(temp);
                road[nextY][nextX] = next.count;
                queue.add(next);
                if (startPoint[nextY][nextX] != 0) {
                    Passenger passenger = passengerManagement.get(startPoint[nextY][nextX]);
                    passenger.setDistance(next.count);
                    priorityQueue.add(passenger);
                }
            }
        }
    }

    public static void resetRoad(){
        for( int i=0; i<roadSize; i++ ){
            System.arraycopy(copyRoad[i], 0, road[i], 0, roadSize);
        }
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

    public static int endBfs( Passenger passenger ){
        Queue<Movement> queue = new LinkedList<>();
        boolean[][] visited = new boolean[roadSize][roadSize];
        visited[passenger.startPoint.positionY][passenger.startPoint.positionX] = true;
        queue.add(new Movement(passenger.startPoint.positionX, passenger.startPoint.positionY));
        while(!queue.isEmpty()){
            Movement temp = queue.poll();
            for( int i=0; i<4; i++ ){
                int nextX = temp.positionX + directionX[i];
                int nextY = temp.positionY + directionY[i];
                if (!Movement.isMovable(nextX, nextY) || visited[nextY][nextX]) continue;
                visited[nextY][nextX] = true;
                Movement next = Movement.createMovement(nextX, nextY);
                next.increaseDepth(temp);
                road[nextY][nextX] = next.count;
                queue.add(next);
            }
        }
        if(visited[passenger.endPoint.positionY][passenger.endPoint.positionX]){
            return road[passenger.endPoint.positionY][passenger.endPoint.positionX];
        }
        return -1;
    }

}
