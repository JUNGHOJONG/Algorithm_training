package algorithm.swea;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.StringTokenizer;
import java.util.TreeSet;

public class Swea_5658 {
    private static BufferedReader br = new BufferedReader( new InputStreamReader( System.in ));
    private static int testCase;
    private static int numberCount;
    private static int indexNumber;
    private static char[] password;
    private static TreeSet<String> treeSet;
    public static void main(String[] args) throws IOException {
        testCase = Integer.parseInt(br.readLine());
        for( int i=0; i<testCase; i++ ){
            StringTokenizer st = new StringTokenizer(br.readLine());
            numberCount = Integer.parseInt(st.nextToken());
            indexNumber = Integer.parseInt(st.nextToken());
            treeSet = new TreeSet<>();
            initPassword();
            int output = rotationPassword();
            System.out.println( "#" + (i+1) + " " + output );
        }
    }

    public static void initPassword() throws IOException{
        password = new char[numberCount];
        String temp = br.readLine();
        int size = temp.length();
        for( int i=0; i<size; i++ ){
            password[i] = temp.charAt(i);
        }
    }

    public static int rotationPassword(){
        for( int i=0; i<numberCount/4; i++ ){
            movePassword();
            savePassword();
        }
        String temp = getPassword();
        return tranferPassword( temp );
    }

    public static void movePassword(){
        char temp = password[numberCount-1];
        for( int i=numberCount-2; i>=0; i-- ){
            password[i+1] = password[i];
        }
        password[0] = temp;
    }

    public static void savePassword(){
        for( int i=0; i<numberCount; i=i+numberCount/4 ){
            StringBuilder sb = new StringBuilder();
            for( int j=i; j<i+numberCount/4; j++ ){
                sb.append( password[j] );
            }
            treeSet.add( sb.toString() );
        }
    }

    public static String getPassword(){
        int index = 0;
        String temp = null;
        Iterator<String> iterator = treeSet.descendingIterator();
        while(iterator.hasNext()){
            temp = iterator.next();
            index++;
            if( index == indexNumber ){
                break;
            }
        }
        return temp;
    }

    public static int tranferPassword( String temp ){
        int size = temp.length();
        int passwordNumber = 0;
        for( int i=size-1; i>=0; i-- ){
            if( temp.charAt(size-i-1) >= '1' && temp.charAt(size-i-1) <= '9' ){
                passwordNumber += ( Math.pow( 16, i ) * ( temp.charAt(size-i-1) - '0' ) );
            }else if( temp.charAt(size-i-1) >= 'A' && temp.charAt(size-i-1) <= 'F' ){
                passwordNumber += ( Math.pow( 16, i ) * ( temp.charAt(size-i-1) - 'A' + 10 ) );
            }
        }
        return passwordNumber;
    }
}
