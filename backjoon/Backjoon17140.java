package algorithm.backjoon;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Backjoon17140 {
    public static class NumberSet implements Comparable<NumberSet>{
        private int number;
        private int count;
        public NumberSet( int number, int count ){
            this.number = number;
            this.count = count;
        }
        public int compareTo( NumberSet temp ){
            if( count == temp.count ){
                return( number < temp.number ? -1 : 1 );
            }
            return( count < temp.count ? -1 : 1 );
        }
    }
    private static BufferedReader br = new BufferedReader( new InputStreamReader( System.in ));
    private static int row;
    private static int col;
    private static int value;
    private static int minTime = -1;
    private static int[][] map = new int[3][3];
    private static boolean check = false;
    private static HashMap<Integer, Integer> hashMap = new HashMap<>();
    private static HashSet<Integer> hashSet = new HashSet<>();
    private static PriorityQueue<NumberSet> priorityQueue = new PriorityQueue();

    public static void main(String[] args) throws IOException {
        StringTokenizer st = new StringTokenizer(br.readLine());
        row = Integer.parseInt(st.nextToken())-1;
        col = Integer.parseInt(st.nextToken())-1;
        value = Integer.parseInt(st.nextToken());
        initMap();
        if ( map.length > row && map[0].length > col && map[row][col] == value ) {
            System.out.println(0);
            return;
        }
        for( int i=0; i<100; i++ ){
            int rowSize = map.length; // 3
            int colSize = map[0].length; // 5
            if( rowSize >= colSize ){
                calculateRow( rowSize, colSize );
            }else{
                calculateCol( rowSize, colSize );
            }
            if( map.length <= row || map[0].length <= col ) continue;
            if( map[row][col] == value ){
                minTime = i+1;
                break;
            }
        }
        System.out.println( minTime );
    }

    public static void initMap() throws IOException{
        for( int i=0; i<3; i++ ){
            StringTokenizer st = new StringTokenizer(br.readLine());
            for( int j=0; j<3; j++ ){
                map[i][j] = Integer.parseInt(st.nextToken());
            }
        }
    }

    public static void calculateRow( int rowSize, int colSize ){
        ArrayList<Integer>[] lists = new ArrayList[rowSize];
        for( int i=0; i<rowSize; i++ ){
            lists[i] = new ArrayList();
        }
        int colMaxSize = 0;
        for( int i=0; i<rowSize; i++ ){
            for( int j=0; j<colSize; j++ ){
                if( map[i][j] == 0 ) continue;
                hashSet.add( map[i][j] );
                if( hashMap.containsKey( map[i][j] )){
                    int tempValue = hashMap.get( map[i][j] );
                    hashMap.put( map[i][j], tempValue + 1 );
                }else{
                    hashMap.put( map[i][j], 1 );
                }
            }
            ArrayList<Integer> tempList = new ArrayList( hashSet );
            for( int n : tempList ){
                priorityQueue.add( new NumberSet( n, hashMap.get(n) ) );
            }
            while(!priorityQueue.isEmpty()){
                NumberSet numberSet = priorityQueue.poll();
                lists[i].add( numberSet.number );
                lists[i].add( numberSet.count );
            }
            int size = lists[i].size();
            colMaxSize = Math.max( colMaxSize, size );
            hashMap.clear();
            hashSet.clear();
        }
        if( colMaxSize > 100 ) colMaxSize = 100;
        map = new int[rowSize][colMaxSize];
        for( int i=0; i<rowSize; i++ ){
            int size = lists[i].size();
            if( size > 100 ) size = 100;
            for( int j=0; j<size; j++ ){
                map[i][j] = lists[i].get(j);
            }
        }
    }

    public static void calculateCol( int rowSize, int colSize ){
        ArrayList<Integer>[] lists = new ArrayList[colSize];
        for( int j=0; j<colSize; j++ ){
            lists[j] = new ArrayList();
        }
        int rowMaxSize = 0;
        for( int j=0; j<colSize; j++ ){
            for( int i=0; i<rowSize; i++ ){
                if( map[i][j] == 0 ) continue;
                hashSet.add( map[i][j] );
                if( hashMap.containsKey( map[i][j] )){
                    int tempValue = hashMap.get( map[i][j] );
                    hashMap.put( map[i][j], tempValue + 1 );
                }else{
                    hashMap.put( map[i][j], 1 );
                }
            }
            ArrayList<Integer> tempList = new ArrayList( hashSet );
            for( int n : tempList ){
                priorityQueue.add( new NumberSet( n, hashMap.get(n) ) );
            }
            while(!priorityQueue.isEmpty()){ // 삽입
                NumberSet numberSet = priorityQueue.poll();
                lists[j].add( numberSet.number );
                lists[j].add( numberSet.count );
            }
            int size = lists[j].size();
            rowMaxSize = Math.max( rowMaxSize, size );
            hashMap.clear();
            hashSet.clear();
        }
        if( rowMaxSize > 100 ) rowMaxSize = 100;
        map = new int[rowMaxSize][colSize];
        for( int j=0; j<colSize; j++ ){
            int size = lists[j].size();
            if( size > 100 ) size = 100;
            for( int i=0; i<size; i++ ){
                map[i][j] = lists[j].get(i);
            }
        }
    }
}
