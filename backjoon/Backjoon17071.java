package algorithm.backjoon;

import java.io.*;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Backjoon17071 {

    public static class Person{
        private int subinPosition;
        private int youngerBrotherPosition;
        private int elapsedTime;
        public Person( int subinPosition, int youngerBrotherPosition, int elapsedTime ){
            this.subinPosition = subinPosition;
            this.youngerBrotherPosition = youngerBrotherPosition;
            this.elapsedTime = elapsedTime;
        }
    }

    private static BufferedReader br = new BufferedReader( new InputStreamReader( System.in ));
    private static BufferedWriter bw = new BufferedWriter( new OutputStreamWriter( System.out ));
    private static Queue<Person> queue = new LinkedList<>();
    private static int minSecond = Integer.MAX_VALUE;

    public static void  main(String[] args) throws IOException {
        StringTokenizer st = new StringTokenizer(br.readLine());
        int subinPosition = Integer.parseInt(st.nextToken());
        int youngerBrotherPosition = Integer.parseInt(st.nextToken());
        if ( bfs(subinPosition, youngerBrotherPosition) ) {
            bw.write( minSecond + "\n" );
        }else {
            bw.write( -1 + "\n" );
        }
        bw.flush();
        bw.close();
    }

    public static boolean bfs( int subinPosition, int youngerBrotherPosition ){
        queue.add(new Person(subinPosition, youngerBrotherPosition, 0));
        while (!queue.isEmpty()) {
            Person current = queue.poll();
            int subin = current.subinPosition;
            int youngerBrother = current.youngerBrotherPosition;
            int time = current.elapsedTime;
            System.out.println( "subin: " + subin + " brother: " + youngerBrother + " time: " + time );
            if( youngerBrother > 500000 ){
                return false;
            }
            if( subin == youngerBrother ){
                minSecond = time;
                break;
            }
            youngerBrother += ( time+1 );
            for( int i=0; i<3; i++ ){
                if( i == 0 ){
                    if( subin < youngerBrother ) continue;
                    if( movable( subin ) ) {
                        queue.add( new Person( subin-1, youngerBrother, time+1));
                    }
                    // && subin >= youngerBrother
                }else if( i == 1 ){
                    if( subin > youngerBrother ) continue;
                    if( movable( subin ) ) {
                        queue.add( new Person( subin+1, youngerBrother, time+1 ));
                    }
                    //&& subin <= youngerBrother + ( time+1 )
                }else{
                    if( subin > youngerBrother ) continue;
                    if( movable( subin ) ) {
                        queue.add( new Person( 2*subin, youngerBrother, time+1 ));
                    }
                }
            }
        }
        return true;
    }

    public static boolean movable ( int subinPosiiton ){
        return ( subinPosiiton >= 0 && subinPosiiton <= 500000 );
    }
}
