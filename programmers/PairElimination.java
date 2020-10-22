package algorithm.programmers;

import java.util.Stack;

public class PairElimination {
    public static void main(String[] args) {
//        String input = "baabaa";
        String input = "cdcd";
        Solution_PairElimination processCommand = new Solution_PairElimination();
        System.out.println(processCommand.solution(input));
    }
}

class Solution_PairElimination{
    public int solution(String s){
        Stack<Character> stack = new Stack<>();
        for(int i=0; i<s.length(); i++){
            if(!stack.isEmpty() && stack.peek() == s.charAt(i)){
                stack.pop();
                continue;
            }
            stack.push(s.charAt(i));
        }
        return stack.size() == 0 ? 1 : 0;
    }
}
