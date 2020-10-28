package algorithm.jungol;

import java.io.*;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class Jungol_1516 {

    private static BufferedReader br = new BufferedReader( new InputStreamReader( System.in ));
    private static BufferedWriter bw = new BufferedWriter( new OutputStreamWriter( System.out ));
    private static TreeMap<String, Integer> treeMap = new TreeMap();

    public static void main(String[] args) throws IOException {
        while(true){
            String temp = br.readLine();
            if( temp.equals("END") ) break;
            String[] tokens = temp.split(" |, " ); // 정규 표현식
            saveWords( tokens );
            Set set = treeMap.entrySet();
            ArrayList<Map.Entry> list = new ArrayList(set);
            for( Map.Entry n : list ){
                bw.write( n.getKey() + " : " + n.getValue() + "\n" );
            }
            treeMap.clear();
        }
        bw.flush();
        bw.close();
    }

    public static void saveWords( String[] tokens ){
        int size = tokens.length;
        for( int i=0; i<size; i++ ){
            if( treeMap.containsKey(tokens[i]) ){
                int count = treeMap.get(tokens[i]);
                treeMap.put( tokens[i], ++count );
            }else{
                treeMap.put( tokens[i] , 1 );
            }
        }
    }
}
