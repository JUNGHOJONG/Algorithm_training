package algorithm.backjoon;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Backjoon1194 {
    public static class Person{
        private int positionX;
        private int positionY;
        private int count;
        private HashSet<String> keyList;
        public Person( int x, int y, int count, HashSet<String> list ){
            this.positionX = x;
            this.positionY = y;
            this.count = count;
            keyList = list;
        }
    }
    private static BufferedReader br = new BufferedReader( new InputStreamReader( System.in ));
    private static int mazeRowSize;
    private static int mazeColSize;
    private static char[][] maze;
    private static boolean[][] visited;
    private static Queue<Person> startList = new LinkedList<>();
    private static int[] directionX = { 1, 0, -1, 0 };
    private static int[] directionY = { 0, 1, 0, -1 };
    private static boolean check = false;
    private static int minDistance = Integer.MAX_VALUE;

    public static void main(String[] args) throws IOException {
        StringTokenizer st = new StringTokenizer(br.readLine());
        mazeRowSize = Integer.parseInt(st.nextToken());
        mazeColSize = Integer.parseInt(st.nextToken());
        initMaze();
        bfs();
//        System.out.println("first: " + check);
        findEscapeArea();
        System.out.println( check ? minDistance : -1 );
    }

    public static void initMaze() throws IOException{
        maze = new char[mazeRowSize][mazeColSize];
        visited = new boolean[mazeRowSize][mazeColSize];
        for( int i=0; i<mazeRowSize; i++ ){
            String temp = br.readLine();
            for( int j=0; j<mazeColSize; j++ ){
                maze[i][j] = temp.charAt(j);
                if( maze[i][j] == '0' ){
                    HashSet<String> tempSet = new HashSet<>();
                    startList.add( new Person( j, i, 0, tempSet ) );
                }
            }
        }
    }

    public static void bfs() {
        Queue<Person> queue = new LinkedList<>();
        Person temp = startList.poll();
        visited[temp.positionY][temp.positionX] = true;
        queue.add(temp);
        while (!queue.isEmpty()) {
            Person person = queue.poll();
            int currentX = person.positionX;
            int currentY = person.positionY;
            int currentCount = person.count;
//            System.out.println( "currentX: " + currentX + " currentY: " + currentY );
            HashSet<String> currentKeyList = person.keyList;
            if (maze[currentY][currentX] == '1') {
                check = true;
                minDistance = currentCount;
                break;
            }
            for (int i = 0; i < 4; i++) {
                int nextX = currentX + directionX[i];
                int nextY = currentY + directionY[i];
                if ( !movable( nextX, nextY ) || maze[nextY][nextX] == '#' || visited[nextY][nextX] ) continue;
                if (maze[nextY][nextX] >= 'a' && maze[nextY][nextX] <= 'z') {
                    if (!currentKeyList.contains(maze[nextY][nextX] + "")) {
                        HashSet<String> tempKeyList = new HashSet<>();
                        Iterator<String> iterator = currentKeyList.iterator();
                        while(iterator.hasNext()){
                            tempKeyList.add(iterator.next());
                        }
                        tempKeyList.add( maze[nextY][nextX] + "" );
                        startList.add(new Person(nextX, nextY, currentCount + 1, tempKeyList));
                        visited[nextY][nextX] = true;
                        queue.add(new Person(nextX, nextY, currentCount + 1, currentKeyList));
                    } else {
                        visited[nextY][nextX] = true;
                        queue.add(new Person(nextX, nextY, currentCount + 1, currentKeyList));
                    }
                } else if (maze[nextY][nextX] >= 'A' && maze[nextY][nextX] <= 'Z') {
                    char tempKey = (char) ( maze[nextY][nextX] + 32 );
                    if (currentKeyList.contains( tempKey + "" )) {
//                        System.out.println("@@: " + currentX + " " + currentY );
                        visited[nextY][nextX] = true;
                        queue.add(new Person(nextX, nextY, currentCount + 1, currentKeyList));
                    }
                } else { // . Or O
                    visited[nextY][nextX] = true;
                    queue.add(new Person(nextX, nextY, currentCount + 1, currentKeyList));
                }
            }
        }
    }

    public static boolean movable( int nextX, int nextY ){
        return ( nextX>=0 && nextX<mazeColSize && nextY>=0 && nextY<mazeRowSize );
    }

    public static void findEscapeArea(){
        while(true){
//            System.out.println( "size: " + startList.size() + " check: " + check );
            if( startList.size() == 0 || check ) break;
            Iterator<Person> iterator = startList.iterator();
            int maxSize = 0;
            while(iterator.hasNext()){
                int tempSize = iterator.next().keyList.size();
                if( tempSize > maxSize ){
//                    System.out.println("@@");
                    maxSize = tempSize;
                }
            }
            Queue<Person> queue = new LinkedList<>();
            while(!startList.isEmpty()){
                Person person = startList.poll();
                if( person.keyList.size() == maxSize ){
                    queue.add( person );
                }
            }
            while(!queue.isEmpty()){
//                System.out.println("x: " + queue.peek().positionX + " y: " + queue.peek().positionY );
                startList.add( queue.poll() );
            }
            visited = new boolean[mazeRowSize][mazeColSize];
            bfs();
//            while(iterator.hasNext()){
//                Person person = iterator.next();
//                int tempSize = person.keyList.size();
//                if( tempSize == maxSize ){
//                    bfs();
//                }
//            }
        }
    }
}
