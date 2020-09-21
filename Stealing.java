package algorithm;

public class Stealing {
    public static void main(String[] args) {
        int[] money = {1, 2, 3, 1};
        Solution_Stealing processSolution = new Solution_Stealing();
        System.out.println(processSolution.solution(money)); //output: 4
    }
}
class Solution_Stealing {
    public int solution(int[] money) {
        return dynamicProgramming(money);
    }

    public int dynamicProgramming(int[] money){
        int size = money.length;
        int[] firstStolenHouse = new int[size];
        int[] secondStolenHouse = new int[size];
        firstStolenHouse[0] = money[0];
        firstStolenHouse[1] = money[0];
        secondStolenHouse[1] = money[1];
        for(int i=2; i<size; i++){
            firstStolenHouse[i] = Math.max(firstStolenHouse[i-1], firstStolenHouse[i-2] + money[i]);
            secondStolenHouse[i] = Math.max(secondStolenHouse[i-1], secondStolenHouse[i-2] + money[i]);
        }
        return Math.max(firstStolenHouse[size-2], secondStolenHouse[size-1]);
    }
}
