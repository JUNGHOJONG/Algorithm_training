package algorithm.backjoon;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class Backjoon1931 {
    public static class Meeting{
        private int startTime;
        private int endTime;
        public Meeting( int startTime, int endTime ){
            this.startTime = startTime;
            this.endTime = endTime;
        }
    }
    private static BufferedReader br = new BufferedReader( new InputStreamReader( System.in ) );
    private static PriorityQueue<Meeting> priorityQueue = new PriorityQueue<>(new Comparator<Meeting>() {
        @Override
        public int compare(Meeting o1, Meeting o2) {
            if( o1.endTime == o2.endTime ) return ( o1.startTime - o2.startTime);
            else return (o1.endTime - o2.endTime);
        }
    });
    private static int meetingCount;
    public static void main(String[] args) throws IOException {
        meetingCount = Integer.parseInt(br.readLine());
        initSortMeeting();
        int output = getMaxUsableMeetingCount();
        System.out.println( output );
    }

    public static void initSortMeeting() throws IOException{
        for( int i=0; i<meetingCount; i++ ){
            StringTokenizer st = new StringTokenizer( br.readLine() );
            int startTime = Integer.parseInt( st.nextToken() );
            int endTime = Integer.parseInt( st.nextToken() );
            priorityQueue.add( new Meeting( startTime, endTime ) );
        }
    }

    public static int getMaxUsableMeetingCount(){
        int criteria = priorityQueue.poll().endTime;
        int count = 1;
        while( !priorityQueue.isEmpty() ){
            Meeting temp = priorityQueue.poll();
            if( criteria <= temp.startTime ){
                count++;
                criteria = temp.endTime;
            }
        }
        return count;
    }
}
