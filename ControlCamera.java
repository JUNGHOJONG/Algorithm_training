package algorithm;
import java.util.*;
public class ControlCamera {
    public static void main(String[] args) {
        int[][] routes = {{-20, 15}, {-14, -5}, {-18, -13}, {-5, -3}};
        Solution_ControlCamera processSolution = new Solution_ControlCamera();
        System.out.println(processSolution.solution(routes));
    }
}

class Solution_ControlCamera {
    public static class Camera implements Comparable<Camera>{
        private int start;
        private int end;
        public Camera(int start, int end){
            this.start = start;
            this.end = end;
        }
        @Override
        public int compareTo(Camera temp) {
            return this.end - temp.end;
        }
    }
    private PriorityQueue<Camera> priorityQueue = new PriorityQueue<>();
    public int solution(int[][] routes) {
        initPriority(routes);
        assert priorityQueue.peek() != null;
        int criteria = priorityQueue.peek().end;
        int count = 1;
        while(!priorityQueue.isEmpty()){
            Camera temp = priorityQueue.poll();
            if( criteria < temp.start ) {
                criteria = temp.end;
                count++;
            }
        }
        return count;
    }

    public void initPriority(int[][] routes){
        for(int[] route : routes){
            priorityQueue.add(new Camera(route[0], route[1]));
        }
    }
}