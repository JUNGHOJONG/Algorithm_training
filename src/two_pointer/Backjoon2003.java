package two_pointer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Backjoon2003 {
    private static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    public static void main(String[] args) throws IOException {
        StringTokenizer st = new StringTokenizer(br.readLine());
        int sequenceSize = Integer.parseInt(st.nextToken());
        int criteriaSum = Integer.parseInt(st.nextToken());
        int[] sequence = initSequence(sequenceSize);
        System.out.println(twoPointer(sequence, criteriaSum));
    }

    public static int[] initSequence(int sequenceSize) throws IOException{
        int[] sequence = new int[sequenceSize];
        StringTokenizer st = new StringTokenizer(br.readLine());
        for(int i=0; i<sequenceSize; i++){
            sequence[i] = Integer.parseInt(st.nextToken());
        }
        return sequence;
    }

    public static int twoPointer(int[] sequence, int criteriaSum){
        int leftPointer = 0;
        int sum = 0;
        int count = 0;
        for (int i : sequence) {
            sum += i;
            while (sum > criteriaSum) {
                sum -= sequence[leftPointer];
                leftPointer++;
            }
            if (criteriaSum == sum) count++;
        }
        return count;
    }


}
