package algorithm.programmers;

public class CleanRectangle {
    public static void main(String[] args) {
        int w = 8;
        int h = 12; // output: 80

        for(int i=0; i<100; i++){
            int value = (int) (Math.random() * 10); // 0-9
            System.out.println(value + " ");
        }

        Solution_CleanRectangle processCommand = new Solution_CleanRectangle();
        System.out.println(processCommand.solution(w, h));
    }
}

class Solution_CleanRectangle{

    public long solution(int w,int h) {
        return getEliminatedRectangleCount(w, h);
    }

    public long getEliminatedRectangleCount(int w, int h){
        long count = 0;
        double lean = (double) h / w;
        for(int j=1; j<w; j++){
            double tempHeight = lean * j;
            int partH = (int) Math.ceil(tempHeight);
            count += h - partH;
        }
        return count * 2;
    }
}
