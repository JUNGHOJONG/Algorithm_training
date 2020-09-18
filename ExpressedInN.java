package algorithm;
public class ExpressedInN { // 다시 풀어보기!!
    public static void main(String[] args) {
        int N = 4;
        int number = 17;
        // output = 4
        // 반례1 : N = 4, number = 17, output = 4
        // 반례2 : N = 5, number = 26, output = 4
        Solution_ExpressedInN processSolution = new Solution_ExpressedInN();
        System.out.println(processSolution.solution(N, number));
    }
}

class Solution_ExpressedInN {
    private int min = Integer.MAX_VALUE;
    private boolean check = false;
    public int solution(int N, int number) {
        if(N == number) return 1;
        dfs( N, number, 0, 0 );
        return check ? min : -1;
    }

    public void dfs( int N, int number, int count, int value ){
        if(count > 8) return;
        if(value == number){
            check = true;
            min = Math.min(min, count);
            return;
        }
        int temp = 0;
        for( int i=0; i+count<9; i++ ){
            temp = temp * 10 + N;
            dfs(N, number, count+1+i, value * temp);
            dfs(N, number, count+1+i, value / temp);
            if(value != 0)dfs(N, number, count+1+i, temp / value);
            dfs(N, number, count+1+i, value + temp);
            dfs(N, number, count+1+i, value - temp);
            dfs(N, number, count+1+i, temp - value);
        }
    }
}