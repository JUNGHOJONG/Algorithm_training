package algorithm.backjoon;

import java.io.*;
import java.util.StringTokenizer;

public class Backjoon14888 {

    private static BufferedReader br = new BufferedReader( new InputStreamReader( System.in ) );
    private static BufferedWriter bw = new BufferedWriter( new OutputStreamWriter( System.out ) );
    private static StringBuilder operatorManagement  = new StringBuilder();
    private static int[] number;
    private static char[] operators;
    private static int numberSize;
    private static int minValue = Integer.MAX_VALUE;
    private static int maxValue = -Integer.MAX_VALUE;

    public static void main(String[] args) throws IOException {
        numberSize = Integer.parseInt(br.readLine());
        initNumber();
        initOperatorManagement();
        String temp = operatorManagement.toString();
        operators = temp.toCharArray();
        permutation(0, operators.length );
        print();
    }

    public static void initNumber() throws IOException{
        number = new int[numberSize];
        StringTokenizer st = new StringTokenizer(br.readLine());
        for( int i=0; i<numberSize; i++ ){
            number[i] = Integer.parseInt(st.nextToken());
        }
    }

    public static void initOperatorManagement() throws IOException{
        StringTokenizer st = new StringTokenizer(br.readLine());
        int plus = 0, minus = 1, multiple = 2, divide = 3;
        for( int i=0; i<4; i++ ){
            int operatorCount = Integer.parseInt(st.nextToken());
            if ( i == plus ) {
                insertOperator( '+', operatorCount );
            }else if( i == minus ){
                insertOperator( '-', operatorCount );
            }else if( i == multiple ){
                insertOperator( '*', operatorCount );
            }else if( i == divide ){
                insertOperator( '/', operatorCount );
            }
        }
    }

    public static void insertOperator( char c, int operatorCount ){
        for (int i = 0; i < operatorCount; i++) {
            operatorManagement.append(c);
        }
    }

    public static void permutation( int k, int operatorsSize ){
        if( k == operatorsSize ){
            int output = getOperatorResult( operatorsSize );
            if( output < minValue ) minValue = output;
            if( output > maxValue ) maxValue = output;
            return;
        }
        for( int i=k; i<operatorsSize; i++ ){
            swap( i, k );
            permutation( k+1, operatorsSize );
            swap( i, k );
        }
    }

    public static void swap( int i, int k ){
        char temp = operators[i];
        operators[i] = operators[k];
        operators[k] = temp;
    }

    public static int getOperatorResult( int operatorsSize ){
        int temp = number[0];
        for( int i=0; i<operatorsSize; i++ ){
            if( operators[i] == '+' ){
                temp += number[i+1];
            } else if( operators[i] == '-' ){
                temp -= number[i+1];
            } else if( operators[i] == '*' ){
                temp *= number[i+1];
            } else if( operators[i] == '/' ){
                temp /= number[i+1];
            }
        }
        return temp;
    }

    public static void print() throws IOException{
        bw.write( maxValue + "\n" );
        bw.write( minValue + "\n" );
        bw.flush();
        bw.close();
    }
}
