package algorithm.backjoon;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class Backjoon17281 {
    public static void main(String[] args) throws IOException{
        Solution_Backjoon17281 processCommand = new Solution_Backjoon17281();
        processCommand.solution();
    }
}

class Solution_Backjoon17281{
    private BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    private ArrayList<Integer>[] players = new ArrayList[10];
    private int inningCount;
    private int maxScore = 0;
    private int partScore = 0;
    private int playerOrder = 0;
    private int[] number;
    public void solution() throws IOException{
        inningCount = Integer.parseInt(br.readLine());
        initPlayers();
        initNumber();
        permutation(0);
        System.out.println(maxScore);
    }

    public void initPlayers() throws IOException{
        for(int i=1; i<10; i++) {
            players[i] = new ArrayList<>();
        }
        for(int i=0; i<inningCount; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            for(int j=1; j<10; j++) {
                int result = Integer.parseInt(st.nextToken());
                players[j].add(result);
            }
        }
    }

    public void initNumber() {
        number = new int[8];
        for(int i=0; i<8; i++) {
            number[i] = i+2;
        }
    }

    public void permutation(int k) {
        if(k == 8) {
            doCommand();
//			for(int n : number) {
//				System.out.print(n + " ");
//			}
//			System.out.println();
            return;
        }
        for(int i=k; i<8; i++) {
            swap(i, k);
            permutation(k + 1);
            swap(i, k);
        }
    }

    public void swap(int i, int k) {
        int temp = number[i];
        number[i] = number[k];
        number[k] = temp;
    }

    public void doCommand() {
        String playOrder = getOrder(); // 불변(모든 이닝 동안)
        for(int i=0; i<inningCount; i++) {
            int outCount = 0;
            boolean[] ground = new boolean[4];
            while(true) {
                if(outCount == 3) break;
//                System.out.println("@:"+playerOrder);
                int playerNumber = playOrder.charAt(playerOrder) - '0';
//                System.out.println("@@:"+playerNumber);
                int hitKind = players[playerNumber].get(i);
                outCount = playGame(outCount, ground, hitKind);
                playerOrder++; // 타순
                playerOrder %= 9;
//                System.out.println("out: " + outCount);
            }
        }
        playerOrder = 0;
        maxScore = Math.max(maxScore, partScore);
        partScore = 0;
    }

    public String getOrder() {
        StringBuffer sb = new StringBuffer();
        for(int i=0; i<8; i++) {
            if(i == 3) {
                sb.append(1);
            }
            sb.append(number[i]);
        }
        return sb.toString();
    }

    public int playGame(int outCount, boolean[] ground, int hitKind) {
        if(hitKind == 1) { // 안타
            ground[0] = true;
            rotate(1, ground);
        }else if(hitKind == 2) { // 2루타
            ground[0] = true;
            rotate(2, ground);
        }else if(hitKind == 3) { // 3루타
            ground[0] = true;
            rotate(3, ground);
        }else if(hitKind == 4) { // 홈런
            for(int i=0; i<4; i++) {
                if(ground[i]) {
                    partScore++;
                    ground[i] = false;
                }
            }
            partScore++;
        }else { // 아웃
            outCount++;
        }
        return outCount; // outCount 개수
    }

    public void rotate(int count, boolean[] ground) {
        for(int i=0; i<count; i++) {
            boolean temp = ground[3];
            for(int j=2; j>=0; j--) {
                ground[j+1] = ground[j];
            }
            ground[0] = temp;
            if(ground[0]) {
                partScore++;
                ground[0] = false;
            }
        }
    }
}
