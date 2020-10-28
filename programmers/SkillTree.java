package algorithm.programmers;

import java.util.Arrays;
import java.util.HashMap;

public class SkillTree {
    public static void main(String[] args) {
        String skill = "CBD";
        String[] skill_trees = {"BACDE", "CBADF", "AECB", "BDA"};
        Solution_SkillTree processCommand = new Solution_SkillTree();
        System.out.println(processCommand.solution(skill, skill_trees));
    }
}

class Solution_SkillTree{
    private HashMap<Character, Integer> skillOrder = new HashMap<>();
    private int skillLength;
    public int solution(String skill, String[] skill_trees){
        initMap(skill, skillOrder);
        skillLength = skill.length();
        int count = 0;
        for(String skillTree : skill_trees){
            if(isPossibleSkillTree(skillTree)){
                count++;
            }
        }
        return count;
    }

    public void initMap(String skill, HashMap<Character, Integer> skillOrder){
        for(int i=0; i<skill.length(); i++){
            skillOrder.put(skill.charAt(i), i);
        }
    }

    public boolean isPossibleSkillTree(String skillTree){
        boolean[] visited = new boolean[skillLength];
        for(int i=0; i<skillTree.length(); i++){
            char currentSkill = skillTree.charAt(i);
            if(!isAntecedentSkill(currentSkill, visited)){
                return false;
            }
        }
        return true;
    }

    public boolean isAntecedentSkill(char currentSkill, boolean[] visited){
        if(!skillOrder.containsKey(currentSkill)) return true;
        int skillIndex = skillOrder.get(currentSkill);
        visited[skillIndex] = true;
        for(int j=0; j<=skillIndex; j++){
            if(!visited[j]) {
                return false;
            }
        }
        return true;
    }

}
