package algorithm.programmers;

import java.util.LinkedList;
import java.util.Queue;

public class Kakao_2018_01_cache {
    public static void main(String[] args) {
        int cacheSize = 3;
        String[] cities = {"Jeju", "Pangyo", "Seoul", "Jeju",
                "Pangyo", "Seoul", "Jeju", "Pangyo", "Seoul"};
        Solution_cache processSolution = new Solution_cache();
        System.out.println( processSolution.solution( cacheSize, cities ) );
    }
}

class Solution_cache{
    public int solution(int cacheSize, String[] cities) {
        int cachehitTime = 1; int cachemissTime = 5;
        if( cacheSize == 0 ) return cachemissTime * cities.length;

        String[] tempCities = changeToUpperCase( cities );
        Queue<String> queue = new LinkedList<>();
        int time = 0;
        for( String s : tempCities ){
            if( queue.contains( s ) ){
                time += cachehitTime;
                queue.remove( s );
            }else{
                if( queue.size() == cacheSize ){
                    queue.poll();
                }
                time += cachemissTime;
            }
            queue.add( s );
        }
        return time;
    }

    public String[] changeToUpperCase( String[] cities ){
        String[] tempCities = new String[cities.length];
        int index = 0;
        for( String s : cities ){
            tempCities[index] = s.toUpperCase();
            index++;
        }
        return tempCities;
    }
}