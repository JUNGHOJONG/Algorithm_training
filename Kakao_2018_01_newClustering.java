package algorithm;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;

public class Kakao_2018_01_newClustering {
    private static HashSet<String> hashSetofA = new HashSet<>();
    private static HashMap<String, Integer> hashMapofA = new HashMap<>();
    private static HashSet<String> hashSetofB = new HashSet<>();
    private static HashMap<String, Integer> hashMapofB = new HashMap<>();
    public static void main(String[] args) {
        String st1 = "aa1+aa2"; // aa1+aa2
        String st2 = "AAAA12";
        String changedSt1 = st1.toLowerCase();
        String changedSt2 = st2.toLowerCase();
        initA( changedSt1 );
        initB( changedSt2 );
        int countofA = getCountofA();
        int countofB = getCountofB();
        int intersection = getIntersection();
        int union = countofA + countofB - intersection;
        double output = (double) intersection / (double) union;
        System.out.println( (int) ( output * 65536 ) ); // 자리수 버림
    }

    public static void initA( String changedSt1 ){
        int size = changedSt1.length();
        for( int i=0; i < size-1; i++ ){
            if( changedSt1.charAt( i ) >= 'a' && changedSt1.charAt( i ) <= 'z'
            && changedSt1.charAt( i+1 ) >= 'a' && changedSt1.charAt( i+1 ) <= 'z'){
                String temp = changedSt1.substring( i, i+2 );
                if( !hashMapofA.containsKey( temp ) ){
                    hashMapofA.put(temp, 1);
                }else{
                    hashMapofA.put(temp, hashMapofA.get(temp) + 1);
                }
                hashSetofA.add( temp );
            }
        }
    }

    public static void initB( String changedSt2 ){
        int size = changedSt2.length();
        for( int i=0; i < size-1; i++ ){
            if( changedSt2.charAt( i ) >= 'a' && changedSt2.charAt( i ) <= 'z'
                    && changedSt2.charAt( i+1 ) >= 'a' && changedSt2.charAt( i+1 ) <= 'z'){
                String temp = changedSt2.substring( i, i+2 );
                if( !hashMapofB.containsKey( temp ) ){
                    hashMapofB.put(temp, 1);
                }else{
                    hashMapofB.put(temp, hashMapofB.get(temp) + 1);
                }
                hashSetofB.add( temp );
            }
        }
    }

    public static int getCountofA(){
        int count = 0;
        Iterator<String> iterator = hashSetofA.iterator();
        while( iterator.hasNext() ){
            count += hashMapofA.get( iterator.next() );
        }
        return count;
    }

    public static int getCountofB(){
        int count = 0;
        Iterator<String> iterator = hashSetofB.iterator();
        while( iterator.hasNext() ){
            count += hashMapofB.get( iterator.next() );
        }
        return count;
    }

    public static int getIntersection(){
        int count = 0;
        Iterator<String> iterator = hashSetofA.iterator();
        while( iterator.hasNext() ){
            String temp = iterator.next();
            if( hashSetofB.contains( temp ) ){
                count += Math.min( hashMapofA.get( temp ), hashMapofB.get( temp ) );
            }
        }
        return count;
    }
}
