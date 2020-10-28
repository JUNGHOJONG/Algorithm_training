package algorithm.programmers;

import java.util.HashSet;

public class PrimeNumberMaker {
    public static void main(String[] args) {
//        int[] nums = {1, 2, 3, 4};
        int[] nums = {1, 2, 7, 6, 4};
        Solution_PrimeNumberMaker processCommand = new Solution_PrimeNumberMaker();
        System.out.println(processCommand.solution(nums));
    }
}

class Solution_PrimeNumberMaker{
    private HashSet<Integer> differentNumbers = new HashSet<>();
    private boolean[] visited;
    private int numsSize;
    private int count = 0;
    public int solution(int[] nums){
        numsSize = nums.length;
        visited = new boolean[numsSize];
        selectNumbers(0, 0, nums);
        return count;
    }

    public void selectNumbers(int count, int index, int[] nums){
        if(count == 3){
            doCommand(nums);
            return;
        }
        for(int i=index; i<numsSize; i++){
            visited[i] = true;
            selectNumbers(count + 1, i + 1, nums);
            visited[i] = false;
        }
    }

    public void doCommand(int[] nums){
        addThreeNumber(nums);
        if(differentNumbers.size() < 3) {
            differentNumbers.clear();
            return;
        }
        int numberOfSum = getSumOfNumbers();
        if(isPrimeNumber(numberOfSum)){
            count++;
        }
        differentNumbers.clear();
    }

    public void addThreeNumber(int[] nums){
        for(int i=0; i<numsSize; i++){
            if(visited[i]){
                differentNumbers.add(nums[i]);
            }
        }
    }

    public int getSumOfNumbers(){
        int sum = 0;
        for(Integer n : differentNumbers){
            sum += n;
        }
        return sum;
    }

    public boolean isPrimeNumber(int numberOfSum){
        for(int i=2; i*i <= numberOfSum; i++){
            if(numberOfSum % i == 0){
                return false;
            }
        }
        return true;
    }
}
