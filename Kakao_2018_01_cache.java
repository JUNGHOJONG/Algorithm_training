package algorithm;

import java.util.LinkedList;
import java.util.Queue;

public class Kakao_2018_01_cache {
    public static void main(String[] args) {
        int cacheSize = 3;
        String[] cities = { "Jeju", "Pangyo", "Seoul", "Jeju",
                "Pangyo", "Seoul", "Jeju", "Pangyo", "Seoul" };

        if( cacheSize == 0 ) {
            System.out.println( 5 * cities.length );
        }
        String[] tempCities = changeToUpperCase( cities );
        Queue<String> queue = new LinkedList<>();
        int time = 0;
        for( String s : tempCities ){
            if( queue.contains( s ) ){
                time++;
                queue.remove( s );
            }else{
                if( queue.size() == cacheSize ){
                    queue.poll();
                }
                time += 5;
            }
            queue.add( s );
        }
        System.out.println( time );
    }

    public static String[] changeToUpperCase( String[] cities ){
        String[] tempCities = new String[cities.length];
        int index = 0;
        for( String s : cities ){
            tempCities[index] = s.toUpperCase();
            index++;
        }
        return tempCities;
    }
}
