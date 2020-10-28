package algorithm.backjoon;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.TreeSet;

public class Backjoon2668 {
    public static void main(String[] args) throws IOException{
        Solution_Backjoon2668 processCommand = new Solution_Backjoon2668();
        processCommand.solution();
    }
}

class Solution_Backjoon2668{
    private BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    private TreeSet<Integer> treeSet = new TreeSet<>();
    private TreeSet<Integer> temp = new TreeSet<>();
    private int N;
    private boolean[] visited;
    private int[] number;
    private boolean check = true;
    public void solution() throws IOException {
        N = Integer.parseInt(br.readLine());
        visited = new boolean[N+1];
        number = new int[N+1];
        initNumber(N);
        for(int i=N; i>=0 && check; i--){
            combination(0, 1, i);
        }
        Iterator<Integer> iterator = treeSet.iterator();
        System.out.println(treeSet.size());
        while(iterator.hasNext()){
            System.out.println(iterator.next());
        }
    }

    public void initNumber(int N) throws IOException{
        for(int i=1; i<=N; i++){
            number[i] = Integer.parseInt(br.readLine());
        }

    }

    public void combination(int count, int index, int end){
        if(count == end){
            doCommand();
//            System.out.println("@");
            return;
        }
        for(int i=index; i<=N && check; i++){
            visited[i] = true;
            combination(count+1, i+1, end);
            visited[i] = false;
        }
    }

    public void doCommand(){
        for(int i=1; i<=N; i++){
            if(visited[i]){
                temp.add(i);
                treeSet.add(number[i]);
            }
        }
        if(treeSet.containsAll(temp)) {
//            for(Integer n : treeSet){
//                System.out.println(n + " ");
//            }
            check = false;
            return;
        }
        temp.clear();
        treeSet.clear();
    }
}