package algorithm;

import java.util.PriorityQueue;

public class Kakao_2018_01_shuttleBus {
    public static void main(String[] args) throws Exception {
        int n = 1; int t = 1; int m = 5;
        String[] timetable = { "08:00", "08:01", "08:02", "08:03" };
        Solution processSolution = new Solution();
        System.out.println( processSolution.solution( n, t, m, timetable ) );
    }
}

class Solution{
    private PriorityQueue<Integer> priorityQueue = new PriorityQueue<>();
    public String solution(int n, int t, int m, String[] timetable)
            throws Exception {
        initQueue( timetable );
        int dateTime = 540; // 09:00
        int temp = 0;
        boolean check = false;
        int count = 0;
        for( int i=0; i<n; i++ ){
            for( int j=0; j<m; j++ ){
                if( priorityQueue.size() == 0 ) check = true;
                if( !priorityQueue.isEmpty() && dateTime >= priorityQueue.peek() ){
                    if( i == n-1 ){
                        count++;
                    }
                    temp = priorityQueue.poll();
                }
            }
            if( i == n-1 && count < m ) check = true;
            dateTime += t;
        }
        dateTime -= t;
        if( !check ){
            int hour = ( temp - 1 ) / 60;
            int minute = ( temp - 1 ) % 60;
            return numberToString( hour, minute );
        }else{
            int hour = dateTime / 60;
            int minute = dateTime % 60;
            return numberToString( hour, minute ); // 마지막 셔틀 시간
        }
    }

    public void initQueue( String[] table ){
        for( String s : table ){
            String[] tokes = s.split( ":" );
            int num = Integer.parseInt( tokes[0] ) * 60 + Integer.parseInt( tokes[1] );
            priorityQueue.add( num );
        }
    }

    public String numberToString( int hour, int minute ){
        String tempHour = "";
        String tempMinute = "";
        if( hour < 10 ) tempHour = "0" + hour;
        else tempHour = "" + hour;
        if( minute < 10 ) tempMinute = "0" + minute;
        else tempMinute = "" + minute;
        return tempHour + ":" + tempMinute;
    }
}
