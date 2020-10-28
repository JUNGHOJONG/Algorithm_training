package algorithm.programmers;

import java.util.HashSet;

import static java.lang.System.out;

public class kakao_gemShoping {
    //["DIA", "RUBY", "RUBY", "DIA", "DIA", "EMERALD", "SAPPHIRE", "DIA"]
    private static int minValue = Integer.MAX_VALUE;
    public static void main(String[] args) {
        String[] gems = {"DIA", "RUBY", "DIA", "RUBY", "DIA", "EMERALD", "SAPPHIRE", "DIA"};
//        String[] gems = {"XYZ", "XYZ", "XYZ"};
        int gemCount = getAllNonRedundantGemCount( gems );
        getRangeOfAllGemCount( gemCount, gems );
//        out.println( minValue );
//        out.println( 5 / 2 );
    }

    public static int getAllNonRedundantGemCount( String[] gems ){
        HashSet<String> hashSet = new HashSet<>();
        for( String s : gems ){
            hashSet.add( s );
        }
        return hashSet.size();
    }

    public static void getRangeOfAllGemCount( int gemCount, String[] gems ){
        HashSet<String> hashSet = new HashSet<>();
        int gemsSize = gems.length;
        int index = 0;
        for( int i=0; i<gemsSize; i++ ){
            hashSet.add( gems[i] );
            if( gemCount == hashSet.size() ) {
                index = i;
                break;
            }
        }
        out.println( "index:" + index  );

        // binarySearch
        int fixedStart = 0;
        int fixedEnd = index;
        int start = fixedStart;
        int end = fixedEnd;
        if( start == end ) minValue = start;
        while( start < end ){
            int mid = ( start + end ) / 2;
            if( getGemCount( mid, fixedEnd, gems ) < gemCount ){
               end = mid;
               out.println("@@");
            }else{
                start = mid + 1;
                minValue = mid;
                out.println( "start: " + start + " mid: " + mid + " end: " + end );
                // 구간의 길이가 작거나 같을때
            }
        }
        print( index );
    }

    public static int getGemCount( int mid, int fixedEnd, String[] gems ){
        HashSet<String> hashSet = new HashSet<>();
        for( int i=mid; i<=fixedEnd; i++ ){
            hashSet.add( gems[i] );
        }
        return hashSet.size();
    }

    public static void print( int index ){
        int output[] = new int[2];
        output[0] = minValue + 1;
        output[1] = index + 1;
        out.println( "[" + output[0] + ", " + output[1] + "]" );
    }
}
