package algorithm.programmers;

import java.util.ArrayList;
import java.util.PriorityQueue;

public class Kakao_roadFinderGame {

    public static class TreeNode implements Comparable<TreeNode>{
        private int x;
        private int y;
        private int index;
        public TreeNode( int x, int y, int index ){
            this.x = x;
            this.y = y;
            this.index = index;
        }
        @Override
        public int compareTo(TreeNode temp) {
            return ( this.y >= temp.y ? -1 : 1 );
        }
    }

    public static class BinaryTree_generic {

        public static class Node<TreeNode> {
            private TreeNode data;
            private Node<TreeNode> rightChild;
            private Node<TreeNode> leftChild;
            private Node<TreeNode> parent;

            public Node(TreeNode data) {
                this.data = data;
                rightChild = null;
                leftChild = null;
                parent = null;
            }
        }

        private Node<TreeNode> root;
        private int size = 0;

        public BinaryTree_generic() {
            root = null;
        }

        public Node<TreeNode> getRoot(){
            return root;
        }

        public void insert(TreeNode data) {
            Node<TreeNode> newNode = new Node<TreeNode>(data);
            if (root == null) {
                root = newNode;
                size++;
                return;
            }
            Node<TreeNode> currentNode = findInsertPosition(data);
            if (data.x <= currentNode.data.x) {
                currentNode.leftChild = newNode;
                newNode.parent = currentNode;
            } else {
                currentNode.rightChild = newNode;
                newNode.parent = currentNode;
            }
            size++;
        }

        public Node<TreeNode> findInsertPosition(TreeNode data) {
            Node<TreeNode> parentNode = null;
            Node<TreeNode> currentNode = root;
            while (currentNode != null) {
                parentNode = currentNode;
                if (data.x <= currentNode.data.x) {
                    currentNode = currentNode.leftChild;
                } else {
                    currentNode = currentNode.rightChild;
                }
            }
            return parentNode;
        }

        public void preorderTraversal( Node<TreeNode> rootNode, ArrayList list ) {
            if( rootNode != null ) {
                list.add( rootNode.data.index );
                preorderTraversal( rootNode.leftChild, list );
                preorderTraversal( rootNode.rightChild, list );
            }
        }

        public void postorderTraversal( Node<TreeNode> rootNode, ArrayList list ) {
            if( rootNode != null ) {
                postorderTraversal( rootNode.leftChild, list );
                postorderTraversal( rootNode.rightChild, list );
                list.add( rootNode.data.index );
            }
        }
    }

    private static PriorityQueue<TreeNode> priorityQueue = new PriorityQueue<>();
    private static int[][] nodeInfo = {{5, 3}, {11, 5}, {13, 3}, {3, 5}, {6, 1}, {1, 3}, {8, 6}, {7, 2}, {2, 2}};

    public static void main(String[] args){
        BinaryTree_generic binaryTree_generic = new BinaryTree_generic();
        for( int i=0; i<nodeInfo.length; i++ ){
            priorityQueue.add( new TreeNode( nodeInfo[i][0], nodeInfo[i][1], i+1 ));
        }
        while(!priorityQueue.isEmpty()){
            binaryTree_generic.insert( priorityQueue.poll() );
        }
        int[][] output = new int[2][nodeInfo.length];
        ArrayList<Integer> list1 = new ArrayList<>();
        ArrayList<Integer> list2 = new ArrayList<>();
        binaryTree_generic.preorderTraversal( binaryTree_generic.getRoot(), list1 );
        binaryTree_generic.postorderTraversal( binaryTree_generic.getRoot(), list2 );
        for( int i=0; i<output[0].length; i++ ){
            output[0][i] = list1.get(i);
            output[1][i] = list2.get(i);
        }
    }
}

