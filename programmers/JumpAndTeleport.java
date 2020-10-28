package algorithm.programmers;

public class JumpAndTeleport {
    public static void main(String[] args) {
        int N = 5;	// 2
//        int N = 6;	// 2
//        int N = 5000;	// 5
        Solution_JumpAndTeleport processCommand = new Solution_JumpAndTeleport();
        System.out.println(processCommand.solution(N));
    }
}

class Solution_JumpAndTeleport{

    public int solution(int n) {
        return dfs(n, 0);
    }

    public int dfs(int number, int count){
        if(number == 1){
            return count + 1;
        }
        if(number % 2 == 0){
            return dfs(number / 2, count);
        }else{
            return dfs(number - 1, count + 1);
        }
    }
}


