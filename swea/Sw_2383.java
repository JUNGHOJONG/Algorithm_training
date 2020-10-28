package algorithm.swea;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.StringTokenizer;

public class Sw_2383 {
    public static class Stair{
        private int positionX;
        private int positionY;
        private int length;
        public Stair( int positionX, int positionY, int length ){
            this.positionX = positionX;
            this.positionY = positionY;
            this.length = length;
        }
    }
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
    private static int mapSize;
    private static int totalPerson;
    private static int minValue;
    private static Stair[] stairs;
    private static boolean[] visited;
    private static ArrayList<Integer> tempList = new ArrayList<>();
    private static ArrayList<Integer> stepAndWaitList = new ArrayList<>();
    private static HashMap<Integer, Person> hashMap;

    public static void main(String[] args) throws IOException {
        testCase = Integer.parseInt(br.readLine());
        for( int i=0; i<testCase; i++ ){
            mapSize = Integer.parseInt(br.readLine());
            hashMap = new HashMap<>();
            stairs = new Stair[2];
            totalPerson = 0;
            minValue = Integer.MAX_VALUE;
            initMap();
            visited = new boolean[totalPerson+1];
            for( int j=0; j<=totalPerson; j++ ){
                combination( 1, 0, j );
            }
            System.out.println( "#" + (i+1) + " " + minValue );
        }
    }

    public static void initMap() throws IOException{
        int personIndex = 1;
        int stairIndex = 0;
        for (int i = 0; i < mapSize; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            for (int j = 0; j < mapSize; j++) {
                int value = Integer.parseInt(st.nextToken());
                if( value == 1 ){
                    hashMap.put( personIndex, new Person( j, i ));
                    personIndex++;
                    totalPerson++;
                }else if( value > 1 ) {
                    stairs[stairIndex] = new Stair( j, i, value );
                    stairIndex++;
                }
            }
        }
    }

    public static void combination( int k, int count, int end ){
        if( count == end ){
            int output = doCommand();
            minValue = Math.min( minValue, output );
            return;
        }
        for( int i=k; i<=totalPerson; i++ ){
            visited[i] = true;
            combination( i+1, count+1, end );
            visited[i] = false;
        }
    }

    public static int doCommand(){
        for( int i=1; i<=totalPerson; i++ ){
            if( !visited[i] ){
                Person person = hashMap.get(i);
                int distanceX = Math.abs( person.positionX - stairs[0].positionX );
                int distanceY = Math.abs( person.positionY - stairs[0].positionY );
                tempList.add( distanceX + distanceY );
            }
        }
        int time1 = 0;
        if( tempList.size() != 0 ){
            Collections.sort( tempList );
            time1 = tempList.get(0);
            plusPerson( time1, 0 );
            while( true ){
                time1++;
                listTimeDown();
                plusPerson( time1, 0 );
                if( stepAndWaitList.size() == 0 && tempList.size() == 0 ) break;
            }
        }
        stepAndWaitList.clear();
        tempList.clear();
        for( int i=1; i<=totalPerson; i++ ){
            if( visited[i] ){
                Person person = hashMap.get(i);
                int distanceX = Math.abs( person.positionX - stairs[1].positionX );
                int distanceY = Math.abs( person.positionY - stairs[1].positionY );
                tempList.add( distanceX + distanceY );
            }
        }
        int time2 = 0;
        if( tempList.size() != 0 ){
            Collections.sort( tempList );
            time2 = tempList.get(0);
            plusPerson( time2, 1 );
            while( true ){
                time2++;
                listTimeDown();
                plusPerson( time2, 1 );
                // 수정 부분: 예제 3의 경우 거리가 제일 먼 1이 도착하기도 전에 stepAndWaitList == 0 이 되어
                // 종료가 된다. 그래서 tempList도 조건에 넣어서 둘다 size == 0이 될 경우 종료.
                // tempList는 아래 plusPerson() 함수에서 해당 time을 검사하고 난뒤 제거 하게끔 수정했다.
                if( stepAndWaitList.size() == 0 && tempList.size() == 0 ) break;
            }
        }
        // 수정 부분: stepAndWaitList, tempList를 초기화 해주지 않았다.(전역변수는 최대한 사용하지 않기)
        stepAndWaitList.clear();
        tempList.clear();
        return Math.max( time1, time2 );
    }

    public static void listTimeDown(){
        if( stepAndWaitList.size() >= 3 ){
            for( int i=2; i>=0; i-- ){
                stepAndWaitList.set( i, stepAndWaitList.get(i)-1 );
                if( stepAndWaitList.get(i) == 0 ) stepAndWaitList.remove(i);
            }
        }else{
            int size = stepAndWaitList.size();
            for( int i=size-1; i>=0; i-- ){
                stepAndWaitList.set( i, stepAndWaitList.get(i)-1 );
                if( stepAndWaitList.get(i) == 0 ) stepAndWaitList.remove(i);
            }
        }
    }

    public static void plusPerson( int time, int index ){
        int size = tempList.size();
        for( int i=size-1; i>=0; i-- ){
            if( tempList.get(i) == time ) {
                if( stepAndWaitList.size() < 3 ){
                    // 수정: plusPerson()함수가 계단 1,2 경우 다르기 때문에 index 변수로 구분하였다.
                    stepAndWaitList.add( stepAndWaitList.size(), stairs[index].length + 1 );
                }else{
                    stepAndWaitList.add( stepAndWaitList.size(), stairs[index].length );
                }
                // 수정 부분: tempList 삭제 처리
                tempList.remove(i);
            }
        }
    }

}
