package algorithm.programmers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class Kakao_matchingScore_again {
    public class Web{
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
    private HashMap<String, Integer> indexAndSelfUrl = new HashMap<>();
    private ArrayList<Web> webManagement = new ArrayList<>();
    public int solution(String word, String[] pages) {
        mathcingIndexAndRref( pages );
        initWebManagement( pages, word );
        sortWebManagement();
        return webManagement.get( webManagement.size() - 1 ).index;
    }

    public void mathcingIndexAndRref( String[] pages ){
        Pattern pattern = Pattern.compile( "<meta property=\"og:url\" content=\"https://(.+?)\"" );
        int index = 0;
        for( String page : pages ){
            Matcher matcher = pattern.matcher( page );
            while( matcher.find() ){
                String href = matcher.group( 1 );
                indexAndSelfUrl.put( href, index );
            }
            index++;
        }
    }

    public void initWebManagement( String[] pages, String word ){
        initBasicScore( pages, word );
        initExternalLinkAddress( pages );
        initLinkScore( pages );
        initTotalLinkScore( pages );
        initMatchingScore();
    }

    public void initBasicScore( String[] pages, String word ){
        int index = 0;
        String wordConvertedToLowercase = word.toLowerCase();
        for (String page : pages) {
            webManagement.add( new Web( index ) );
            String pageConvertedToLowercase = page.toLowerCase();
            int findIndex = pageConvertedToLowercase.indexOf( wordConvertedToLowercase );
            int count = 0;
            while( findIndex != -1 ){
                char start = pageConvertedToLowercase.charAt( findIndex - 1 );
                char end = pageConvertedToLowercase.charAt( findIndex
                        + wordConvertedToLowercase.length() );
                if( !Character.isLowerCase( start ) && ! Character.isLowerCase( end ) ){
                    count++;
                }
                findIndex = pageConvertedToLowercase.indexOf( wordConvertedToLowercase,
                        findIndex + wordConvertedToLowercase.length() );
            }
            webManagement.get( index ).basicScore = count;
            index++;
        }
    }

    public void initExternalLinkAddress( String[] pages ){ // OK!!
        Pattern pattern = Pattern.compile( "<a href=\"https://(.+?)\"" );
        int index = 0;
        for( String page : pages ){
            Matcher matcher = pattern.matcher( page );
            while(matcher.find()){
                String href = matcher.group( 1 );
                if( !webManagement.get( index ).externalLinkAddress.contains( href ) ){
                    webManagement.get( index ).externalLinkAddress.add( href );
                }
            }
            index++;
        }
    }

    public void initLinkScore( String[] pages ){
        for ( Web web : webManagement ) {
            if( web.externalLinkAddress.size() > 0 ) web.linkScore = ( ( double ) web.basicScore / ( double ) web.externalLinkAddress.size() );
        }
    }

    public void initTotalLinkScore( String[] pages ){
        for( Web web : webManagement ){
            for( String externalLinkAddress : web.externalLinkAddress ){
                if( indexAndSelfUrl.containsKey( externalLinkAddress ) ){
                    int tempIndex = indexAndSelfUrl.get( externalLinkAddress );
                    webManagement.get( tempIndex ).totalLinkScore += web.linkScore;
                }
            }
        }
    }

    public void initMatchingScore(){
        for (Web web : webManagement) {
            web.matchingScore = (double) web.basicScore + web.totalLinkScore;
        }
    }

    public void sortWebManagement(){
        webManagement.sort( (a, b) -> {
            if ( Double.compare( a.matchingScore, b.matchingScore ) == 0 ) {
                return b.index - a.index;
            } else {
                return Double.compare ( a.matchingScore, b.matchingScore );
            }
        } );
    }
}