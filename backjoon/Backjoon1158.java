package algorithm.backjoon;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.StringTokenizer;

public class Backjoon1158 {
    public static void main(String[] args) throws IOException{
        Solution_Backjoon1158 processCommand = new Solution_Backjoon1158();
        processCommand.solution();
    }
}

class Solution_Backjoon1158{
    private BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    public void solution() throws IOException {
        StringTokenizer st = new StringTokenizer(br.readLine());
        int personCount = Integer.parseInt(st.nextToken());
        int jumpCount = Integer.parseInt(st.nextToken());
        LinkedList<Integer> persons = new LinkedList<>();
        initPersons(persons, personCount);
        System.out.println(getJosephusPermutation(persons, jumpCount));
    }

    public void initPersons(LinkedList<Integer> persons, int personCount){
        for(int i=0; i<personCount; i++){
            persons.add(i + 1);
        }
    }

    public String getJosephusPermutation(LinkedList<Integer> persons, int jumpCount){
        StringBuilder josephusPermutation = new StringBuilder("<");
        int index = 0;
        while(persons.size() != 0){
            index = (index + (jumpCount - 1)) % persons.size();
            if(persons.size() != 1) {
                josephusPermutation.append(persons.get(index)).append(", ");
            }else{
                josephusPermutation.append(persons.get(index)).append(">");
            }
            if(index == persons.size() - 1){
                persons.remove(index);
                index = 0;
                continue;
            }
            persons.remove(index);
        }
        return josephusPermutation.toString();
    }
}
