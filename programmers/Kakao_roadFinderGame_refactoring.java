package algorithm.programmers;
import java.util.ArrayList;
import java.util.PriorityQueue;

class Kakao_roadFinderGame_refactoring {

    public static class TreeNode implements Comparable<TreeNode> {
        private int x;
        private int y;
        private int index;
        private TreeNode leftChild;
        private TreeNode rightChild;

        public TreeNode(int x, int y, int index) {
            this.x = x;
            this.y = y;
            this.index = index;
            leftChild = null;
            rightChild = null;
        }
        @Override
        public int compareTo(TreeNode temp) {
            return (this.y >= temp.y ? -1 : 1);
        }
    }

    private static PriorityQueue<TreeNode> priorityQueue = new PriorityQueue<>();
    private static int[][] nodeinfo = {{5,3},{11,5},{13,3},{3,5},{6,1},{1,3},{8,6},{7,2},{2,2}};

    public static void main(String[] agrs){
        Kakao_roadFinderGame_refactoring game = new Kakao_roadFinderGame_refactoring();
        int[][] output = game.solution();
        for (int i = 0; i < output[0].length; i++) {
            System.out.print( output[0][i] + " " );
            System.out.println( output[1][i] );
        }
    }

    public static int[][] solution() {
        for (int i = 0; i < nodeinfo.length; i++) {
            priorityQueue.add(new TreeNode(nodeinfo[i][0], nodeinfo[i][1], i + 1));
        }
        TreeNode root = priorityQueue.poll();
        while (!priorityQueue.isEmpty()) {
            insert( root, priorityQueue.poll() );
        }
        int[][] output = new int[2][nodeinfo.length];
        ArrayList<Integer> list1 = new ArrayList<>();
        ArrayList<Integer> list2 = new ArrayList<>();
        preorderTraversal( root, list1 );
        postorderTraversal( root, list2 );

        for (int i = 0; i < output[0].length; i++) {
            output[0][i] = list1.get(i);
            output[1][i] = list2.get(i);
        }
        return output;
    }

    public static void insert( TreeNode root, TreeNode nodeToInsert ) {
        if( root.x >= nodeToInsert.x ){
            if( root.leftChild == null ){
                root.leftChild = nodeToInsert;
            }else{
                insert( root.leftChild, nodeToInsert );
            }
        }else{
            if( root.rightChild == null ){
                root.rightChild = nodeToInsert;
            }else{
                insert( root.rightChild, nodeToInsert );
            }
        }
    }

    public static void preorderTraversal( TreeNode rootNode, ArrayList list ) {
        if( rootNode != null ){
            list.add(rootNode.index);
            preorderTraversal( rootNode.leftChild, list );
            preorderTraversal( rootNode.rightChild, list );
        }
    }

    public static void postorderTraversal( TreeNode rootNode, ArrayList list ) {
        if( rootNode != null){
            postorderTraversal( rootNode.leftChild, list );
            postorderTraversal( rootNode.rightChild, list );
            list.add(rootNode.index);
        }
    }
}