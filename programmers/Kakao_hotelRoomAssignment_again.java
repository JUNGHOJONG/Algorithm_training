package algorithm.programmers;

import java.util.HashMap;

public class Kakao_hotelRoomAssignment_again {
//    private static long k = 10;
    private static long[] room_number = {1,3,4,1,3,1};
    private static HashMap<Long, Long> hashMap = new HashMap<>();
    public static void main(String[] args){
        long[] customer = new long[room_number.length];
        for( int i=0; i<room_number.length; i++ ){
            if( !hashMap.containsKey( room_number[i] ) ){
                hashMap.put( room_number[i], room_number[i] );
                customer[i] = room_number[i];
                if( room_number[i] - 1 != 0 && hashMap.containsKey( room_number[i] - 1 ) ) mergeParent( room_number[i], room_number[i]-1 );
                if( hashMap.containsKey( room_number[i] + 1 ) ) mergeParent( room_number[i], room_number[i]+1 );
            }else{
                long index = getParent( room_number[i] ) + 1;
                hashMap.put( index, index );
//                parent[index] = index;
                customer[i] = index;
                if( hashMap.containsKey( index - 1 ) ) mergeParent( index, index-1 );
                if( hashMap.containsKey( index + 1 ) ) mergeParent( index, index+1 );
            }
        }
        print( customer );
    }

    public static void print( long[] customer ){
        for( int i=0; i<customer.length; i++ ){
            System.out.print( customer[i] + " " );
        }
    }

    public static long getParent( long data ){
        if( hashMap.get( data ) == data ) return data;
        hashMap.put( data, getParent( hashMap.get( data ) ) );
        return hashMap.get( data );
    }

    public static void mergeParent( long a, long b ){
        long parentA = getParent(a);
        long parentB = getParent(b);
        if( parentA < parentB ) hashMap.put( parentA, parentB );
        else hashMap.put( parentB, parentA );
    }
}
