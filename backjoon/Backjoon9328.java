package algorithm.backjoon;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Backjoon9328 {
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
    private static char[][] building;
    private static boolean[][] visited;
    private static int buildingRow;
    private static int buildingCol;
    private static HashMap<String, String> hashMap;
    private static Queue<Person> queue;
    private static int[] directionX = { 1, 0, -1, 0 };
    private static int[] directionY = { 0, 1, 0, -1 };
    private static Queue<Person> impenetrableDoor;
    private static int totalDocument;

    public static void main(String[] args) throws IOException {
        testCase = Integer.parseInt(br.readLine());
        for( int i=0; i<testCase; i++ ){
            StringTokenizer st = new StringTokenizer(br.readLine());
            buildingRow = Integer.parseInt(st.nextToken());
            buildingCol = Integer.parseInt(st.nextToken());
            hashMap = new HashMap<>();
            impenetrableDoor = new LinkedList<>();
            queue = new LinkedList<>();
            totalDocument = 0;
            initBuilding();
            doCommand();
            findAccessibleDoor();
            System.out.println(totalDocument);
        }
    }

    public static void initBuilding() throws IOException{
        building = new char[buildingRow][buildingCol];
        visited = new boolean[buildingRow][buildingCol];
        for( int i=0; i<buildingRow; i++ ){
            String temp = br.readLine();
            for( int j=0; j<buildingCol; j++ ){
                building[i][j] = temp.charAt(j);
            }
        }
        String keyList = br.readLine();
        if( keyList.charAt(0) != '0' ){
            int size = keyList.length();
            for( int i=0; i<size; i++ ){
                char key = keyList.charAt(i);
                char door = (char) ( (int)key - 32 );
                hashMap.put( door + "", key + "" );
            }
        }
    }

    public static void doCommand(){
        for( int i=0; i<buildingRow; i++ ){
            if( i==0 || i==buildingRow-1 ){
                for( int j=0; j<buildingCol; j++ ){
                    checkEntrance( j, i );
                }
            }else{
                for( int j=0; j<buildingCol; j=j+(buildingCol-1) ){
                    checkEntrance( j, i );
                }
            }
        }
    }

    public static void checkEntrance( int x, int y ){
        if( building[y][x] == '.' ){
            bfs( x, y );
        }else if( building[y][x] >= 'A' && building[y][x] <= 'Z' ){
            if( hashMap.containsKey( building[y][x] + "" ) ){
                bfs( x, y );
            }else{
                impenetrableDoor.add( new Person( x, y ));
            }
        }else if( building[y][x] >= 'a' && building[y][x] <= 'z' ){
            char key = building[y][x];
            char door = (char) ( (int)key - 32 );
            hashMap.put( door + "", key + "" );
            bfs( x, y );
        }else if( !visited[y][x] && building[y][x] == '$' ){
            totalDocument++;
            bfs( x, y );
        }
    }

    public static void bfs( int x, int y ){
        visited[y][x] = true;
        queue.add( new Person( x, y ) );
        while(!queue.isEmpty()){
            Person temp = queue.poll();
            int currentX = temp.positionX;
            int currentY = temp.positionY;
            for( int i=0; i<4; i++ ){
               int nextX = currentX + directionX[i];
               int nextY = currentY + directionY[i];
               if( movable( nextX, nextY ) && !visited[nextY][nextX] && building[nextY][nextX] != '*' ){
                   if( building[nextY][nextX] >= 'A' && building[nextY][nextX] <= 'Z' ){
                       if( hashMap.containsKey( building[nextY][nextX] + "" ) ){
                           visited[nextY][nextX] = true;
                           queue.add( new Person( nextX, nextY ) );
                       }else{
                           impenetrableDoor.add( new Person( nextX, nextY ));
                       }
                   }else if( building[nextY][nextX] >= 'a' && building[nextY][nextX] <= 'z' ){
                       visited[nextY][nextX] = true;
                       char key = building[nextY][nextX];
                       char door = (char) ( (int)key - 32 );
                       hashMap.put( door + "", key + "" );
                       queue.add( new Person( nextX, nextY ) );
                   }else if( building[nextY][nextX] == '$' ){
                       visited[nextY][nextX] = true;
                       totalDocument++;
                       queue.add( new Person( nextX, nextY ) );
                   }else{
                       visited[nextY][nextX] = true;
                       queue.add( new Person( nextX, nextY ) );
                   }

               }
            }
        }
    }

    public static boolean movable( int nextX, int nextY ){
        return ( nextX>=0 && nextX<buildingCol && nextY>=0 && nextY<buildingRow );
    }

    public static void findAccessibleDoor(){
        while(true){
            Queue<Person> tempQueue = new LinkedList<>();
            int size = impenetrableDoor.size();
            while(!impenetrableDoor.isEmpty()){
                Person temp = impenetrableDoor.poll();
                int currentX = temp.positionX;
                int currentY = temp.positionY;
                if( hashMap.containsKey( building[currentY][currentX] + "" ) ){
                    bfs( currentX, currentY );
                }else{
                    tempQueue.add( temp );
                }
            }
            while(!tempQueue.isEmpty()){
                impenetrableDoor.add( tempQueue.poll() );
            }
            if( size == impenetrableDoor.size() ) break;
        }
    }

}
