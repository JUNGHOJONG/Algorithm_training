package algorithm.backjoon;

import java.io.*;
import java.util.StringTokenizer;

public class Backjoon2042 {

    private static BufferedReader br = new BufferedReader( new InputStreamReader( System.in));
    private static BufferedWriter bw = new BufferedWriter( new OutputStreamWriter( System.out));
    private static int totalNumber;
    private static int changeCount;
    private static int sumCount;
    private static int[] number;
    private static long[] segment;

    public static void main(String[] args) throws IOException {
        StringTokenizer st = new StringTokenizer(br.readLine());
        totalNumber = Integer.parseInt(st.nextToken());
        changeCount = Integer.parseInt(st.nextToken());
        sumCount = Integer.parseInt(st.nextToken());
        initNumber();
        segment = new long[4 * totalNumber];
        initSegment( 1, totalNumber, 1 );
        doCommand();
    }

    public static void initNumber() throws IOException{
        number = new int[totalNumber+1];
        for( int i=1; i<=totalNumber; i++ ){
            number[i] = Integer.parseInt(br.readLine());
        }
    }

    public static long initSegment( int nodeStart, int nodeEnd, int nodeIndex ){
        if( nodeStart == nodeEnd ){
            segment[nodeIndex] = number[nodeStart];
            return segment[nodeIndex];
        }
        int nodeMid = ( nodeStart + nodeEnd ) / 2;
        segment[nodeIndex] = initSegment( nodeStart, nodeMid, 2 * nodeIndex)
                + initSegment( nodeMid + 1, nodeEnd, 2 * nodeIndex + 1);
        return segment[nodeIndex];
    }

    public static void doCommand() throws IOException{
        for( int i=1; i<=changeCount+sumCount; i++ ){
            StringTokenizer st = new StringTokenizer(br.readLine());
            int mode = Integer.parseInt(st.nextToken());
            int changeNumber = 1;
            int sumRangeNumber = 2;
            int nodeIndex = 1;
            if( mode == changeNumber ){
                int indexToChange = Integer.parseInt(st.nextToken());
                int numberToChange = Integer.parseInt(st.nextToken());
                updateSegment( 1, totalNumber, nodeIndex, indexToChange, numberToChange );
            }else if( mode == sumRangeNumber ){
                int queryStart = Integer.parseInt(st.nextToken());
                int queryEnd = Integer.parseInt(st.nextToken());
                long output = sumSegment( 1, totalNumber, queryStart, queryEnd, nodeIndex );
                bw.write( output + "\n" );
            }
        }
        bw.flush();
        bw.close();
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

    public static long sumSegment( int nodeStart, int nodeEnd, int queryStart, int queryEnd, int nodeIndex ){
        if( queryStart <= nodeStart && nodeEnd <= queryEnd ) return segment[nodeIndex];
        else if( queryEnd < nodeStart || queryStart > nodeEnd) return 0;
        int nodeMid = ( nodeStart + nodeEnd ) / 2;
        return (sumSegment(nodeStart, nodeMid, queryStart, queryEnd, 2 * nodeIndex)
                + sumSegment(nodeMid + 1, nodeEnd, queryStart, queryEnd, 2 * nodeIndex + 1) );
    }
}
