package algorithm.jungol;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Jungol_1409 {
    private static BufferedReader br = new BufferedReader( new InputStreamReader( System.in ) );
    private static boolean[] closet;
    private static int[] command;
//    private static int[] direction = { 1, -1 };
    private static int closetCount;
    private static int commandCount;
    private static int minValue = Integer.MAX_VALUE;
    public static void main(String[] args) throws IOException {
        closetCount = Integer.parseInt(br.readLine());
        initCloset();
        initCommand();
        dfs( 0, 0 );
        System.out.println( minValue );
    }

    public static void initCloset() throws IOException{
        StringTokenizer st = new StringTokenizer(br.readLine());
        int openNumber1 = Integer.parseInt(st.nextToken())-1;
        int openNumber2 = Integer.parseInt(st.nextToken())-1;
        closet = new boolean[closetCount];
        closet[openNumber1] = true;
        closet[openNumber2] = true;
    }

    public static void initCommand() throws IOException{
        commandCount = Integer.parseInt(br.readLine());
        command = new int[commandCount];
        for( int i=0; i<commandCount; i++ ){
            command[i] = Integer.parseInt(br.readLine())-1;
        }
    }

    public static void dfs( int count, int movementCount ){
        if ( count == commandCount ) {
            minValue = Math.min( minValue, movementCount );
            return;
        }
        for( int i=0; i<2; i++ ){
            boolean[] copyCloset = copyCloset();
            if( closet[ command[count] ] ) {
                dfs( count+1, movementCount);
            } else{
                int tempCount = moveCloset( command[count], i );
                if( closet[ command[count] ] ) dfs( count+1, movementCount + tempCount );
            }
            resetCloset( copyCloset );
        }
    }

    public static boolean[] copyCloset(){
        boolean[] copyCloset = new boolean[closetCount];
        for( int i=0; i<closetCount; i++ ){
            copyCloset[i] = closet[i];
        }
        return copyCloset;
    }

    public static int moveCloset( int closetNumber, int index ){
        int movementCount = 0;
        if( index == 0 ){
            for( int i=closetNumber; i<closetCount; i++ ){
                if( closet[i] ) {
                    movementCount = Math.abs( i - closetNumber );
                    closet[i] = false;
                    closet[closetNumber] = true;
                    break;
                }
            }
        }else{
            for( int i=closetNumber; i>=0; i-- ){
                if( closet[i] ) {
                    movementCount = Math.abs( i - closetNumber );
                    closet[i] = false;
                    closet[closetNumber] = true;
                    break;
                }
            }
        }
        return movementCount;
    }

    public static void resetCloset( boolean[] copyCloset ){
        for( int i=0; i<closetCount; i++ ){
            closet[i] = copyCloset[i];
        }
    }
}
