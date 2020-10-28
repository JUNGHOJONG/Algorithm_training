package algorithm.programmers;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Kakao_matchingScore {
    public static class Web{
        private int index;
        private int basicScore = 0;
        private ArrayList<String> externalLinkAddress;
        private double linkScore = 0;
        private double totalLinkScore = 0;
        private double matchingScore = 0;
        public Web( int index ){
            this.index = index;
            externalLinkAddress = new ArrayList<>();
        }
    }
    private static HashMap<String, Integer> indexAndSelfUrl = new HashMap<>();
    private static ArrayList<Web> webManagement = new ArrayList<>();
    public static void main(String[] args) {
//        String[] pages = {"<html lang=\"ko\" xml:lang=\"ko\" xmlns=\"http://www.w3.org/1999/xhtml\">\n<head>\n  <meta charset=\"utf-8\">\n  <meta property=\"og:url\" content=\"https://a.com\"/>\n</head>  \n<body>\nBlind Lorem Blind ipsum dolor Blind test sit amet, consectetur adipiscing elit. \n<a href=\"https://b.com\"> Link to b </a>\n</body>\n</html>", "<html lang=\"ko\" xml:lang=\"ko\" xmlns=\"http://www.w3.org/1999/xhtml\">\n<head>\n  <meta charset=\"utf-8\">\n  <meta property=\"og:url\" content=\"https://b.com\"/>\n</head>  \n<body>\nSuspendisse potenti. Vivamus venenatis tellus non turpis bibendum, \n<a href=\"https://a.com\"> Link to a </a>\nblind sed congue urna varius. Suspendisse feugiat nisl ligula, quis malesuada felis hendrerit ut.\n<a href=\"https://c.com\"> Link to c </a>\n</body>\n</html>", "<html lang=\"ko\" xml:lang=\"ko\" xmlns=\"http://www.w3.org/1999/xhtml\">\n<head>\n  <meta charset=\"utf-8\">\n  <meta property=\"og:url\" content=\"https://c.com\"/>\n</head>  \n<body>\nUt condimentum urna at felis sodales rutrum. Sed dapibus cursus diam, non interdum nulla tempor nec. Phasellus rutrum enim at orci consectetu blind\n<a href=\"https://a.com\"> Link to a </a>\n</body>\n</html>"};
//        String word = "blind";
        String[] pages = {"<html lang=\"ko\" xml:lang=\"ko\" xmlns=\"http://www.w3.org/1999/xhtml\">\n<head>\n  <meta charset=\"utf-8\">\n  <meta property=\"og:url\" content=\"https://careers.kakao.com/interview/list\"/>\n</head>  \n<body>\n<a href=\"https://programmers.co.kr/learn/courses/4673\"></a>#!MuziMuzi!)jayg07con&&\n\n</body>\n</html>", "<html lang=\"ko\" xml:lang=\"ko\" xmlns=\"http://www.w3.org/1999/xhtml\">\n<head>\n  <meta charset=\"utf-8\">\n  <meta property=\"og:url\" content=\"https://www.kakaocorp.com\"/>\n</head>  \n<body>\ncon%\tmuzI Muzi-Muzi Muzi92apeach&2<a href=\"https://hashcode.co.kr/tos\"></a>\n\n\t^\n</body>\n</html>"};
        String word = "Muzi";
        mathcingIndexAndRref( pages );
        initWebManagement( pages, word );
        sortWebManagement();
        System.out.println( webManagement.get( webManagement.size() - 1 ).index );
    }

    public static void mathcingIndexAndRref( String[] pages ){
        Pattern pattern = Pattern.compile( "content=\"https:\\/\\/(.+?)\"" );
        int index = 0;
        for( String page : pages ){
            Matcher matcher = pattern.matcher( page );
            while(matcher.find()){
                String href = matcher.group( 1 );
                indexAndSelfUrl.put( href, index );
            }
            index++;
        }
    }

    public static void initWebManagement( String[] pages, String word ){
        initBasicScore( pages, word );
        initExternalLinkAddress( pages );
        initLinkScore( pages );
        initTotalLinkScore( pages );
        initMatchingScore();
    }

    public static void initBasicScore( String[] pages, String word ){
        int index = 0;
        StringBuffer sb = new StringBuffer();
        sb.append( "[^a-zA-Z](?i)" );
        sb.append( word );
        sb.append( "[^a-zA-Z]" );
        Pattern pattern = Pattern.compile( sb.toString() );
        for (String page : pages) {
            webManagement.add( new Web( index ) );
            Matcher matcher = pattern.matcher( page );
            StringBuffer sb2 = new StringBuffer();
            while( matcher.find() ){
                matcher.appendReplacement( sb2, "  " + word + "  " );
            }
            int count = 0;
            matcher.appendTail( sb2 );
            matcher = pattern.matcher( sb2.toString() );
            while( matcher.find() ){
                count++;
            }
            webManagement.get( index ).basicScore = count;
            index++;
        }
    }

    public static void initExternalLinkAddress( String[] pages ){
        Pattern pattern = Pattern.compile( "<a href=\"https:\\/\\/(.+?)\"" );
        int index = 0;
        for( String page : pages ){
            Matcher matcher = pattern.matcher( page );
            while(matcher.find()){
                String href = matcher.group( 1 );
                webManagement.get( index ).externalLinkAddress.add( href );
            }
            index++;
        }
    }

    public static void initLinkScore( String[] pages ){
        for ( Web web : webManagement ) {
            web.linkScore = ( web.basicScore / ( double ) web.externalLinkAddress.size() );
        }
    }

    public static void initTotalLinkScore( String[] pages ){
        for( Web web : webManagement ){
            for( String externalLinkAddress : web.externalLinkAddress ){
                if( indexAndSelfUrl.containsKey( externalLinkAddress ) ){
                    int tempIndex = indexAndSelfUrl.get( externalLinkAddress );
                    webManagement.get( tempIndex ).totalLinkScore += web.linkScore;
                }
            }
        }
    }

    public static void initMatchingScore(){
        for (Web web : webManagement) {
            web.matchingScore = (double) web.basicScore + web.totalLinkScore;
        }
    }

    public static void sortWebManagement(){
        webManagement.sort( (a, b) -> {
            if ( Double.compare( a.matchingScore, b.matchingScore ) == 0 ) {
                return b.index - a.index;
            } else {
                return Double.compare ( a.matchingScore, b.matchingScore );
            }
        } );
    }
}
