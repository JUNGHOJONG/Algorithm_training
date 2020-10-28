package algorithm.backjoon;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class Backjoon1920 {
    public static void main(String[] args) throws IOException{
        Backjoon1920 backjoon1920 = new Backjoon1920();
        backjoon1920.mainMethod();
    }

    public static class TreeNode{
        private int index;
        private int value;
        private TreeNode leftChild = null;
        private TreeNode rightChild = null;
        public TreeNode(int index, int value){
            this.index = index;
            this.value = value;
        }
    }

    private BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    public void mainMethod() throws IOException {
        int numberCount = Integer.parseInt(br.readLine());
        TreeNode root = initTree(numberCount);
        int findCount = Integer.parseInt(br.readLine());
        findTree(findCount, root);
    }

    public TreeNode initTree(int numberCount) throws IOException{
        StringTokenizer st = new StringTokenizer(br.readLine());
        TreeNode root = new TreeNode(1, Integer.parseInt(st.nextToken()));
        for(int i=1; i<numberCount; i++){
            insert(root, new TreeNode(i+1, Integer.parseInt(st.nextToken())));
        }
        return root;
    }

    public void insert(TreeNode root, TreeNode noToInsert){
        if(root.value >= noToInsert.value){
            if(root.leftChild == null){
                root.leftChild = noToInsert;
            }else{
                insert(root.leftChild, noToInsert);
            }
        }else{
            if(root.rightChild == null){
                root.rightChild = noToInsert;
            }else{
                insert(root.rightChild, noToInsert);
            }
        }
    }

    public void findTree(int findCount, TreeNode root) throws IOException{
        StringTokenizer st = new StringTokenizer(br.readLine());
        ArrayList<Integer> list = new ArrayList<>();
        for(int i=0; i<findCount; i++){
            int numberToFind = Integer.parseInt(st.nextToken());
            if(isExist(numberToFind, root)){
                list.add(1);
                continue;
            }
            list.add(0);
        }
        for(Integer i : list){
            System.out.println(i);
        }
    }

    public boolean isExist(int numberToFind, TreeNode root){
        if(root == null) return false;
        if(numberToFind < root.value){
            return isExist(numberToFind, root.leftChild);
        }else if(numberToFind > root.value){
            return isExist(numberToFind, root.rightChild);
        }else{
            return true;
        }
    }
}

