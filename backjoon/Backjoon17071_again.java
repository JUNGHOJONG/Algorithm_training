package algorithm.backjoon;

import java.io.*;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Backjoon17071_again {

    public static class Subin{
        private int position;
        private int elapsedTime;
        public Subin( int position, int elapsedTime ){
            this.position = position;
            this.elapsedTime = elapsedTime;
        }
    }

    private static BufferedReader br = new BufferedReader( new InputStreamReader( System.in ));
    private static BufferedWriter bw = new BufferedWriter( new OutputStreamWriter( System.out ));
    private static Queue<Subin> queue = new LinkedList<>();
    private static int[] brotherTime = new int[500001];
    private static boolean[][] visited = new boolean[500001][2];
    private static int minSecond = Integer.MAX_VALUE;
    private static boolean check = false;

    public static void main(String[] args) throws IOException {
        StringTokenizer st = new StringTokenizer(br.readLine());
        int subinPosition = Integer.parseInt(st.nextToken());
        int brotherPosition = Integer.parseInt(st.nextToken());

        initBrotherTime( brotherPosition );
        bfs( subinPosition );
        print();
    }

    public static void initBrotherTime( int brotherPosition ){
        for( int i=0; i<=500000; i++ ){
            brotherTime[i] = -1;
        }
        int time = 0;
        while( brotherPosition + time <= 500000 ){
            brotherPosition += time;
            brotherTime[brotherPosition] = time;
            time++;
        }
    }

    public static void bfs( int subinPosition ){
        queue.add( new Subin( subinPosition, 0 ) );
        visited[subinPosition][0] = true;
        while(!queue.isEmpty()){
            Subin subin = queue.poll();
            int currentPosition = subin.position;
            int time = subin.elapsedTime;

            if( brotherTime[currentPosition] >= 0 && brotherTime[currentPosition] >= time
                    && ( brotherTime[currentPosition] - time ) % 2 == 0 ){
               minSecond = Math.min( minSecond, brotherTime[currentPosition] );
               check = true;
            }
            for( int i=0; i<3; i++ ){
                int nextPosition = moveNext( i, currentPosition );
                if( movable( nextPosition ) && !visited[nextPosition][(time+1)%2] ){
                    visited[nextPosition][(time+1)%2] = true;
                    queue.add( new Subin( nextPosition, time+1) );
                }
            }
        }
    }

    public static int moveNext( int i, int position ){
        if (i == 0) {
            return position - 1;
        } else if (i == 1) {
            return position + 1;
        } else {
            return 2 * position;
        }
    }

    public static boolean movable( int position ){
        return ( position>=0 && position<=500000 );
    }

    public static void print() throws IOException{
        if( check ){
            bw.write( minSecond + "\n" );
        }else{
            bw.write( -1 + "\n" );
        }
        bw.flush();
        bw.close();
    }
}
