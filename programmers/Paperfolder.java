package algorithm.programmers;

public class Paperfolder {
    private static StringBuilder sb = new StringBuilder();
    private static int number = 4;
    public static void main(String[] args){
        dynamicProgramming();
        System.out.println( sb.toString() );
    }

    public static void dynamicProgramming(){
        for( int i=1; i<=number; i++ ){
            if( i==1 ){
                sb.append( "0" );
            }else{
                String temp = sb.reverse().toString();
                StringBuilder temp2 = new StringBuilder();
                for( int j=0; j<temp.length(); j++ ){
                    if( temp.charAt(j) == '1' ){
                        temp2.append( '0' );
                    }else{
                        temp2.append( '1' );
                    }
                }
                sb.reverse();
                sb.append( "0" +  temp2.toString() );
            }
        }
    }
}
