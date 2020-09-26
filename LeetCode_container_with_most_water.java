package algorithm;

public class LeetCode_container_with_most_water {
    public static void main(String[] args) {
        int[] height = {1, 8, 6, 2, 5, 4, 8, 3, 7}; // output: 49
        Solution_container_with_most_water processCommand = new Solution_container_with_most_water();
        System.out.println(processCommand.maxArea(height));
    }
}

class Solution_container_with_most_water {
    public int maxArea(int[] height) {
        int leftPointer = 0;
        int rightPointer = height.length-1;
        int max = 0;
        while(leftPointer != rightPointer){
            int waterWidth = Math.min(height[leftPointer], height[rightPointer]);
            int waterHeight = rightPointer - leftPointer;
            int area = waterWidth * waterHeight;
            max = Math.max(max, area);
            if(height[leftPointer] <= height[rightPointer]){
                leftPointer++;
            }else{
                rightPointer--;
            }
        }
        return max;
    }
}