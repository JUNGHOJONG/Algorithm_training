package algorithm.programmers;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class Kakao_holidayTraffic {
    private static class Traffic{
        private long startTraffic;
        private long endTraffic;
        public Traffic( long startTraffic, long endTraffic ){
            this.startTraffic = startTraffic;
            this.endTraffic = endTraffic;
        }
    }
    private static ArrayList<Traffic> trafficManagement = new ArrayList<>();
    private static int maxValue = 0;
    public static void main(String[] args) throws ParseException {
        String[] lines = {
                "2016-09-15 20:59:57.421 0.351s",
                "2016-09-15 20:59:58.233 1.181s",
                "2016-09-15 20:59:58.299 0.8s",
                "2016-09-15 20:59:58.688 1.041s",
                "2016-09-15 20:59:59.591 1.412s",
                "2016-09-15 21:00:00.464 1.466s",
                "2016-09-15 21:00:00.741 1.581s",
                "2016-09-15 21:00:00.748 2.31s",
                "2016-09-15 21:00:00.966 0.381s",
                "2016-09-15 21:00:02.066 2.62s"
        };
        initTraffic( lines );
        doCommand( lines );
        System.out.println( maxValue );
    }

    public static void initTraffic( String[] lines ) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat( "HH:mm:ss.SSS" );
        Calendar date = Calendar.getInstance();
        for( String line : lines ){
            String[] tokens = line.split( "\\s" );
            Date te = simpleDateFormat.parse( tokens[1] );
            date.setTime(te);
            long endTime = date.getTimeInMillis();
            long periodTime = (long) ( Double.parseDouble( tokens[2].substring( 0, tokens[2].length() - 1 ) ) * 1000 );
            long startTime = endTime - ( periodTime - 1 );
            trafficManagement.add( new Traffic( startTime, endTime ) );
        }
    }

    public static void doCommand( String[] lines ){
        int size = trafficManagement.size();
        for( int i=0; i<size; i++ ){
            int count = 0;
            for( int j=i; j<size; j++ ){
                long criteria = trafficManagement.get( i ).endTraffic + 1000;
                if( criteria > trafficManagement.get( j ).startTraffic ){
                    count++;
                }
            }
            maxValue = Math.max( maxValue, count );
        }
    }

}
