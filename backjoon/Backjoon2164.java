package algorithm.backjoon;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;

public class Backjoon2164 {
    private static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    private static int number;
    private static Queue<Integer> queue = new LinkedList<>();
    public static void main(String[] args) throws IOException {
        number = Integer.parseInt( br.readLine() );
        initQueue();
        command();
    }

    public static void initQueue(){
        for( int i=1; i<=number; i++ ){
            queue.add( i );
        }
    }

    public static void command(){
        while( queue.size() > 1 ){
            queue.poll();
            queue.add( queue.poll() );
        }
        System.out.println( queue.peek() );
    }
}
