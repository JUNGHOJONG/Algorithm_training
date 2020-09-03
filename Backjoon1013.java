package algorithm;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.regex.Pattern;
// ^(100+1+|01)+$ 와 (100+1+|01)+의 차이점에 대해 곱씹어보자....
public class Backjoon1013 {
    private static BufferedReader br = new BufferedReader( new InputStreamReader( System.in ) );
    private static int testCase;
    public static void main(String[] args) throws IOException {
        testCase = Integer.parseInt( br.readLine() );
        for( int i=0; i<testCase; i++ ){
            String source = br.readLine();
            System.out.println( Pattern.compile( "^(100+1+|01)+$" ).matcher( source ).matches() ? "YES" : "NO" );
        }
    }
}
