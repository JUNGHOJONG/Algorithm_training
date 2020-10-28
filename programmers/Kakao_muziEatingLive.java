package algorithm.programmers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

public class Kakao_muziEatingLive {
    public static class Food{
        private int mount;
        private int index;
        public Food( int mount, int index ){
            this.mount = mount;
            this.index = index;
        }
    }
    private static Comparator<Food> mountAscendSort = new Comparator<Food>() {
        @Override
        public int compare(Food o1, Food o2) {
            return o1.mount <= o2.mount ? -1 : 1;
        }
    };
    private static Comparator<Food> indexAscendSort = new Comparator<Food>() {
        @Override
        public int compare(Food o1, Food o2) {
            return o1.index <= o2.index ? -1 : 1;
        }
    };
    private static ArrayList<Food> arrayList = new ArrayList<>();
//    private static int[] food_times = { 3, 1, 3, 4, 5 };
    private static int[] food_times = { 3, 1, 2 };
    private static int k = 5;
    public static void main(String[] args) {
        // return -1 거르기
        initArrayList();
        arrayList.sort( mountAscendSort );
        int size = arrayList.size();
        int cycle = size;
        int total = 0;
        int tempIndex = 0;
        for( int i=0; i<size;i++ ){
            if( i == 0 ){
                total = cycle * arrayList.get(i).mount;
            }else{
                total = cycle * ( arrayList.get(i).mount - arrayList.get(i-1).mount );
            }
            if( k < total ){
                tempIndex = i;
                break;
            } else{
                k -= total;
            }
            cycle--;
        }
        Food[] temp = new Food[arrayList.size()];
        temp = arrayList.toArray( temp );
        Arrays.sort( temp, tempIndex, size-1, indexAscendSort );
        System.out.println( temp[tempIndex + k % cycle ].index );
    }

    public static void initArrayList(){
        int size = food_times.length;
        for( int i=0; i<size; i++ ){
            arrayList.add( new Food( food_times[i], i + 1 ) );
        }
    }
}
