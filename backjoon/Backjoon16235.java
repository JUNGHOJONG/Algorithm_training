package algorithm.backjoon;

import java.io.*;
import java.util.*;

public class Backjoon16235 {
    public static class Tree{
        private int positionX;
        private int positionY;
        private int age;
        public Tree( int positionX, int positionY, int age ){
            this.positionX = positionX;
            this.positionY = positionY;
            this.age = age;
        }
    }
    private static BufferedReader br = new BufferedReader( new InputStreamReader( System.in ));
    private static BufferedWriter bw = new BufferedWriter( new OutputStreamWriter( System.out ));
    private static ArrayList<Integer>[][] plantedTrees;
    private static Queue<Tree> deadTreeList = new LinkedList<>();
    private static int landSize;
    private static int treeCount;
    private static int elapsedYear;
    private static int[][] landNourishment;
    private static int[][] additionalNourishment;
    private static int[] directionX = { 1, 1, 0, -1, -1, -1, 0, 1 };
    private static int[] directionY = { 0, -1, -1, -1, 0, 1, 1, 1 };

    public static void main(String[] args) throws IOException {
        StringTokenizer st = new StringTokenizer(br.readLine());
        landSize = Integer.parseInt(st.nextToken());
        treeCount = Integer.parseInt(st.nextToken());
        elapsedYear = Integer.parseInt(st.nextToken());
        initLand();
        initPlantedTress();
        for( int i=0; i<elapsedYear; i++ ){
            eatNourishmentInSpring();
            addNourishmentInSummer();
            breedTreeInAutumn();
            addNourishmentInWinter();
        }
        int output = countLiveTree();
        System.out.println( output );
    }

    public static void initLand() throws IOException{
        landNourishment = new int[landSize][landSize];
        additionalNourishment = new int[landSize][landSize];
        for( int i=0; i<landSize; i++ ){
            StringTokenizer st = new StringTokenizer(br.readLine());
            for( int j=0; j<landSize; j++ ){
                additionalNourishment[i][j] = Integer.parseInt(st.nextToken());
                landNourishment[i][j] = 5;
            }
        }
    }

    public static void initPlantedTress() throws IOException{
        plantedTrees = new ArrayList[landSize][landSize];
        for( int i=0; i<landSize; i++ ){
            for( int j=0; j<landSize; j++ ){
                plantedTrees[i][j] = new ArrayList<>();
            }
        }
        for( int i=0; i<treeCount; i++ ){
            StringTokenizer st = new StringTokenizer(br.readLine());
            int y = Integer.parseInt(st.nextToken())-1;
            int x = Integer.parseInt(st.nextToken())-1;
            int age = Integer.parseInt(st.nextToken());
            plantedTrees[y][x].add( age );
        }
    }

    public static void eatNourishmentInSpring(){
        sortTrees();
        for( int i=0; i<landSize; i++ ){
            for( int j=0; j<landSize; j++ ){
                int index = 0;
                int size = plantedTrees[i][j].size();
                boolean check = false;
               for( int k=0; k<size; k++ ){
                   if( landNourishment[i][j] >= plantedTrees[i][j].get(k) ){
                       landNourishment[i][j] -= plantedTrees[i][j].get(k);
                       plantedTrees[i][j].set( k, plantedTrees[i][j].get(k)+1 );
                   }else{
                       index = k;
                       check = true;
                       break;
                   }
               }
               if( check ){
                   for( int k=size-1; k>=index; k-- ){
                       deadTreeList.add( new Tree( j, i, plantedTrees[i][j].get(k) ) );
                       plantedTrees[i][j].remove(k);
                   }
               }
            }
        }
    }

    public static void sortTrees(){
        for( int i=0; i<landSize; i++ ){
            for( int j=0; j<landSize; j++ ){
                if( plantedTrees[i][j].size() >= 2 ) {
                    Collections.sort( plantedTrees[i][j] );
                }
            }
        }
    }

    public static void addNourishmentInSummer(){
        while(!deadTreeList.isEmpty()){
            Tree deadTree = deadTreeList.poll();
            int currentX = deadTree.positionX;
            int currentY = deadTree.positionY;
            int currentAge = deadTree.age;
            landNourishment[currentY][currentX] += currentAge / 2;
        }
    }

    public static void breedTreeInAutumn(){
        for( int i=0; i<landSize; i++ ){
            for( int j=0; j<landSize; j++ ){
                int size = plantedTrees[i][j].size();
                for( int k=0; k<size; k++ ){
                    if( plantedTrees[i][j].get(k) % 5 == 0 ){
                        for( int l=0; l<8; l++ ){
                          int nextX = j + directionX[l];
                          int nextY = i + directionY[l];
                            if( movable( nextX, nextY ) ){
                                plantedTrees[nextY][nextX].add( 1 );
                            }
                        }
                    }
                }
            }
        }
        sortTrees();
    }

    public static boolean movable( int nextX, int nextY ){
        return ( nextX>=0 && nextX<landSize && nextY>=0 && nextY<landSize );
    }

    public static void addNourishmentInWinter(){
        for( int i=0; i<landSize; i++ ){
            for( int j=0; j<landSize; j++ ){
                landNourishment[i][j] += additionalNourishment[i][j];
            }
        }
    }

    public static int countLiveTree(){
        int count = 0;
        for( int i=0; i<landSize; i++ ){
            for( int j=0; j<landSize; j++ ){
                count += plantedTrees[i][j].size();
            }
        }
        return count;
    }

}
