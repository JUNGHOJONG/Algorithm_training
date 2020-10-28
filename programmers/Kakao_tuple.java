package algorithm.programmers;

import java.util.*;

public class Kakao_tuple {
//    private static String temp = "{{2},{2,1},{2,1,3},{2,1,3,4}}";
    private static String temp = "{{1,2,3},{2,1},{1,2,4,3},{2}}";
    private static HashMap<Integer, HashSet> hashMap = new HashMap<>();
    public static void main(String[] args){
        temp = temp.substring( 2, temp.length()-2 ).replace( "},{", "-" );
        String[] tokens = temp.split( "-" );
        Arrays.sort(tokens, new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return o1.length() < o2.length() ? -1 : 1 ;
            }
        });
        for( String n : tokens ){
            System.out.print( n + " " );
        }
        ArrayList<Integer> list = new ArrayList<>();
        for( String n : tokens ){
            System.out.println( n );
            String[] token = n.split( "," );
            for( String n2 : token ){
                int value = Integer.parseInt( n2 );
                if( !list.contains( value ) ) list.add( value );
            }
        }
        int[] output = new int[list.size()];
        for( int i=0; i<output.length; i++ ){
            output[i] = list.get(i);
            System.out.print( list.get(i) + " " );
        }
    }
}
