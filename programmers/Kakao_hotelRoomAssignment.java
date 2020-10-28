package algorithm.programmers;

public class Kakao_hotelRoomAssignment {
    private static int k = 10;
    private static int[] room_number = {1,3,4,1,3,1};
    private static int[] parent;
    public static void main(String[] args){
        parent = new int[k + 1];
        long[] customer = new long[(int)room_number.length];
        for( int i=0; i<room_number.length; i++ ){
            if( parent[room_number[i]] == 0 ){
                parent[room_number[i]] = room_number[i];
                customer[i] = room_number[i];
                if( room_number[i]-1 != 0 && parent[room_number[i]-1] != 0 ) mergeParent( room_number[i], room_number[i]-1 );
                if( parent[room_number[i]+1] != 0 ) mergeParent( room_number[i], room_number[i]+1 );
            }else{
                int index = getParent( room_number[i] ) + 1;
                parent[index] = index;
                customer[i] = index;
                if( parent[index-1] != 0 ) mergeParent( index, index-1 );
                if( parent[index+1] != 0 ) mergeParent( index, index+1 );
            }
        }
        long[] answer = (long[]) customer;
//        print( customer );
    }

    public static void print( int[] customer ){
        for( int i=0; i<customer.length; i++ ){
            System.out.print( customer[i] + " " );
        }
    }

    public static int getParent( int data ){
        if( parent[data] == data ) return data;
        return parent[data] = getParent( parent[data] );
    }

    public static void mergeParent( int a, int b ){
        int parentA = getParent(a);
        int parentB = getParent(b);
        if( parentA < parentB ) parent[parentA] = parentB;
        else parent[parentB] = parentA;
    }
}
