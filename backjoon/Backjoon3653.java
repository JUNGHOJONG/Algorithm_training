package algorithm.backjoon;

import java.io.*;
import java.util.HashMap;
import java.util.StringTokenizer;

public class Backjoon3653 {

    private static BufferedReader br = new BufferedReader( new InputStreamReader( System.in));
    private static BufferedWriter bw = new BufferedWriter( new OutputStreamWriter( System.out));
    private static HashMap<Integer, Integer> hashMap = new HashMap<Integer, Integer>();
    private static int testCase;
    private static int[] dvd;
    private static int[] segment;
    private static int movieCount;
    private static int movieToWatch;

    public static void main(String[] args) throws IOException {
        testCase = Integer.parseInt(br.readLine());
        for( int i=1; i<=testCase; i++ ){
            StringTokenizer st = new StringTokenizer(br.readLine());
            movieCount = Integer.parseInt(st.nextToken());
            movieToWatch = Integer.parseInt(st.nextToken());
            initDvd();
            segment = new int[4*dvd.length];
            initSegment( 1, movieCount+movieToWatch, 1 );
            doCommand();
        }
        bw.flush();
        bw.close();
    }

    public static void initDvd(){
        dvd = new int[movieCount+movieToWatch+1];
        for( int i=movieToWatch+1; i<=movieCount+movieToWatch; i++ ){
            dvd[i] = i-movieToWatch;
            hashMap.put(i-movieToWatch, i);
        }
    }

    public static int initSegment( int nodeStart, int nodeEnd, int nodeIndex ){
        if( nodeStart == nodeEnd ){
            if( dvd[nodeStart] != 0 ) segment[nodeIndex] = 1;
            return segment[nodeIndex];
        }
        int nodeMid = ( nodeStart + nodeEnd ) / 2;
        segment[nodeIndex] = initSegment( nodeStart, nodeMid, 2 * nodeIndex)
                + initSegment( nodeMid + 1, nodeEnd, 2 * nodeIndex + 1);
        return segment[nodeIndex];
    }

    public static void doCommand() throws IOException {
        StringTokenizer st = new StringTokenizer(br.readLine());
        for( int i=1; i<=movieToWatch; i++){
            int dvdNumber = Integer.parseInt(st.nextToken()); // 봐야할
//            int dvdIndex = findDvdIndex( dvdNumber );
            int dvdIndex = hashMap.get(dvdNumber);
            int output = sumSegment( 1, movieCount+movieToWatch, 1, dvdIndex-1, 1 );
            bw.write( output + " " );
            dvd[movieToWatch+1-i] = dvdNumber;
            hashMap.put( dvdNumber, movieToWatch+1-i);
            updateSegment( 1, movieCount+movieToWatch, 1, movieToWatch+1-i, 1 );
            dvd[dvdIndex] = 0;
            updateSegment( 1, movieCount+movieToWatch, 1, dvdIndex, 0 );
        }
        bw.write( "\n" );
    }

    public static int findDvdIndex( int dvdNumber ){
        int index = 0;
        for( int i=1; i<=movieCount+movieToWatch; i++ ){
            if( dvd[i] == dvdNumber ) index = i;
        }
        return index;
    }

    public static int sumSegment( int nodeStart, int nodeEnd, int queryStart, int queryEnd, int nodeIndex ){
        if( queryStart <= nodeStart && nodeEnd <= queryEnd ) return segment[nodeIndex];
        else if( queryEnd < nodeStart || queryStart > nodeEnd) return 0;
        int nodeMid = ( nodeStart + nodeEnd ) / 2;
        return (sumSegment(nodeStart, nodeMid, queryStart, queryEnd, 2 * nodeIndex)
                + sumSegment(nodeMid + 1, nodeEnd, queryStart, queryEnd, 2 * nodeIndex + 1) );
    }

    public static void updateSegment( int nodeStart, int nodeEnd, int nodeIndex, int indexToChange, int numberToChange ){
        if( indexToChange < nodeStart || indexToChange > nodeEnd ){
            return;
        }else if( nodeStart == nodeEnd ){
            segment[nodeIndex] = numberToChange;
            return;
        }
        int nodeMid = ( nodeStart + nodeEnd ) / 2;
        updateSegment( nodeStart, nodeMid, 2 * nodeIndex, indexToChange, numberToChange );
        updateSegment( nodeMid + 1, nodeEnd, 2 * nodeIndex + 1, indexToChange, numberToChange );
        segment[nodeIndex] = segment[2 * nodeIndex] + segment[2 * nodeIndex + 1];
    }
}
