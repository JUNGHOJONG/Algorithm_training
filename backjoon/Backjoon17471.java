package algorithm.backjoon;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Backjoon17471 {
    public static class Section{
        private int population;
        private int count;
        public Section( int population, int count ){
            this.population = population;
            this.count = count;
        }
    }
    private static BufferedReader br = new BufferedReader( new InputStreamReader( System.in ));
    private static ArrayList<Integer>[] sectionLink;
    private static int[] population;
    private static boolean[] visited;
    private static int sectionCount;
    private static int minValue = Integer.MAX_VALUE;
    private static boolean dividePossible = false;
    public static void main(String[] args) throws IOException {
        sectionCount = Integer.parseInt(br.readLine());
        initPopulation();
        initSectionLink();
        visited = new boolean[sectionCount+1];
        for( int i=1; i<sectionCount; i++ ){
            combination( 1, 0, i );
        }
        System.out.println( dividePossible ? minValue : -1 );
    }

    public static void initPopulation() throws IOException{
        population = new int[sectionCount+1];
        StringTokenizer st = new StringTokenizer(br.readLine());
        for( int i=1; i<=sectionCount; i++ ){
            population[i] = Integer.parseInt(st.nextToken());
        }
    }

    public static void initSectionLink() throws IOException{
        sectionLink = new ArrayList[sectionCount + 1];
        for ( int i = 0; i <= sectionCount; i++ ) {
            sectionLink[i] = new ArrayList<>();
        }
        for( int i=1; i<=sectionCount; i++ ){
            StringTokenizer st = new StringTokenizer(br.readLine());
            int count = Integer.parseInt(st.nextToken());
            for( int j=0; j<count; j++ ){
                int endSection = Integer.parseInt(st.nextToken());
                sectionLink[i].add( endSection );
                sectionLink[endSection].add( i );
            }
        }
    }

    public static void combination( int k, int count, int endValue ){
        if( count == endValue ){
            doCommand();
            return;
        }
        for( int i=k; i<=sectionCount; i++ ){
            visited[i] = true;
            combination( i+1, count+1, endValue );
            visited[i] = false;
        }
    }

    public static void doCommand(){
        boolean check1 = true;
        boolean check2 = true;
        int value1 = 0;
        int value2 = 0;
        int tempCount = 0;
        for( int i=1; i<=sectionCount; i++ ){
            if( visited[i] && check1 ){
                Section temp1 = doBfsGroup( i, true );
                value1 += temp1.population;
                tempCount += temp1.count;
                check1 = false;
            }else if( !visited[i] && check2 ){
                Section temp2 = doBfsGroup( i, false );
                value2 = temp2.population;
                tempCount += temp2.count;
                check2 = false;
            }
            if( !check1 && !check2 && tempCount == sectionCount ){
                minValue = Math.min( minValue, Math.abs( value1 - value2 ));
                dividePossible = true;
                break;
            }
        }
    }

    public static Section doBfsGroup( int index, boolean check ){
        Queue<Integer> queue = new LinkedList<>();
        boolean[] tempVisited = new boolean[sectionCount+1];
        tempVisited[index] = true;
        int tempPopulation = population[index];
        int count = 1;
        queue.add( index );
        while(!queue.isEmpty()){
            int tempIndex = queue.poll();
            for( int n : sectionLink[tempIndex] ){
                if( !tempVisited[n] && checkSameSection( n, check ) ){
                    tempVisited[n] = true;
                    queue.add( n );
                    count++;
                    tempPopulation += population[n];
                }
            }
        }
        return new Section( tempPopulation, count );
    }

    public static boolean checkSameSection( int n, boolean check ){
        return ( check ? visited[n] : !visited[n] );
    }

}
