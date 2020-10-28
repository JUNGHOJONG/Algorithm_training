package algorithm.programmers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.StringTokenizer;

public class Kakao_recomandTest01 {
    public static class Document{
        private long id;
        private int wordCount;
        private ArrayList<String> wordList = new ArrayList<>();
        public Document( long id, int wordCount ){
            this.id = id;
            this.wordCount = wordCount;
        }
    }
    public static class Cluster{
        private long representativeId;
        private int count = 0;
        private ArrayList<Long> documentIdList = new ArrayList<>();
        public Cluster( long representativeId ){
            this.representativeId = representativeId;
        }
    }
    public static class Id_tfIdf{
        private String word;
//        private float id_tfIdf = 0;
        private int tf = 0;
        private double idf = 0;
        private double tf_idf = tf * idf;

        public Id_tfIdf( String word ){
            this.word = word;
        }

    }
    private static BufferedReader br = new BufferedReader( new InputStreamReader( System.in ) );
    private static HashMap<Long, Id_tfIdf[]> id_tfIdf_management = new HashMap<>();
    private static HashMap<String, Integer> word_paperCount_management = new HashMap<>();
    private static HashSet<String> hashSet = new HashSet<>();
    private static ArrayList<Document> documentList = new ArrayList<>();
    private static ArrayList<Cluster> clusterList = new ArrayList<>();
    private static int newsCount;
    private static int uniqueWordCount = 0; // hashSet으로 구한다.

    public static void main(String[] args) throws IOException {
        newsCount = Integer.parseInt( br.readLine() );
        initDocumentList();
        initId_tfIdf_management();
        updateId_tfIdf();
        goClustering();
        System.out.println( clusterList.size() );
        for( Cluster cluster : clusterList ){
            System.out.println( cluster.representativeId + " " + cluster.count );
        }
    }

    public static void initDocumentList() throws IOException{
        for( int i=0; i<newsCount; i++ ){
            StringTokenizer st1 = new StringTokenizer( br.readLine() );
            String temp1 = st1.nextToken();
            String temp2 = st1.nextToken();
            long documentId = Long.parseLong( temp1 );
            int wordCount = Integer.parseInt( temp2 );
//            System.out.println( documentId + " : " + wordCount );
            Document document = new Document( documentId, wordCount );
            StringTokenizer st2 = new StringTokenizer( br.readLine() );
            for( int j=0; j<wordCount; j++ ){
                String temp = st2.nextToken();
                document.wordList.add( temp );
                hashSet.add( temp );
                if( !word_paperCount_management.containsKey( temp ) ){
                    word_paperCount_management.put( temp, 1 );
                }else{
                    int tempCount = word_paperCount_management.get( temp );
                    word_paperCount_management.put( temp, tempCount + 1 );
                }
            }
            documentList.add( document );
        }
    }

    public static void initId_tfIdf_management(){
        String[] words = new String[hashSet.size()];
        words = hashSet.toArray( words );
        for( Document n : documentList ){
            int index = 0;
            Id_tfIdf[] id_tfIdfs = new Id_tfIdf[words.length];
            for( String s : words ){
                id_tfIdfs[index] = new Id_tfIdf( s );
                index++;
            }
            long tempId = n.id;
            id_tfIdf_management.put( tempId, id_tfIdfs );
        }
    }

    public static void updateId_tfIdf(){
        // tf 최신화
//        System.out.println( "documentTotal: " + documentList.size() );
        for( Document document : documentList ){
            Id_tfIdf[] id_tfIdfs = id_tfIdf_management.get( document.id );
            for( String s : document.wordList ){
//                Id_tfIdf[] id_tfIdfs = id_tfIdf_management.get( document.id );
                int index = 0;
                for( Id_tfIdf tfIdf : id_tfIdfs ){
                    if( s.equals( tfIdf.word ) ){
                        id_tfIdfs[index].tf++;
//                        System.out.println( "id: " + document.id + " tf: " + id_tfIdfs[index].tf );
                        id_tfIdfs[index].idf = Math.log( documentList.size() / ( word_paperCount_management.get( s ) + 1 ) );
//                        System.out.println( "s: " + s + " documentTotal: " + documentList.size() + " paperCount: " + word_paperCount_management.get( s ) + " result: " + id_tfIdfs[index].idf);
                        id_tfIdfs[index].tf_idf = id_tfIdfs[index].tf * id_tfIdfs[index].idf;
                    }
                    index++;
                }
            }
            id_tfIdf_management.put( document.id, id_tfIdfs );
        }
        // idf 최신화 -> log (전체 문서 수 / 해당 단어가 나타난 문서 수)
    }

    public static void goClustering(){
        for( Document document : documentList ){
            if( clusterList.size() == 0 ) {
              Cluster cluster = new Cluster( document.id );
              cluster.count++;
                clusterList.add( cluster );
            }
            boolean check = true;
            for( Cluster cluster : clusterList ){
                // 클러스터 대표와 비교해서 유사도 0.98이상만 그 아래에 편입시키다. + 가장 유사도 높은 클러스터일으로 들어감
                Id_tfIdf[] temp1 = id_tfIdf_management.get( document.id );
                Id_tfIdf[] temp2 = id_tfIdf_management.get( cluster.representativeId );
                if( isSatisfiedSimillar( temp1, temp2 ) ){
                    cluster.documentIdList.add( document.id );
                    cluster.count++;
                    check = false;
                    break;
                }
            }
            if( check ){
                Cluster cluster = new Cluster( document.id );
                cluster.count++;
                clusterList.add( cluster );
            }
        }
    }

    public static boolean isSatisfiedSimillar( Id_tfIdf[] temp1, Id_tfIdf[] temp2 ){
        double molecularSum = 0;
        double denominatorTemp1 = 0;
        double denominatorTemp2 = 0;
        for( int i=0; i<temp1.length; i++ ){
//            System.out.println( temp1[i].tf_idf + " : " + temp2[i].tf_idf );
            molecularSum += temp1[i].tf_idf * temp2[i].tf_idf;
            denominatorTemp1 += temp1[i].tf_idf * temp1[i].tf_idf;
            denominatorTemp2 += temp2[i].tf_idf * temp2[i].tf_idf;
        }
        denominatorTemp1 = Math.sqrt( denominatorTemp1 );
        denominatorTemp2 = Math.sqrt( denominatorTemp2 );
        double simillarity = molecularSum / ( denominatorTemp1 * denominatorTemp2 );
        System.out.println( "sim: " + simillarity );
        if( simillarity >= 0.98 ) return true;
        else return false;
    }
}
