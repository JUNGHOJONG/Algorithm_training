package algorithm.backjoon;

import java.io.*;
import java.util.HashMap;
import java.util.StringTokenizer;

public class Backjoon4195 {

    private static BufferedReader br = new BufferedReader( new InputStreamReader( System.in));
    private static BufferedWriter bw = new BufferedWriter( new OutputStreamWriter( System.out));
    private static HashMap<String, Integer> hashMap;
    private static int testCase;
    private static int relationship;
    private static int[] parent;
    private static int[] size;

    public static void main(String[] args) throws IOException {
        testCase = Integer.parseInt(br.readLine());
        for( int i=1; i<=testCase; i++ ){
            hashMap = new HashMap<String, Integer>();
            relationship = Integer.parseInt(br.readLine());
            initParent();
            initSize();
            initHashmap();
        }
        bw.flush();
        bw.close();
    }

    public static void initParent(){
        parent = new int[100001];
        for( int i=1; i<=100000; i++ ){
            parent[i] = i;
        }
    }

    public static void initSize(){
        size = new int[100001];
        for( int i=1; i<=100000; i++ ){
            size[i] = 1;
        }
    }

    public static void initHashmap() throws IOException{
        int index = 1;
        for( int i=1; i<=relationship; i++ ){
            StringTokenizer st = new StringTokenizer(br.readLine());
            String firstPerson = st.nextToken();
            String secondPerson = st.nextToken();
            int firstIndex = 0;
            int secondIndex = 0;
            if( !hashMap.containsKey(firstPerson) ) {
                firstIndex = index;
                hashMap.put( firstPerson, index );
                index++;
            }else{
                firstIndex = hashMap.get(firstPerson);
            }
            if( !hashMap.containsKey(secondPerson) ) {
                secondIndex = index;
                hashMap.put( secondPerson, index );
                index++;
            }else{
                secondIndex = hashMap.get(secondPerson);
            }
            mergeParent( firstIndex, secondIndex );
            int output = size[getParent(firstIndex)];
            bw.write( output + "\n" );
        }
    }

    public static int getParent( int data ){
        if( parent[data] == data ) return data;
        return parent[data] = getParent( parent[data] );
    }

    public static void mergeParent( int a, int b ){
        int parentA = getParent(a);
        int parentB = getParent(b);
        if( parentA < parentB ) {
            parent[parentB] = parentA;
            size[parentA] += size[parentB];
        } else if( parentA > parentB ) {
            parent[parentA] = parentB;
            size[parentB] += size[parentA];
        }
    }
}
