package algorithm;
import java.util.PriorityQueue;
class Kakao_steppingStoneCrossing {
    private int[] number;
    private int[] segment;
    private int totalNumber;
    public int solution(int[] stones, int k) {
        totalNumber = stones.length;
        initNumber( stones );
        segment = new int[4 * totalNumber];
        initSegment( 1, totalNumber, 1 );
        PriorityQueue<Integer> priorityQueue = new PriorityQueue<>();
        for( int i = 0; i <= stones.length - k; i++ ){
            priorityQueue.add( getMaxSegment( 1, totalNumber, 1, i + 1, i + k ) );
        }
        return priorityQueue.poll();
    }

    public void initNumber( int[] stones ) {
        number = new int[totalNumber+1];
        for( int i=1; i<=totalNumber; i++ ) {
            number[i] = stones[i-1];
        }
    }

    public int initSegment( int nodeStart, int nodeEnd, int nodeIndex ) {
        if( nodeStart == nodeEnd ) {
            segment[nodeIndex] = number[nodeStart];
            return segment[nodeIndex];
        }
        int nodeMid = ( nodeStart + nodeEnd ) / 2;
        segment[nodeIndex] = Math.max( initSegment( nodeStart, nodeMid, 2 * nodeIndex )
                , initSegment( nodeMid + 1, nodeEnd, 2 * nodeIndex + 1 ) );
        return segment[nodeIndex];
    }

    public int getMaxSegment( int nodeStart, int nodeEnd, int nodeIndex, int queryStart, int queryEnd ) {
        if( queryStart <= nodeStart && nodeEnd <= queryEnd ) return segment[nodeIndex];
        else if( queryEnd < nodeStart || queryStart > nodeEnd ) return 0;
        int nodeMid = ( nodeStart + nodeEnd ) / 2;
        return Math.max( getMaxSegment( nodeStart, nodeMid, 2 * nodeIndex, queryStart, queryEnd )
                , getMaxSegment( nodeMid + 1, nodeEnd, 2 * nodeIndex + 1, queryStart, queryEnd ) );
    }
}