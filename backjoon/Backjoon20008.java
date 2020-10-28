package algorithm.backjoon;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class Backjoon20008 {
    public static void main(String[] args) throws IOException{
        Solution_Backjoon20008 processCommand = new Solution_Backjoon20008();
        processCommand.solution();
    }
}

class Solution_Backjoon20008{
    public static class Skill{
        private final int MAX_COOLTIME;
        private int coolTime = 0;
        private int damage;
        public Skill(int coolTime, int damage){
            MAX_COOLTIME = coolTime;
            this.damage = damage;
        }
    }
    private BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    private ArrayList<Skill> skills = new ArrayList<>();
    private int minTime = Integer.MAX_VALUE;
    private int skillCount;
    private int monsterHp;
    public void solution() throws IOException {
        StringTokenizer st = new StringTokenizer(br.readLine());
        skillCount = Integer.parseInt(st.nextToken());
        monsterHp = Integer.parseInt(st.nextToken());
        initSkills(skillCount);
        dfs(0, 0);
        System.out.println(minTime);
    }

    public void initSkills(int skillCount) throws IOException{
        for(int i=0; i<skillCount; i++){
            StringTokenizer st = new StringTokenizer(br.readLine());
            int coolTime = Integer.parseInt(st.nextToken());
            int damage = Integer.parseInt(st.nextToken());
            skills.add(new Skill(coolTime, damage));
        }
    }

    public void dfs(int time, int cumulativeDamage){
        if(cumulativeDamage >= monsterHp){
            minTime = Math.min(minTime, time);
            return;
        }
        int check = 0;
        for(int i=0; i<skillCount; i++){
            if(skills.get(i).coolTime == 0){
                check++;
                int[] tempCoolTime = disCountAllSkillCoolTime();
                skills.get(i).coolTime = skills.get(i).MAX_COOLTIME;
                dfs(time + 1, cumulativeDamage + skills.get(i).damage);
                skills.get(i).coolTime = 0;
                rollBackAllSkillCoolTime(tempCoolTime);
            }
        }
        if(check == 0){
            int[] tempCoolTime = disCountAllSkillCoolTime();
            int check2 = 0;
            for(int i=0; i<skillCount; i++){
                if(skills.get(i).coolTime == 0){
                    check2++;
                    skills.get(i).coolTime = skills.get(i).MAX_COOLTIME;
                    dfs(time + 1, cumulativeDamage + skills.get(i).damage);
                    skills.get(i).coolTime = 0;
                }
            }
            if(check2 == 0){
                dfs(time + 1, cumulativeDamage);
            }
            rollBackAllSkillCoolTime(tempCoolTime);
        }
    }

    public int[] disCountAllSkillCoolTime(){
        int[] tempCoolTime = new int[skillCount];
        int index = 0;
        for(Skill skill : skills){
            if(skill.coolTime > 0){
                skill.coolTime--;
                tempCoolTime[index]--;
            }
            index++;
        }
        return tempCoolTime;
    }

    public void rollBackAllSkillCoolTime(int[] tempCoolTime){
        int index = 0;
        for(int coolTime : tempCoolTime){
            if(coolTime < 0){
                skills.get(index).coolTime++;
            }
            index++;
        }
    }
}
