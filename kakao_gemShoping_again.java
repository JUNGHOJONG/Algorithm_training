package algorithm;
import java.util.*;
public class kakao_gemShoping_again {
    public static class Purchasement{
        private int left;
        private int right;
        private int length;
        public Purchasement( int left, int right, int length ){
            this.left = left;
            this.right = right;
            this.length = length;
        }
    }
    private static PriorityQueue<Purchasement> priorityQueue = new PriorityQueue<>((a, b) -> {
        if (a.length - b.length == 0) {
            return (a.left - b.left);
        } else {
            return (a.length - b.length);
        }
    }
    );
    public static void main(String[] args) {
        String[] gems = {"DIA", "RUBY", "RUBY", "DIA", "DIA", "EMERALD", "SAPPHIRE", "DIA"};
        int nonRedundantGemCount = getAllNonRedundantGemCount( gems );
        doTwoPointer( gems, nonRedundantGemCount );
        int[] output = new int[2];
        assert priorityQueue.peek() != null: "priorityQueue is null";
        output[0] = priorityQueue.peek().left + 1;
        output[1] = priorityQueue.peek().right + 1;
        System.out.println( "[" + output[0] + ", " + output[1] + "]" );
    }

    public static int getAllNonRedundantGemCount( String[] gems ){
        HashSet<String> hashSet = new HashSet<>();
        Collections.addAll( hashSet, gems );
        return hashSet.size();
    }

    public static void doTwoPointer( String[] gems, int nonRedundantGemCount ){
        int leftPointer = 0;
        int gemSize = gems.length;
        HashMap<String, Integer> hashMap = new HashMap<>();
        for( int rightPointer=0; rightPointer<gemSize; rightPointer++ ){
            if( !hashMap.containsKey( gems[rightPointer] ) ){
                hashMap.put( gems[rightPointer], 1 );
            }else{
                hashMap.put( gems[rightPointer], hashMap.get( gems[rightPointer] ) + 1 );
            }
            if( hashMap.size() == nonRedundantGemCount ){
                while ( hashMap.size() == nonRedundantGemCount && leftPointer < gemSize ) {
                    priorityQueue.add(new Purchasement(leftPointer, rightPointer, Math.abs(leftPointer - rightPointer)));
                    if ( hashMap.get(gems[leftPointer]) == 1 ) {
                        hashMap.remove(gems[leftPointer]);
                    } else {
                        hashMap.put(gems[leftPointer], hashMap.get(gems[leftPointer]) - 1);
                    }
                    leftPointer++;
                }
            }
        }
    }
}
